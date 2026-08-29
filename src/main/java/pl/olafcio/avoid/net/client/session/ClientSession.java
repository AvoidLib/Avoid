package pl.olafcio.avoid.net.client.session;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import pl.olafcio.avoid.AvoidWrappedLoader;
import pl.olafcio.avoid.ImproperEnvironment;
import pl.olafcio.avoid.RunningEnv;

import java.util.UUID;

/**
 * A namespace for managing the player session.
 * <br/><br/>
 * <hr/>
 * <h1>Sessions</h1>
 * A session is an account log-in state.<br/><br/>
 * When you start Minecraft with your favourite launcher,<br/>
 * that launcher checks if the cached <i><u>access token¹</u></i> is still active.<br/><br/>
 * If not, it gets a new one with the <i><u>refresh token²</u></i>.
 * <br/><br/>
 * <h2>¹Access Tokens</h2>
 * Access tokens are long internal passwords used to identify your account.<br/>
 * They are not related to your Microsoft account password.<br/>
 * Access tokens expire. To get a new one, the <i>refresh token</i> has to be used.
 * <br/><br/>
 * <h2>²Refresh Tokens</h2>
 * Refresh tokens are, also, another type of long internal passwords.<br/>
 * They don't identify your account directly - rather, provide a way to get a new access token.<br/>
 * A refresh token expires only if not used in a long enough time.
 * <br/><br/>
 * <hr/>
 * <h1>XBOX Authentication</h1>
 * To get a refresh token, the user has to authenticate themselves to an app, that upon succesful authentication, will
 * provide the program a refresh token.<br/><br/>
 * The easiest way to do this is:
 * <ol>
 *     <li>host an HTTP server on localhost,</li>
 *     <li>direct the user to a prompt to authenticate an app, that has the redirect uri at localhost,</li>
 *     <li>finally, when the user authenticates, your http server will receive a refresh token.</li>
 * </ol>
 * <br/>
 * Congrats! You've read the whole class doc.
 * <br/><br/>
 * <b>NOTE:</b> This may be a little bit wrong; I've never done XBOX flow myself.<br/>
 * &emsp;&emsp;&ensp;&ensp;&nbsp;However, I know how some of this stuff works.
 */
@NullMarked
@ApiStatus.Experimental
public final class ClientSession {
    @ApiStatus.Internal
    private ClientSession() {}

    /**
     * Returns the active session player's nickname.
    */
    public static String getNick() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#getNick() on the server");

        return ClientSessionNative.getNick();
    }

    /**
     * Returns the active session player's UUID (universally unique identifier).<br/>
     * This persists through name changes.
     */
    public static UUID getUUID() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#getUUID() on the server");

        return ClientSessionNative.getUUID();
    }

    /**
     * Returns the active session player's access token.<br/>
     * For more info, see: {@link ClientSession}
     */
    public static String getAccessToken() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#getAccessToken() on the server");

        return ClientSessionNative.getAccessToken();
    }

    /**
     * Returns the active session player's client ID (?).
     */
    @Nullable
    public static String getClientID() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#getClientID() on the server");

        return ClientSessionNative.getClientID();
    }

    /**
     * Returns the active session player's xuid (?).
     */
    @Nullable
    public static String getXuid() {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#getXuid() on the server");

        return ClientSessionNative.getXuid();
    }

    /**
     * Sets the active session player's nickname.<br/><br/>
     * <b>NOTE:</b> If you don't update the UUID and/or access token accordingly,<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;your account will be in a broken session state -<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;resulting in a kind-of cracked account.
     */
    public static void setNick(String value) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#setNick() on the server");

        ClientSessionNative.setNick(value);
    }

    /**
     * Sets the active session player's UUID (universally unique identifier).<br/><br/>
     * <b>NOTE:</b> If you don't update the nick and/or access token accordingly,<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;your account will be in a broken session state -<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;resulting in a kind-of cracked account.
     */
    public static void setUUID(UUID value) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#setUUID() on the server");

        ClientSessionNative.setUUID(value);
    }

    /**
     * Sets the active session player's access token (universally unique identifier).<br/><br/>
     * <b>NOTE:</b> If you don't update the nick and/or UUID accordingly,<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;your account will be in a broken session state -<br/>
     * &emsp;&emsp;&ensp;&ensp;&nbsp;resulting in a kind-of cracked account.
     */
    public static void setAccessToken(String value) {
        if (AvoidWrappedLoader.getRunningEnvironment() != RunningEnv.CLIENT)
            throw new ImproperEnvironment("Cannot ClientSession#setAccessToken() on the server");

        ClientSessionNative.setAccessToken(value);
    }
}
