package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid.net.command.exception.InvalidSyntaxException;
import pl.olafcio.avoid.net.command.exception.SyntaxInitException;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;
import pl.olafcio.avoid.net.command.parameter.CommandParameters;
import pl.olafcio.avoid.net.command.parameter.impl.LiteralParameter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

sealed class SyntaxTreeParser {
    protected SyntaxTree node;
    protected ArrayList<String> taken;
    protected StringBuilder value;

    SyntaxTree parse(String paramraw, String commandLine, SyntaxTree syntaxes) {
        var paramch = paramraw.substring(commandLine.length())
                              .toCharArray();

        node = syntaxes;
        taken = new ArrayList<>();

        value = new StringBuilder();
        var inTag = false;

        Character prev = null;

        for (char ch : paramch) {
            if (inTag) {
                if (ch == '>') {
                    CommandParameter<?> param = parseParameter(value);

                    intoParameter(param);

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
                processLiteralParameter();
            } else {
                value.append(ch);
            }

            prev = ch;
        }

        if (!inTag) {
            processLiteralParameter();
        }

        return node;
    }

    protected void intoParameter(CommandParameter<?> param) {
        node = node.computeIfAbsent(param, n -> new SyntaxTree());
    }

    protected void processLiteralParameter() {
        if (!value.isEmpty()) {
            var paramName = value.toString();

            if (taken.contains(paramName))
                throw new InvalidSyntaxException("Parameter name already taken");

            taken.add(paramName);

            node = node.compute(new LiteralParameter(paramName), (x, y) -> new SyntaxTree());

            value.setLength(0);
        }
    }

    protected @NotNull CommandParameter<?> parseParameter(StringBuilder value) {
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

        paramName = transformParameterName(paramName, renamed);

        taken.add(paramName);

        CommandParameter<?> param;

        try {
            param = CommandParameters.queryTagConstructor(tagName)
                                     .newInstance(paramName);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new SyntaxInitException("Failed to construct tag '%s'".formatted(tagName), e);
        }

        return param;
    }

    protected String transformParameterName(String paramName, boolean renamed) {
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

        return paramName;
    }

    static final class Reading extends SyntaxTreeParser {
        @Override
        protected void intoParameter(CommandParameter<?> param) {
            for (var entry : node.entrySet()) {
                if (entry.getKey().getName().equals(param.getName())) {
                    if (entry.getKey().getClass() != param.getClass())
                        throw new NotFoundSyntaxException("Incorrectly typed parameter '%s'".formatted(entry.getKey().getName()));

                    node = entry.getValue();

                    return;
                }
            }

            throw new NotFoundSyntaxException("Parameter '%s' not found".formatted(param.getName()));
        }

        @Override
        protected void processLiteralParameter() {
            if (!value.isEmpty()) {
                var paramName = value.toString();

                if (taken.contains(paramName))
                    throw new InvalidSyntaxException("Parameter name already taken");

                taken.add(paramName);

                finding:
                {
                    for (var entry : node.entrySet()) {
                        if (entry.getKey().getName().equals(paramName)) {
                            if (!(entry.getKey() instanceof LiteralParameter))
                                throw new NotFoundSyntaxException("Incorrectly typed parameter '%s'".formatted(entry.getKey().getName()));

                            node = entry.getValue();
                            break finding;
                        }
                    }

                    throw new NotFoundSyntaxException("Parameter '%s' not found".formatted(paramName));
                }

                value.setLength(0);
            }
        }
    }
}
