package guichaguri.dohablocker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Logger LOG = LogManager.getLogger("DOHA-Blocker");

    public static void loadConfig(File configFile) {
        LOG.debug("Loading config file...");

        Config config;
        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
            config = GSON.fromJson(reader, Config.class);
        } catch(Exception ex) {
            config = null;
        } finally {
            IOUtils.closeQuietly(reader);
        }

        if(config == null) config = new Config();

        Blocker.MESSAGE = config.message;
        Blocker.CACHE_MAX_TIME = config.cacheMaxTime;

        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }

            FileUtils.writeStringToFile(configFile, GSON.toJson(config));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static boolean check(UUID uuid) throws IOException {
        URL url = Blocker.getUrl(uuid);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        Check check = GSON.fromJson(reader, Check.class);

        if(check.exists) Blocker.addToCache(uuid);
        return check.exists;
    }

    public static boolean isBlocked(UUID uuid) {
        if(Blocker.isBlockedCache(uuid)) return true;

        try {
            return check(uuid);
        } catch(Exception ex) {
            return false;
        }
    }

    private static class Config {
        public String message = Blocker.DEFAULT_MSG;
        public int cacheMaxTime = Blocker.DEFAULT_CACHE_MAX_TIME;
    }

    private static class Check {
        public boolean exists = false;
    }

}
