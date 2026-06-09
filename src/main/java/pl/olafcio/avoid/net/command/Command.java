package pl.olafcio.avoid.net.command;

import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.net.command.handling.CommandHandler;

/**
 * Specifies the extending class is ready to register as a server command.
 */
@ApiStatus.Experimental
public abstract class Command {
    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final String getName() {
        return CommandManager.get(this).name();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final SyntaxTree getSyntaxTree() {
        return CommandManager.get(this).syntaxes();
    }

    /**
     * <b>Note:</b> This method works only after registering the class.
     */
    public final CommandHandler getUnknownHandler() {
        return CommandManager.get(this).unknownhandler();
    }
}
