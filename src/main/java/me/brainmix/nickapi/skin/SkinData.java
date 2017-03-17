package me.brainmix.nickapi.skin;

import me.brainmix.nickapi.api.Skin;
import me.brainmix.nickapi.impl.SimpleSkin;

import java.util.Base64;

public class SkinData {

    private String id;
    private String name;
    private SkinProperty[] properties;

    public SkinData(String id, String name, String decodedTextureValue) {
        this.id = id;
        this.name = name;
        this.properties = new SkinProperty[]{new SkinProperty("textures", decodedTextureValue)};
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SkinProperty[] getProperties() {
        return properties;
    }

    public SkinProperty getTexturesProperty() {
        for(SkinProperty property : properties) {
            if(property.getName().equals("textures")) return property;
        }
        return null;
    }

    public String getDecodedTextureValue() {
        SkinProperty property = getTexturesProperty();
        byte[] decodedBytes = Base64.getDecoder().decode(property.getValue().getBytes());
        return new String(decodedBytes);
    }

    public Skin toSkin() {
        return SkinFetcher.GSON.fromJson(getDecodedTextureValue(), SimpleSkin.class);
    }

}
