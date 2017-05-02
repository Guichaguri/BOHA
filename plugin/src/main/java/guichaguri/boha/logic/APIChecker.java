package guichaguri.boha.logic;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import guichaguri.boha.Blocker;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class APIChecker extends CachedChecker {

    public APIChecker(boolean cacheEnabled, int cacheTimeout) {
        super(cacheEnabled, cacheTimeout);
    }

    @Override
    public boolean check(UUID uuid) {
        try {
            URL url = Blocker.getUrl(uuid);

            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject data = Json.parse(reader).asObject();

            return data.getBoolean("exists", false);
        } catch(Exception ex) {
            return false;
        }
    }
}
