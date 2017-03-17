package me.brainmix.nickapi.api;

import java.net.URL;

public interface Nick {

    String getNickname();

    void setNickname(String nickname);

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    String getTablistPrefix();

    void setTablistPrefix(String prefix);

    String getTablistSuffix();

    void setTablistSuffix(String suffix);


    Skin getSkin();

    void setSkin(Skin skin);

    boolean hasCustomSkin();

    int getPriority();

    void setPriority(int priority);

}
