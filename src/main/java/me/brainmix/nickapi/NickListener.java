package me.brainmix.nickapi;

import me.brainmix.nickapi.api.Nick;
import me.brainmix.nickapi.impl.SimpleNick;
import me.brainmix.nickapi.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NickListener implements Listener {

    private NickAPIPlugin plugin;

    public NickListener(NickAPIPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Nick nick = new SimpleNick(player.getName());
        nick.setSkin(Utils.getOnlineSkin(player));

    }

}
