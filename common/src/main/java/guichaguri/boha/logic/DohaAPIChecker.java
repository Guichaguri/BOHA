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
public class DohaAPIChecker extends CachedChecker {

    private final String apiBaseUrl = "https://doha.blueslime.fr/api/check/";

    private final JsonParser parser = new JsonParser();

    public DohaAPIChecker(boolean cacheEnabled, int timeout) {
        super(cacheEnabled, timeout);
    }

    @Override
    public boolean check(UUID uuid) {
        Reader reader = null;
        try {
            URL url = new URL(apiBaseUrl + uuid.toString());

            reader = new InputStreamReader(url.openStream());
            JsonObject data = parser.parse(reader).getAsJsonObject();

            return data.get("exists").getAsBoolean();
        } catch(Exception ex) {
            return false;
        } finally {
            Blocker.closeQuietly(reader);
        }
    }

}
