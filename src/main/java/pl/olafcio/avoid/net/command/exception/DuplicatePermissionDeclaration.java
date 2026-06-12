package pl.olafcio.avoid.net.command.exception;

import pl.olafcio.avoid.net.command.annotation.Permission;
import pl.olafcio.avoid.net.command.annotation.PermissionLevel;

/**
 * An exception thrown when both <a style="color: #3887a1">@{@linkplain Permission}</a> and <a style="color: #3887a1">@{@linkplain PermissionLevel}</a> are present.
 */
public class DuplicatePermissionDeclaration extends RuntimeException {
    public DuplicatePermissionDeclaration(String message) {
        super(message);
    }
}
