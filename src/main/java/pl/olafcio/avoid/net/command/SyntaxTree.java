package pl.olafcio.avoid.net.command;

import pl.olafcio.avoid.annotations.refactor.NeverRemoval;
import pl.olafcio.avoid.net.command.annotation.Permission;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;
import pl.olafcio.avoid.net.command.handling.CommandHandler;
import pl.olafcio.avoid.net.command.parameter.CommandParameter;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

/**
 * The syntax tree of a command.
 * <br/><br/>
 * Because of how Minecraft's Command API (Brigadier) works, the internals of AvoidLib have to
 * construct a tree of commands' syntaxes.
 * <br/><br/>
 * <b>For example:</b>
 * <pre>
 *     <code>/echo</code>
 *     <code>🔽</code>
 *     <code>&lt;string&gt;</code>
 *     <code>🔽</code>
 *     <code>&lt;int&gt;</code>
 * </pre>
 *
 * <p>That translates to {@code /echo <string> <int>}.</p>
 * <br/>
 * <b>Here's <i>approximately</i> how you would represent it with code (in Brigadier):</b>
 * <br/><br/>
 * <code>
 *     <pre>
 *       var cmd = Commands.literal("echo")
 *                         .then(new StringArgument("string")
 *                              .then(new IntArgument("int")));
 *     </pre>
 * </code>
 * <br/>
 * <p style="color: #585e79">(notice the indentation)</p>
 */
@NeverRemoval
public final class SyntaxTree extends LinkedHashMap<CommandParameter<?>, SyntaxTree> {
    public CommandHandler method;
    public Command cmd;

    private Annotation permission;

    public SyntaxTree() {
        super();

        this.method = null;
        this.permission = null;
    }

    public SyntaxTree(CommandHandler method) {
        super();

        this.method = method;
        this.permission = null;
    }

    public boolean isNodeExecutable() {
        return method != null;
    }

    public void setPermission(Permission perm) {
        this.permission = perm;
    }

    public void setPermission(PermissionLevel perm) {
        this.permission = perm;
    }

    public Annotation getPermission() {
        return permission;
    }
}
