package me.brainmix.nickapi.skin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.brainmix.nickapi.NickAPIPlugin;
import me.brainmix.nickapi.api.Skin;
import me.brainmix.nickapi.impl.SimpleSkin;
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

    private static final String MCURL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(SimpleSkin.class, new SkinSerializer()).disableHtmlEscaping().create();

    private NickAPIPlugin plugin;

    public SkinFetcher(NickAPIPlugin plugin) {
        this.plugin = plugin;
    }

    public Skin getSkin(UUID uuid) {
        if(plugin.getSkinCache().stream().map(Skin::getUUID).collect(Collectors.toList()).contains(uuid))
            return plugin.getSkinCache().stream().filter(skin -> skin.getUUID().equals(uuid)).findFirst().orElse(null);

        String id = uuid.toString().replaceAll("-", "");
        String fullURL = String.format(MCURL, id);

        try(InputStream is = new URL(fullURL).openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            StringBuilder sb = new StringBuilder();
            int ic;
            while((ic = reader.read()) != -1) {
                sb.append((char) ic);
            }
            String raw = sb.toString();
            SkinData skindata = GSON.fromJson(raw, SkinData.class);
            Skin skin = skindata.toSkin();
            plugin.getSkinCache().add(skin);
            return skin;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getSkinAsync(UUID uuid, Consumer<Skin> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> callback.accept(getSkin(uuid)));
    }

}
