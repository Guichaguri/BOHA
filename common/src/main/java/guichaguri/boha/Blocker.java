package guichaguri.boha;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class Blocker {

    public static final String VERSION = "1.0.3";

    public static final String DEFAULT_MSG = "&cYou are using a hacked account.";
    public static final int DEFAULT_CACHE_MAX_TIME = 3600;
    public static final String URL_BASE = "https://doha.blueslime.fr/api/check/";

    public static String MESSAGE = DEFAULT_MSG;
    public static int CACHE_MAX_TIME = DEFAULT_CACHE_MAX_TIME;

    private static final Map<UUID, Boolean> CACHE = new HashMap<UUID, Boolean>();
    private static long CACHE_TIME = 0;

    public static URL getUrl(UUID uuid) throws MalformedURLException {
        return new URL(URL_BASE + uuid.toString());
    }

    public static void setBlocked(UUID uuid, boolean blocked) {
        CACHE.put(uuid, blocked);
    }

    public static boolean isBlockedCache(UUID uuid) {
        if(CACHE_MAX_TIME == 0) return false;

        long currentTime = System.currentTimeMillis();
        if(currentTime > CACHE_TIME + (CACHE_MAX_TIME * 1000)) {
            CACHE.clear();
            CACHE_TIME = currentTime;
            return false;
        }

        Boolean blocked = CACHE.get(uuid);
        return blocked != null && blocked;
    }

    public static String translateChatColors(String str) {
        return str.replaceAll("(?i)&([0-9A-FK-OR])", "\u00A7$1");
    }

}
