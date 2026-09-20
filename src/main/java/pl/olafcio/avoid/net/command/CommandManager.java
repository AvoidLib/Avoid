package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.command.annotation.*;
import pl.olafcio.avoid.net.command.exception.*;
import pl.olafcio.avoid.net.command.exception.late.TooLateException;
import pl.olafcio.avoid.net.command.exception.use.CannotCallException;
import pl.olafcio.avoid.net.command.handling.CommandHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.Consumer;

/**
 * A namespace for managing commands.
 * <br/><br/>
 * To register a command, use {@link #add(Command)}.<br/>
 * To get metadata of a command instance, use {@link #get(Command)}.<br/>
 * To get a command instance from its name, use {@link #getByName(String)}.
 */
@WillRefactor(aspect = "name")
public final class CommandManager {
    @ApiStatus.Internal
    private CommandManager() {}

    private static final HashMap<Command, CommandMetadata> commands
                   = new HashMap<>();

    private static final AtomicBoolean frozen
                   = new AtomicBoolean(false);

    public static void add(Command cmd) {
        if (frozen.get())
            throw new TooLateException("[CommandManager#add] CommandManager was already frozen; either:" +
                                       "\n - annotate your command with @AutoCommand," +
                                       "\n - call this method in your addon's onEnable method instead.");

        var syntaxes = new SyntaxTree();
        var methods = cmd.getClass().getMethods();

        markPermission(cmd.getClass(), syntaxes);

        String lastNameRecorded = null;
        CommandHandler unknownhandler = null;

        for (var method : methods) {
            if (method.isAnnotationPresent(Syntax.class)) {
                var paramraw = method.getAnnotation(Syntax.class)
                                     .value();

                var commandLine = extractCommandLine(paramraw);

                if (lastNameRecorded != null) {
                    if (!commandLine.equals(lastNameRecorded))
                        throw new InvalidSyntaxException("Command name must be equal in all @Syntax definitions");
                } else {
                    lastNameRecorded = commandLine;
                }

                var node = new SyntaxTreeParser().parse(paramraw, commandLine, syntaxes);
                if (node.method != null)
                    throw new DuplicateSyntaxException("Syntax '%s' present twice".formatted(paramraw));

                node.method = input -> {
                    try {
                        method.invoke(cmd, input);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        var out = new StringWriter();
                        e.printStackTrace(new PrintWriter(out));
                        Avoid.LOGGER.debug("Couldn't invoke syntax method\n{}", out);

                        throw new CannotCallException("Reflection failure", e);
                    }
                };

                node.cmd = cmd;

                markPermission(method, node);
            } else if (method.isAnnotationPresent(Unknown.class)) {
                if (unknownhandler != null)
                    throw new DuplicateSyntaxException("@Unknown method present twice");

                unknownhandler = in -> {
                    try {
                        method.invoke(cmd, in);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        var out = new StringWriter();
                        e.printStackTrace(new PrintWriter(out));
                        Avoid.LOGGER.debug("Couldn't invoke @Unknown method\n{}", out);

                        throw new CannotCallException("Reflection failure", e);
                    }
                };
            }
        }

        if (lastNameRecorded == null)
            throw new NoSyntaxException("No @Syntax method present in class: '" + cmd.getClass().getName() + "'");

        var used = new ArrayList<SyntaxTree>();

        for (var method : methods) {
            if (method.isAnnotationPresent(Tabcomplete.class)) {
                var paramraw = method.getAnnotation(Tabcomplete.class)
                                     .value();

                if (method.getParameterCount() > 1)
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' has %d parameters, expected 0-1".formatted(cmd.getClass().getSimpleName(), method.getName(), method.getParameterCount()));

                var commandLine = extractCommandLine(paramraw);
                if (!commandLine.equals(lastNameRecorded))
                    throw new InvalidSyntaxException("Command name must be equal in all @Syntax definitions");

                SyntaxTree syntax;

                try {
                    syntax = new SyntaxTreeParser.Reading().parse(paramraw, commandLine, syntaxes);
                } catch (NotFoundSyntaxException e) {
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' refers to non-implemented syntax".formatted(cmd.getClass().getSimpleName(), method.getName()));
                }

                if (used.contains(syntax))
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' refers to duplicated syntax".formatted(cmd.getClass().getSimpleName(), method.getName()));

                final var lastError = new AtomicLong(-1L);

                syntax.tabcomplete = input -> {
                    try {
                        if (method.getParameterCount() == 1)
                            return (String[]) method.invoke(cmd, input);
                        else
                            return (String[]) method.invoke(cmd);
                    } catch (InvocationTargetException e) {
                        var now = System.currentTimeMillis();
                        if (now - lastError.get() > 10_000) {
                            lastError.set(now);
                        } else {
                            var out = new StringWriter();
                            e.printStackTrace(new PrintWriter(out));
                            Avoid.LOGGER.warn("Couldn't invoke tabcomplete method\n{}", out);
                        }

                        throw new CannotCallException("Invocation failure", e);
                    } catch (IllegalAccessException e) {
                        var out = new StringWriter();
                        e.printStackTrace(new PrintWriter(out));
                        Avoid.LOGGER.debug("Couldn't access tabcomplete method\n{}", out);

                        throw new CannotCallException("Reflection failure", e);
                    }
                };

                used.add(syntax);
            }
        }

        commands.put(cmd, new CommandMetadata(lastNameRecorded.substring(1), syntaxes, unknownhandler));
    }

    private static @NotNull String extractCommandLine(String paramraw) {
        var commandSpace = paramraw.indexOf(" ");
        var commandLine = commandSpace == -1
                            ? paramraw
                            : paramraw.substring(0, commandSpace);

        if (!commandLine.startsWith("/"))
            throw new InvalidSyntaxException("Syntax line must start with /  (e.g. /warp)");

        return commandLine;
    }

    private static void markPermission(AnnotatedElement element, SyntaxTree node) {
        if (element.isAnnotationPresent(PermissionLevel.class))
            node.setPermission(element.getAnnotation(PermissionLevel.class));
        else if (element.isAnnotationPresent(Permission.class))
            node.setPermission(element.getAnnotation(Permission.class));

        if (
                element.isAnnotationPresent(PermissionLevel.class) &&
                element.isAnnotationPresent(Permission.class)
        )
            throw new DuplicatePermissionDeclaration("Both @Permission and @PermissionLevel are present");
    }

    static CommandMetadata get(Command cmd) {
        return commands.get(cmd);
    }

    @Nullable
    static Command getByName(String name) {
        var keys = commands.keySet();
        for (Command key : keys)
            if (key.getName().equalsIgnoreCase(name))
                return key;

        return null;
    }

    private static void each(Consumer<Command> callback) {
        frozen.set(true);

        var cmds = commands.keySet();
        for (Command cmd : cmds)
            callback.accept(cmd);
    }
}
