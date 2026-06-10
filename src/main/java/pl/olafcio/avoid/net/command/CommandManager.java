package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pl.olafcio.avoid.annotations.refactor.WillRefactor;
import pl.olafcio.avoid.net.command.annotation.Syntax;
import pl.olafcio.avoid.net.command.annotation.Unknown;
import pl.olafcio.avoid.net.command.exception.DuplicateSyntaxException;
import pl.olafcio.avoid.net.command.exception.InvalidSyntaxException;
import pl.olafcio.avoid.net.command.exception.SyntaxInitException;
import pl.olafcio.avoid.net.command.exception.late.TooLateException;
import pl.olafcio.avoid.net.command.exception.use.CannotCallException;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.CommandParameters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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

        String lastNameRecorded = null;
        CommandHandler unknownhandler = null;

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
                            var tagType = CommandParameters.queryTag(tagName);

                            if (tagType == null)
                                throw new InvalidSyntaxException("Unrecognized tag '%s'".formatted(tagName));

                            var paramName = tagName;
                            if (taken.contains(paramName)) {
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
                    }

                    prev = ch;
                }

                if (node.method != null)
                    throw new DuplicateSyntaxException("Syntax '%s' present twice".formatted(paramraw));

                node.method = input -> {
                    try {
                        method.invoke(cmd, input);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new CannotCallException("Reflection failure", e);
                    }
                };

                node.cmd = cmd;
            } else if (method.isAnnotationPresent(Unknown.class)) {
                if (unknownhandler != null)
                    throw new DuplicateSyntaxException("@Unknown method present twice");

                unknownhandler = in -> {
                    try {
                        method.invoke(cmd, in);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new CannotCallException("Reflection failure", e);
                    }
                };
            }
        }

        commands.put(cmd, new CommandMetadata(lastNameRecorded.substring(1), syntaxes, unknownhandler));
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
