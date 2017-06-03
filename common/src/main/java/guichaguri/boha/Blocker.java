package guichaguri.boha;

import com.google.gson.Gson;
import guichaguri.boha.logic.IChecker;
import java.io.Closeable;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class Blocker {

    public static final String VERSION = "1.0.5";

    public static final String DEFAULT_MSG = "&cYou are using a hacked account.";
    public static final int DEFAULT_CACHE_TIMEOUT = 3600;
    public static final int DEFAULT_DB_INTERVAL = 24;

    public static final Gson GSON = new Gson();

    public static String MESSAGE = DEFAULT_MSG;

    public static IChecker[] CHECKER = new IChecker[0];

    public static boolean isBlocked(UUID uuid) {
        for(int i = 0; i < CHECKER.length; i++) {
            if(CHECKER[i].isBlocked(uuid)) return true;
        }
        return false;
    }

    public static String translateChatColors(String str) {
        return str.replaceAll("(?i)&([0-9A-FK-OR])", "\u00A7$1");
    }

    public static void closeQuietly(Closeable c) {
        try {
            if(c != null) c.close();
        } catch(IOException e) {
            // Quietly
        }
    }


}
