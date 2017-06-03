package me.brainmix.nickapi.spigot.skin;


public class SkinProperty {

    private String name;
    private String value;

    public SkinProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}