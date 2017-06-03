package me.brainmix.nickapi.spigot;

import me.brainmix.nickapi.*;
import me.brainmix.nickapi.spigot.simple.SimpleNick;
import me.brainmix.nickapi.spigot.skin.DefaultSkinProvider;
import me.brainmix.nickapi.spigot.skin.SkinFetcher;
import me.brainmix.nickapi.spigot.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class NickPlugin extends JavaPlugin {

    private static NickPlugin instance;
    private NickCache nickCache = new NickCache();
    private Map<Player, Nick> defaultNickCache = new HashMap<>();
    private Set<Skin> skinCache = new HashSet<>();
    private NickWatcher watcher;
    private SkinFetcher skinFetcher;
    private TablistType tablistType = TablistType.NORMAL;

    @Override
    public void onEnable() {
        instance = this;
        Utils.init(this);
        watcher = new NickWatcher(this);
        skinFetcher = new SkinFetcher(this, new DefaultSkinProvider());
        Bukkit.getPluginManager().registerEvents(new NickListener(this), this);
        Bukkit.getOnlinePlayers().forEach(this::saveDefaultNick);
        NickService service = new NickService(this);
        Bukkit.getServicesManager().register(NickAPIService.class, service, this, ServicePriority.Normal);
    }

    public static void log(Object... msg) {
        Arrays.asList(msg).stream().map(String::valueOf).forEach(instance.getLogger()::info);
    }

    public void saveDefaultNick(Player player) {
        Nick nick = new SimpleNick(player.getName());
        Skin skin = Utils.getOnlineSkin(player);
        nick.setSkin(skin);
        skinCache.add(skin);
        defaultNickCache.put(player, nick);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nick")) {
            if(!(sender instanceof Player)) return true;
            if(args.length < 1) return true;

            NickAPI.getService().setNick((Player) sender, args[0]);
            sender.sendMessage("Dein Nickname ist nun: " + args[0]);
            return true;
        }
        return false;
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

    public TablistType getTablistType() {
        return tablistType;
    }

    public void setTablistType(TablistType tablistType) {
        this.tablistType = tablistType;
    }

}
