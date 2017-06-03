package me.brainmix.nickapi.spigot.simple;

import me.brainmix.nickapi.Nick;
import me.brainmix.nickapi.Skin;
import org.apache.commons.lang.Validate;

public class SimpleNick implements Nick {

    private String nickname;
    private String prefix = "";
    private String suffix = "";
    private String tablistPrefix = "";
    private String tablistSuffix = "";
    private Skin skin;
    int priority;

    public SimpleNick(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        Validate.notNull(nickname, "nickname cant be null");
        this.nickname = nickname;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        Validate.notNull(prefix, "prefix cant be null");
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        Validate.notNull(suffix, "suffix cant be null");
        this.suffix = suffix;
    }

    @Override
    public String getTablistPrefix() {
        return tablistPrefix;
    }

    @Override
    public void setTablistPrefix(String tablistPrefix) {
        Validate.notNull(tablistPrefix, "tablistPrefix cant be null");
        this.tablistPrefix = tablistPrefix;
    }

    @Override
    public String getTablistSuffix() {
        return tablistSuffix;
    }

    @Override
    public void setTablistSuffix(String tablistSuffix) {
        Validate.notNull(tablistSuffix, "tablistSuffix cant be null");
        this.tablistSuffix = tablistSuffix;
    }

    @Override
    public Skin getSkin() {
        return skin;
    }

    @Override
    public void setSkin(Skin skin) {
        Validate.notNull(skin, "skin cant be null");
        this.skin = skin;
    }

    @Override
    public boolean hasCustomSkin() {
        return skin != null;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "SimpleNick{" +
                "nickname='" + nickname + '\'' +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", tablistPrefix='" + tablistPrefix + '\'' +
                ", tablistSuffix='" + tablistSuffix + '\'' +
                ", skin=" + skin +
                ", priority=" + priority +
                '}';
    }
}
