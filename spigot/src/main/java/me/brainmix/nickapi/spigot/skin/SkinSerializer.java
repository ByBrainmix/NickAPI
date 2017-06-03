package me.brainmix.nickapi.spigot.skin;

import com.google.gson.*;
import me.brainmix.nickapi.SkinType;
import me.brainmix.nickapi.spigot.simple.SimpleSkin;
import me.brainmix.nickapi.spigot.utils.Utils;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

public class SkinSerializer implements JsonSerializer<SimpleSkin>, JsonDeserializer<SimpleSkin> {

    @Override
    public JsonElement serialize(SimpleSkin skin, Type type, JsonSerializationContext context) {
        long timestamp = skin.getTimeStamp();
        String profileId = skin.getUUID().toString().replaceAll("-", "");
        String profileName = skin.getName();
        boolean isPublic = skin.isPublic();
        String skinURL = skin.getSkinURL() != null ? skin.getSkinURL().toString() : null;
        String capeURL = skin.getCapeURL() != null ? skin.getCapeURL().toString() : null;

        JsonObject obj = new JsonObject();
        obj.addProperty("timestamp", timestamp);
        obj.addProperty("profileId", profileId);
        obj.addProperty("profileName", profileName);
        obj.addProperty("isPublic", isPublic);

        JsonObject textures = new JsonObject();
        if(skinURL != null) {
            JsonObject url = new JsonObject();
            url.addProperty("url", skinURL);
            textures.add("SKIN", context.serialize(url));
        }
        if(capeURL != null) {
            JsonObject url = new JsonObject();
            url.addProperty("url", capeURL);
            textures.add("CAPE", context.serialize(url));
        }

        obj.add("textures", context.serialize(textures));
        return obj;
    }

    @Override
    public SimpleSkin deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = element.getAsJsonObject();

        long timestamp = obj.has("timestamp") ? obj.get("timestamp").getAsLong() : -1;
        String profileId = obj.has("profileId") ? obj.get("profileId").getAsString() : null;
        String profileName = obj.has("profileName") ? obj.get("profileName").getAsString() : null;
        boolean isPublic = obj.has("isPublic") ? obj.get("isPublic").getAsBoolean() : false;
        String skinURLString = null;
        String capeURLString = null;

        if(obj.has("textures")) {
            JsonObject textures = obj.get("textures").getAsJsonObject();

            if(textures.has("SKIN") && textures.get("SKIN").getAsJsonObject().has("url")) {
                skinURLString = textures.get("SKIN").getAsJsonObject().get("url").getAsString();
            }

            if(textures.has("CAPE") && textures.get("CAPE").getAsJsonObject().has("url")) {
                capeURLString = textures.get("CAPE").getAsJsonObject().get("url").getAsString();
            }

        }

        URL skinURL = null;
        URL capeURL = null;

        try {
            if(skinURLString != null) skinURL = new URL(skinURLString);
            if(capeURLString != null) capeURL = new URL(capeURLString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return new SimpleSkin(Utils.stringToUUID(profileId), profileName, timestamp, isPublic, SkinType.STEVE, skinURL, capeURL);
    }

}
