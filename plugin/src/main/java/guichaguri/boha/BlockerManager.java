package guichaguri.boha;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import guichaguri.boha.logic.APIChecker;
import guichaguri.boha.logic.DatabaseChecker;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    private static JsonObject createConfig(File configFile) {
        JsonObject config = Json.object();
        config.add("configVersion", Blocker.CONFIG_VERSION);
        config.add("message", Blocker.DEFAULT_MSG);

        JsonObject cache = Json.object();
        cache.add("enabled", true);
        cache.add("timeout", Blocker.DEFAULT_CACHE_TIMEOUT);
        config.add("cache", cache);

        JsonObject database = Json.object();
        database.add("enabled", true);
        database.add("interval", Blocker.DEFAULT_DB_INTERVAL);
        config.add("database", database);

        FileWriter writer = null;
        try {
            configFile.getParentFile().mkdirs();
            writer = new FileWriter(configFile);
            config.writeTo(writer, WriterConfig.PRETTY_PRINT);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            BlockerManager.closeQuietly(writer);
        }
        return config;
    }

    public static void loadConfig(File configFile, File databaseFile) {
        JsonObject config;

        FileReader reader = null;
        try {
            reader = new FileReader(configFile);
            config = Json.parse(reader).asObject();

            if(config.getInt("configVersion", -1) != Blocker.CONFIG_VERSION) {
                config = createConfig(configFile);
            }
        } catch(Exception ex) {
            config = createConfig(configFile);
        } finally {
            BlockerManager.closeQuietly(reader);
        }

        Blocker.MESSAGE = Blocker.translateChatColors(config.getString("message", Blocker.DEFAULT_MSG));

        JsonObject cache = config.get("cache").asObject();
        boolean cacheEnabled = cache.getBoolean("enabled", true);
        int cacheTimeout = cache.getInt("timeout", Blocker.DEFAULT_CACHE_TIMEOUT);

        JsonObject database = config.get("database").asObject();
        boolean databaseEnabled = database.getBoolean("enabled", true);
        int databaseInterval = database.getInt("interval", Blocker.DEFAULT_DB_INTERVAL);

        Blocker.CHECKER = new APIChecker(cacheEnabled, cacheTimeout);

        if(databaseEnabled) {
            DatabaseChecker checker = new DatabaseChecker(databaseFile, databaseInterval);
            if(checker.hasData()) Blocker.CHECKER = checker;
        }
    }

    public static void closeQuietly(Closeable c) {
        try {
            if(c != null) c.close();
        } catch(IOException e) {
            // Quietly
        }
    }

}
