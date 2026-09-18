package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.Avoid;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.command.annotation.*;
import pl.olafcio.avoid.net.command.exception.*;
import pl.olafcio.avoid.net.command.exception.late.TooLateException;
import pl.olafcio.avoid.net.command.exception.use.CannotCallException;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.CommandParameters;
import pl.olafcio.avoid.net.command.parameter.impl.LiteralParameter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
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

        var flat = new HashMap<String, SyntaxTree>();

        for (var method : methods) {
            if (method.isAnnotationPresent(Syntax.class)) {
                var paramraw = method.getAnnotation(Syntax.class)
                                     .value();

                var commandSpace = paramraw.indexOf(" ");
                var commandLine = commandSpace == -1
                                    ? paramraw
                                    : paramraw.substring(0, commandSpace);

                if (!commandLine.startsWith("/"))
                    throw new InvalidSyntaxException("Syntax line must start with /  (e.g. /warp)");

                if (lastNameRecorded != null && !commandLine.equals(lastNameRecorded))
                    throw new InvalidSyntaxException("Command name must be equal in all @Syntax definitions");

                lastNameRecorded = commandLine;

                var paramch = paramraw.substring(commandLine.length())
                                      .toCharArray();

                var node = syntaxes;
                var taken = new ArrayList<String>();

                var value = new StringBuilder();
                var inTag = false;

                Character prev = null;

                for (char ch : paramch) {
                    if (inTag) {
                        if (ch == '>') {
                            var tagName = value.toString();
                            var paramName = tagName;

                            boolean renamed = false;

                            if (tagName.contains(" = ")) {
                                var split = tagName.split(" = ", 2);

                                tagName = split[1];
                                paramName = split[0];

                                if (!paramName.startsWith("'") || !paramName.endsWith("'"))
                                    throw new InvalidSyntaxException("Parameter name must be surrounded with 'apostrophes'");

                                paramName = paramName.substring(1, paramName.length() - 1);
                                renamed = true;
                            }

                            var tagType = CommandParameters.queryTag(tagName);
                            if (tagType == null)
                                throw new InvalidSyntaxException("Unrecognized tag '%s'".formatted(tagName));

                            if (taken.contains(paramName)) {
                                if (renamed)
                                    throw new InvalidSyntaxException("Parameter name already taken");

                                int i = 2;

                                while (true) {
                                    String newName = paramName + "-" + (i++);

                                    if (!taken.contains(newName)) {
                                        paramName = newName;
                                        break;
                                    }
                                }
                            }

                            taken.add(paramName);

                            CommandParameter<?> param;

                            try {
                                param = CommandParameters.queryTagConstructor(tagName)
                                                         .newInstance(paramName);
                            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                                throw new SyntaxInitException("Failed to construct tag '%s'".formatted(tagName), e);
                            }

                            node = node.computeIfAbsent(param, n -> new SyntaxTree());

                            inTag = false;
                            value.setLength(0);
                        } else {
                            value.append(ch);
                        }
                    } else if (ch == '<') {
                        if (!Objects.equals(prev, ' '))
                            throw new InvalidSyntaxException("Each parameter must be preceded by a space");

                        inTag = true;
                    } else if (ch == ' ') {
                        if (!value.isEmpty()) {
                            var paramName = value.toString();

                            if (taken.contains(paramName))
                                throw new InvalidSyntaxException("Parameter name already taken");

                            taken.add(paramName);

                            node = node.compute(new LiteralParameter(paramName), (x, y) -> new SyntaxTree());

                            value.setLength(0);
                        }
                    } else {
                        value.append(ch);
                    }

                    prev = ch;
                }

                if (!inTag) {
                    if (!value.isEmpty()) {
                        var paramName = value.toString();

                        if (taken.contains(paramName))
                            throw new InvalidSyntaxException("Parameter name already taken");

                        taken.add(paramName);

                        node = node.compute(new LiteralParameter(paramName), (x, y) -> new SyntaxTree());

                        value.setLength(0);
                    }
                }

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

                flat.put(paramraw, node);
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

        for (var method : methods) {
            if (method.isAnnotationPresent(Tabcomplete.class)) {
                var paramraw = method.getAnnotation(Tabcomplete.class)
                                     .value();

                if (method.getParameterCount() > 1)
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' has %d parameters, expected 0-1".formatted(cmd.getClass().getSimpleName(), method.getName(), method.getParameterCount()));

                if (!flat.containsKey(paramraw))
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' refers to non-implemented syntax".formatted(cmd.getClass().getSimpleName(), method.getName()));

                if (flat.get(paramraw) == null)
                    throw new InvalidMethodException("@Tabcomplete method '%s#%s' refers to duplicated syntax".formatted(cmd.getClass().getSimpleName(), method.getName()));

                flat.get(paramraw).tabcomplete = input -> {
                    try {
                        if (method.getParameterCount() == 1)
                            return (String[]) method.invoke(cmd, input);
                        else
                            return (String[]) method.invoke(cmd);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        var out = new StringWriter();
                        e.printStackTrace(new PrintWriter(out));
                        Avoid.LOGGER.debug("Couldn't invoke tabcomplete method\n{}", out);

                        throw new CannotCallException("Reflection failure", e);
                    }
                };

                flat.put(paramraw, null);
            }
        }

        commands.put(cmd, new CommandMetadata(lastNameRecorded.substring(1), syntaxes, unknownhandler));
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
