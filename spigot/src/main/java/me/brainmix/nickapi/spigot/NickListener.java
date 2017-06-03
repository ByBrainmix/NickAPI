package me.brainmix.nickapi.spigot;

import me.brainmix.nickapi.Nick;
import me.brainmix.nickapi.spigot.simple.SimpleNick;
import me.brainmix.nickapi.spigot.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NickListener implements Listener {

    private NickPlugin plugin;

    public NickListener(NickPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Nick nick = new SimpleNick(player.getName());
        plugin.getDefaultNickCache().put(player, nick);
        nick.setSkin(Utils.getOnlineSkin(player));

    }

}
