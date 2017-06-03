package guichaguri.boha.logic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import guichaguri.boha.Blocker;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.UUID;

/**
 * @author Guilherme Chaguri
 */
public class GongAPIChecker extends CachedChecker {

    private final String apiBaseUrl = "https://mcleaks.themrgong.xyz/api/v3/isuuidmcleaks/";

    private final JsonParser parser = new JsonParser();

    public GongAPIChecker(boolean cacheEnabled, int cacheTimeout) {
        super(cacheEnabled, cacheTimeout);
    }

    @Override
    public boolean check(UUID uuid) {
        Reader reader = null;
        try {
            URL url = new URL(apiBaseUrl + uuid.toString());

            reader = new InputStreamReader(url.openStream());
            JsonObject data = parser.parse(reader).getAsJsonObject();

            return data.get("isMcleaks").getAsBoolean();
        } catch(Exception ex) {
            return false;
        } finally {
            Blocker.closeQuietly(reader);
        }
    }
}
