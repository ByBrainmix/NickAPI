package me.brainmix.nickapi.spigot;

import me.brainmix.nickapi.*;
import me.brainmix.nickapi.spigot.skin.SkinData;
import me.brainmix.nickapi.spigot.skin.SkinFetcher;
import me.brainmix.nickapi.spigot.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class NickService implements NickAPIService {

    private NickPlugin plugin;

    public NickService(NickPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void setNickFor(Player nicking, Player seeing, Nick nick) {
        NickPlugin.log("nicking: " + nicking.getName() + ", seeing: " + seeing.getName(), nick);
        plugin.getWatcher().update(nicking, seeing, nick, getNickFrom(nicking, seeing));
    }

    @Override
    public void setNickFor(Player nicking, Player seeing, String nickname) {
        Nick nick = getNickFrom(nicking, seeing);
        nick.setNickname(nickname);
        setNickFor(nicking, seeing, nick);
    }

    @Override
    public void setNick(Player nicking, Nick nick) {
        Bukkit.getOnlinePlayers().forEach(p -> setNickFor(nicking, p, nick));
    }

    @Override
    public void setNick(Player nicking, String nickname) {
        Bukkit.getOnlinePlayers().forEach(p -> setNickFor(nicking, p, nickname));
    }

    @Override
    public Nick getNickFrom(Player nicking, Player seeing) {
        return plugin.getNickCache().getNick(nicking, seeing, getDefaultNick(nicking));
    }

    @Override
    public Nick getNick(Player nicking) {
        return getNicks(nicking).stream().findFirst().orElse(null);
    }

    @Override
    public List<Nick> getNicks(Player nicking) {
        return plugin.getNickCache().getNicks(nicking, getDefaultNick(nicking));
    }

    @Override
    public Nick getDefaultNick(Player player) {
        return plugin.getDefaultNickCache().getOrDefault(player, null);
    }

    @Override
    public boolean isNickedFor(Player nicking, Player seeing) {
        return plugin.getNickCache().contains(nicking, seeing);
    }

    @Override
    public Skin getDefaultSkin(Player player) {
        return getDefaultNick(player).getSkin();
    }

    @Override
    public Skin getSkin(UUID uuid) {
        return plugin.getSkinFetcher().getSkin(uuid);
    }

    @Override
    public Skin toSkin(String value) {
        return SkinFetcher.GSON.fromJson(value, SkinData.class).toSkin();
    }

    @Override
    public Skin createSkin(URL skinURL, SkinType type) {
        return null;
    }

    @Override
    public void setSkinProvider(SkinProvider provider) {
        plugin.getSkinFetcher().setProvider(provider);
    }

    @Override
    public void setTablistType(TablistType tablistType) {
        plugin.setTablistType(tablistType);
    }
}
