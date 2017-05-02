package guichaguri.boha;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guichaguri.boha.logic.APIChecker;
import guichaguri.boha.logic.DatabaseChecker;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Logger LOG = LogManager.getLogger("BOHA");

    public static void loadConfig(File configFile, File databaseFile) {
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

        FileWriter writer = null;
        try {
            configFile.getParentFile().mkdirs();
            writer = new FileWriter(configFile);
            GSON.toJson(config, writer);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }


        Blocker.MESSAGE = Blocker.translateChatColors(config.message);
        Blocker.CHECKER = new APIChecker(config.cache.enabled, config.cache.timeout);

        if(config.database.enabled) {
            DatabaseChecker checker = new DatabaseChecker(databaseFile, config.database.interval);
            if(checker.hasData()) Blocker.CHECKER = checker;
        }
    }

    private static class Config {
        int configVersion = Blocker.CONFIG_VERSION;
        String message = Blocker.DEFAULT_MSG;
        Cache cache = new Cache();
        Database database = new Database();
    }

    private static class Cache {
        boolean enabled = true;
        int timeout = Blocker.DEFAULT_CACHE_TIMEOUT;
    }

    private static class Database {
        boolean enabled = true;
        int interval = Blocker.DEFAULT_DB_INTERVAL;
    }

}
