package guichaguri.dohablocker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class Blocker {

    public static final String VERSION = "1.0.1";

    public static final String DEFAULT_MSG = "§cYou can't login using a hacked account.";
    public static final String URL_BASE = "https://doha.blueslime.fr/api/check/";

    public static URL getUrl(UUID uuid) throws MalformedURLException {
        return new URL(URL_BASE + uuid.toString());
    }

}
