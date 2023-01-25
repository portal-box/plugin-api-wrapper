package link.portalbox.type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import link.portalbox.util.JsonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpigetPlugin {

    private final int id;
    private int[] dependencies;
    private HashMap<String, Boolean> files = new HashMap<>();
    private String[] alternativeDownloads = null;

    private final String spigotName;
    private final String tag;
    private final String version;
    private String iconUrl = null;

    private int downloads = 0;
    private long updateDate = 0;
    private final double price;
    private final double rating;
    private double fileSize = 0;
    private boolean premium = false;
    private FileType fileType = null;
    private SizeUnit sizeUnit = null;
    
    private final JsonObject spigetJson;
    private final JsonObject portalboxJson;

    public SpigetPlugin(int id) {
        this.id = id;
        Gson gson = new Gson();
        spigetJson = gson.fromJson(JsonUtil.getSpigetJson(id), JsonObject.class);
        portalboxJson = gson.fromJson(JsonUtil.getPortalBoxPluginJson(id), JsonObject.class);


        this.spigotName = spigetJson.get("name").getAsString();
        this.tag = spigetJson.get("tag").getAsString();
        this.version = spigetJson.get("version").getAsString();
        this.downloads = spigetJson.get("downloads").getAsInt();
        this.updateDate = spigetJson.get("updateDate").getAsLong();
        this.price = spigetJson.get("price").getAsDouble();
        this.rating = spigetJson.get("rating").getAsJsonObject().get("average").getAsDouble();

        try {
            this.premium = spigetJson.get("premium").getAsBoolean();
        } catch (NullPointerException exception) {
            this.premium = false;
        }

        this.fileSize = spigetJson.get("file").getAsJsonObject().get("size").getAsDouble();

        String url = spigetJson.get("icon").getAsJsonObject().get("url").getAsString();
        this.iconUrl = url.isEmpty() ? "https://i.imgur.com/V9jfjSJ.png" : "https://www.spigotmc.org/" + spigetJson.get("icon").getAsJsonObject().get("url").getAsString();

        String sizeUnit = spigetJson.get("file").getAsJsonObject().get("sizeUnit").getAsString();
        this.sizeUnit = sizeUnit.isEmpty() ? SizeUnit.NONE : SizeUnit.valueOf(sizeUnit);

        switch (spigetJson.get("file").getAsJsonObject().get("type").getAsString().toLowerCase()) {
            case ".jar" -> this.fileType = FileType.JAR;
            case ".zip" -> this.fileType = FileType.ZIP;
            case ".sk" -> this.fileType = FileType.SKRIPT;
            default -> this.fileType = FileType.EXTERNAL; // Includes "external"
        }

        try {
        // Portal Box Plugin Information Below
        dependencies = gson.fromJson(portalboxJson.get("dependencies"), new TypeToken<LinkedList<Integer>>() {}.getType());
        alternativeDownloads = gson.fromJson(portalboxJson.get("altDownloads"), new TypeToken<LinkedList<String>>() {}.getType());

            for (Map.Entry<String, JsonElement> entry : portalboxJson.get("files").getAsJsonObject().entrySet()) {
                files.put(entry.getKey(), entry.getValue().getAsBoolean());
            }
        } catch (Exception ignored) {}

    }

    public int getId() { return id; }
    public int[] getDependencies() { return dependencies; }
    public HashMap<String, Boolean> getFiles() { return files; }
    public String[] getAlternativeDownloads() { return alternativeDownloads; }
    public String getSpigotName() { return spigotName; }
    public String getTag() { return tag; }
    public String getVersion() { return version; }
    public String getIconUrl() { return iconUrl; }
    public int getDownloads() { return downloads; }
    public long getUpdateDate() { return updateDate; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public double getFileSize() { return fileSize; }
    public boolean isPremium() { return premium; }
    public FileType getFileType() { return fileType; }
    public SizeUnit getSizeUnit() { return sizeUnit; }
    public JsonObject getSpigetJson() { return spigetJson; }
    public JsonObject getPortalboxJson() { return portalboxJson; }

}
