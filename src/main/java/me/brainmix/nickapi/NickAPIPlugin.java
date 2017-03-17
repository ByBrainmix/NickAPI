package me.brainmix.nickapi;

import me.brainmix.nickapi.api.Nick;
import me.brainmix.nickapi.api.Skin;
import me.brainmix.nickapi.skin.SkinFetcher;
import me.brainmix.nickapi.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class NickAPIPlugin extends JavaPlugin {

    private NickCache nickCache = new NickCache();
    private Map<Player, Nick> defaultNickCache = new HashMap<>();
    private Set<Skin> skinCache = new HashSet<>();
    private NickWatcher watcher;
    private SkinFetcher skinFetcher;

    @Override
    public void onEnable() {
        NickAPI.init(this);
        Utils.init(this);
        watcher = new NickWatcher(this);
        skinFetcher = new SkinFetcher(this);
        Bukkit.getPluginManager().registerEvents(new NickListener(this), this);
        
        /*
        Nick nick = NickAPI.getDefaultNick(player);
        nick.setNickname("Hurensohn");
        nick.setTablistPrefix("n00b | ");

        Skin skin = NickAPI.getSkin(UUID.randomUUID());
        nick.setSkin(skin);
        NickAPI.setNick(player, nick);
        */

    }

    @Override
    public void onDisable() {
    }

    public void log(String... msg) {
        Arrays.asList(msg).forEach(getLogger()::info);
    }

    public NickCache getNickCache() {
        return nickCache;
    }

    public Map<Player, Nick> getDefaultNickCache() {
        return defaultNickCache;
    }

    public Set<Skin> getSkinCache() {
        return skinCache;
    }

    public NickWatcher getWatcher() {
        return watcher;
    }

    public SkinFetcher getSkinFetcher() {
        return skinFetcher;
    }

}
