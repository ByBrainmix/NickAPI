package me.brainmix.nickapi;

import me.brainmix.nickapi.api.Nick;
import me.brainmix.nickapi.api.Skin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class NickAPI {

    private static NickAPIPlugin plugin;

    protected static void init(NickAPIPlugin plugin) {
        NickAPI.plugin = plugin;
    }

    public static void setNickFor(Player nicking, Player seeing, Nick nick) {
        plugin.getWatcher().update(nicking, seeing, nick);
    }

    public static void setNickFor(Player nicking, Player seeing, String nickname) {
        Nick nick = getNickFrom(nicking, seeing);
        nick.setNickname(nickname);
        setNickFor(nicking, seeing, nick);
    }

    public static void setNick(Player nicking, Nick nick) {
        Bukkit.getOnlinePlayers().forEach(p -> setNickFor(nicking, p, nick));
    }

    public static void setNick(Player nicking, String nickname) {
        Bukkit.getOnlinePlayers().forEach(p -> setNickFor(nicking, p, nickname));
    }

    public static Nick getNickFrom(Player nicking, Player seeing) {
        return plugin.getNickCache().getNick(nicking, seeing, getDefaultNick(nicking));
    }

    public static Nick getNick(Player nicking) {
        return getNicks(nicking).stream().findFirst().orElse(null);
    }

    public static List<Nick> getNicks(Player nicking) {
        return plugin.getNickCache().getNicks(nicking, getDefaultNick(nicking));
    }

    public static Nick getDefaultNick(Player player) {
        return plugin.getDefaultNickCache().getOrDefault(player, null);
    }

    public static boolean isNickedFor(Player nicking, Player seeing) {
        return plugin.getNickCache().contains(nicking, seeing);
    }

    public static Skin getDefaultSkin(Player player) {
        return getDefaultNick(player).getSkin();
    }

    public static Skin getSkin(UUID uuid) {
        return plugin.getSkinFetcher().getSkin(uuid);
    }

    public static Skin createSkin(URL skinURL) {
        return null;
    }

}
