package guichaguri.dohablocker;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class Blocker {

    public static final String VERSION = "1.0.1";

    public static final String DEFAULT_MSG = "\u00a7cYou can't login using a hacked account.";
    public static final int DEFAULT_CACHE_MAX_TIME = 3600;
    public static final String URL_BASE = "https://doha.blueslime.fr/api/check/";

    public static String MESSAGE = DEFAULT_MSG;
    public static int CACHE_MAX_TIME = DEFAULT_CACHE_MAX_TIME;

    private static final List<UUID> CACHE = new ArrayList<UUID>();
    private static long CACHE_TIME = 0;

    public static URL getUrl(UUID uuid) throws MalformedURLException {
        return new URL(URL_BASE + uuid.toString());
    }

    public static void addToCache(UUID uuid) {
        CACHE.add(uuid);
    }

    public static boolean isBlockedCache(UUID uuid) {
        if(CACHE_MAX_TIME == 0) return false;

        long currentTime = System.currentTimeMillis();
        if(currentTime > CACHE_TIME + (CACHE_MAX_TIME * 1000)) {
            CACHE.clear();
            CACHE_TIME = currentTime;
            return false;
        }

        return CACHE.contains(uuid);
    }

}
