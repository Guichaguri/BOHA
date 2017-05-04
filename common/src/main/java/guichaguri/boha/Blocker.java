package guichaguri.boha;

import guichaguri.boha.logic.IChecker;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class Blocker {

    public static final String VERSION = "1.0.5";
    public static final int CONFIG_VERSION = 2;

    public static final String DEFAULT_MSG = "&cYou are using a hacked account.";
    public static final int DEFAULT_CACHE_TIMEOUT = 3600;
    public static final int DEFAULT_DB_INTERVAL = 24;

    public static final String API_URL_BASE = "https://doha.blueslime.fr/api/check/";
    public static final String DB_URL = "https://raw.githubusercontent.com/IamBlueSlime/DOHA/master/db.json";

    public static String MESSAGE = DEFAULT_MSG;

    public static IChecker CHECKER = null;

    public static URL getUrl(UUID uuid) throws MalformedURLException {
        return new URL(API_URL_BASE + uuid.toString());
    }

    public static boolean isBlocked(UUID uuid) {
        return CHECKER != null && CHECKER.isBlocked(uuid);
    }

    public static String translateChatColors(String str) {
        return str.replaceAll("(?i)&([0-9A-FK-OR])", "\u00A7$1");
    }

}
