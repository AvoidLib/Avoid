package pl.olafcio.avoid.net.command.parameter;

/**
 * Defines whether trying overloads should be considered when the argument value could not be parsed.
 * <br/><br/>
 * When a command has multiple (more than 1) overloads with the same argument amount,
 * normally each of them is trying to be parsed through the index-appropriate overloads' argument.
 * <br/><br/>
 * This behavior, however, may be overwritten (by using this enum).<br/>
 * When you set the return value of {@link CommandParameter#shouldParse()} to {@code ShouldParse.YES},
 * a warning will be thrown if the value couldn't be parsed, and overloading will be halted.
 */
public enum ShouldParse {
    YES,
    TRY
}
