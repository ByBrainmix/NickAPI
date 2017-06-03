package me.brainmix.nickapi.spigot.skin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.brainmix.nickapi.Skin;
import me.brainmix.nickapi.SkinProvider;
import me.brainmix.nickapi.spigot.NickPlugin;
import me.brainmix.nickapi.spigot.simple.SimpleSkin;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SkinFetcher {

    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(SimpleSkin.class, new SkinSerializer()).disableHtmlEscaping().create();

    private NickPlugin plugin;
    private SkinProvider provider;

    public SkinFetcher(NickPlugin plugin, SkinProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public Skin getSkin(UUID uuid) {
        if(plugin.getSkinCache().stream().map(Skin::getUUID).collect(Collectors.toList()).contains(uuid))
            return plugin.getSkinCache().stream().filter(skin -> skin.getUUID().equals(uuid)).findFirst().orElse(null);

        String raw = provider.getValue(uuid);
        if(raw == null) throw new IllegalStateException("Skin-value is null");
        SkinData skindata = GSON.fromJson(raw, SkinData.class);
        Skin skin = skindata.toSkin();
        plugin.getSkinCache().add(skin);
        return skin;

    }

    public void getSkinAsync(UUID uuid, Consumer<Skin> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> callback.accept(getSkin(uuid)));
    }

    public void setProvider(SkinProvider provider) {
        this.provider = provider;
    }

}
