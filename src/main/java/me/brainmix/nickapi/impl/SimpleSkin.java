package me.brainmix.nickapi.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.brainmix.nickapi.api.Skin;
import me.brainmix.nickapi.api.SkinType;
import me.brainmix.nickapi.skin.SkinFetcher;

import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class SimpleSkin implements Skin {

    private UUID uuid;
    private String name;
    private long timeStamp;
    private boolean isPublic;
    private SkinType skinType;
    private URL skinURL;
    private URL capeURL;

    public SimpleSkin(UUID uuid, String name, long timeStamp, boolean isPublic, SkinType skinType, URL skinURL, URL capeURL) {
        this.uuid = uuid;
        this.name = name;
        this.timeStamp = timeStamp;
        this.isPublic = isPublic;
        this.skinType = skinType;
        this.skinURL = skinURL;
        this.capeURL = capeURL;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean isPublic() {
        return isPublic;
    }

    @Override
    public SkinType getSkinType() {
        return skinType;
    }

    @Override
    public URL getSkinURL() {
        return skinURL;
    }

    @Override
    public URL getCapeURL() {
        return capeURL;
    }

    public String toJSON() {
        JsonObject textures = new JsonObject();
        textures.addProperty("name", "textures");
        textures.addProperty("value", getEncodedValue());

        JsonArray properties = new JsonArray();
        properties.add(textures);

        JsonObject object = new JsonObject();
        object.addProperty("id", uuid.toString().replaceAll("-", ""));
        object.addProperty("name", name);
        object.add("properties", properties);
        return object.toString();
    }

    public String getEncodedValue() {
        String json = SkinFetcher.GSON.toJson(this, SimpleSkin.class);
        return new String(Base64.getEncoder().encode(json.getBytes()));
    }
}
