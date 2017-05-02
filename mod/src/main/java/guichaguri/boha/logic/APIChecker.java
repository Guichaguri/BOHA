package guichaguri.boha.logic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import guichaguri.boha.Blocker;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

/**
 * @author Guilherme Chaguri
 */
public class APIChecker extends CachedChecker {

    private final JsonParser parser = new JsonParser();

    public APIChecker(boolean cacheEnabled, int timeout) {
        super(cacheEnabled, timeout);
    }

    @Override
    public boolean check(UUID uuid) {
        Reader reader = null;
        try {
            URL url = Blocker.getUrl(uuid);

            reader = new InputStreamReader(url.openStream());
            JsonObject data = parser.parse(reader).getAsJsonObject();

            return data.get("exists").getAsBoolean();
        } catch(Exception ex) {
            return false;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

}
