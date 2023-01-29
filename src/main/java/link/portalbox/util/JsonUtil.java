package link.portalbox.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtil {

    private JsonUtil() throws IllegalAccessException {
        throw new IllegalAccessException("No JsonUtil instances for you!");
    }

    public static String getJson(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPortalBoxPluginJson(int id) {
        return getJson("https://api.portalbox.link/plugins/" + id);
    }

    public static String getSpigetJson(int id) {
        return getJson("https://api.spiget.org/v2/resources/" + id);
    }

    public static String getPluginIndex() {
        return getJson("https://api.portalbox.link/index");
    }
}