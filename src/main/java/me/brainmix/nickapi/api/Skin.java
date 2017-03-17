package me.brainmix.nickapi.api;

import java.net.URL;
import java.util.UUID;

public interface Skin {

    UUID getUUID();

    String getName();

    long getTimeStamp();

    boolean isPublic();

    SkinType getSkinType();

    URL getSkinURL();

    URL getCapeURL();

}
