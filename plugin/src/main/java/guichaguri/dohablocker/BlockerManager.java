package guichaguri.dohablocker;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class BlockerManager {

    private static JsonObject createConfig(File configFile) {
        JsonObject config = Json.object();
        config.add("message", Blocker.DEFAULT_MSG);
        config.add("cacheMaxTime", Blocker.DEFAULT_CACHE_MAX_TIME);

        try {
            if(!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }
            FileWriter writer = new FileWriter(configFile);
            config.writeTo(writer, WriterConfig.PRETTY_PRINT);
            writer.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return config;
    }

    public static void loadConfig(File configFile) {
        JsonObject config;
        try {
            config = Json.parse(new FileReader(configFile)).asObject();
        } catch(Exception ex) {
            config = createConfig(configFile);
        }

        Blocker.MESSAGE = Blocker.translateChatColors(config.getString("message", Blocker.DEFAULT_MSG));
        Blocker.CACHE_MAX_TIME = config.getInt("cacheMaxTime", Blocker.DEFAULT_CACHE_MAX_TIME);
    }

    private static boolean check(UUID uuid) throws IOException {
        URL url = Blocker.getUrl(uuid);

        InputStreamReader reader = new InputStreamReader(url.openStream());
        JsonObject data = Json.parse(reader).asObject();

        boolean blocked = data.getBoolean("exists", false);
        Blocker.setBlocked(uuid, blocked);
        return blocked;
    }

    public static boolean isBlocked(UUID uuid) {
        if(Blocker.isBlockedCache(uuid)) return true;

        try {
            return check(uuid);
        } catch(Exception ex) {
            return false;
        }
    }

}
