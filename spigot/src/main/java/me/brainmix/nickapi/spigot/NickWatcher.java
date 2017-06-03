package me.brainmix.nickapi.spigot;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.mojang.authlib.GameProfile;
import me.brainmix.nickapi.Nick;
import me.brainmix.nickapi.events.PlayerNickForPlayerEvent;
import me.brainmix.nickapi.spigot.packetwrappers.WrapperPlayServerEntityDestroy;
import me.brainmix.nickapi.spigot.packetwrappers.WrapperPlayServerNamedEntitySpawn;
import me.brainmix.nickapi.spigot.packetwrappers.WrapperPlayServerPlayerInfo;
import me.brainmix.nickapi.spigot.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class NickWatcher extends PacketAdapter {

    private NickPlugin plugin;
    private NickCache ignorePackets = new NickCache();

    public NickWatcher(NickPlugin plugin) {
        super(plugin, WrapperPlayServerNamedEntitySpawn.TYPE, WrapperPlayServerPlayerInfo.TYPE);
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    public void update(Player nicking, Player seeing, Nick nick, Nick oldNick) {
        PlayerNickForPlayerEvent event = new PlayerNickForPlayerEvent(nicking, seeing, nick, oldNick, new HashSet<>(Arrays.asList()));
        Bukkit.getPluginManager().callEvent(event);
        ignorePackets.add(nicking, seeing, event.getNick());

        NickPlugin.log("removing player...");
        WrapperPlayServerPlayerInfo removePacket = new WrapperPlayServerPlayerInfo();
        removePacket.setAction(PlayerInfoAction.REMOVE_PLAYER);
        removePacket.setData(new ArrayList<>(Arrays.asList(Utils.getPlayerInfoData(nicking, getDefaultGameProfile(nicking)))));
        removePacket.sendPacket(seeing);

        NickPlugin.log("adding player...");
        WrapperPlayServerPlayerInfo addPacket = new WrapperPlayServerPlayerInfo();
        addPacket.setAction(PlayerInfoAction.ADD_PLAYER);
        addPacket.setData(new ArrayList<>(Arrays.asList(Utils.getPlayerInfoData(nicking, getGameProfile(nicking, nick)))));
        addPacket.sendPacket(seeing);

        Utils.updatePlayerFor(nicking, seeing);
        //plugin.getNickCache().add(nicking, seeing, event.getNick());
    }

    private GameProfile getGameProfile(Player nicking, Nick nick) {
        String name = getCutted(nick.getNickname());
        UUID uuid = nicking.getUniqueId();
        GameProfile profile = new GameProfile(uuid, name);
        profile.getProperties().put("textures", Utils.getSkinProperty(nick.getSkin()));
        return profile;
    }

    private GameProfile getDefaultGameProfile(Player player) {
        return ((CraftPlayer) player).getProfile();
    }

    private void nickPlayer(Player nicking, Player seeing, Nick nick) {
        WrapperPlayServerEntityDestroy destroyPacket = new WrapperPlayServerEntityDestroy();
        destroyPacket.setEntityIds(new int[] {nicking.getEntityId()});
        WrapperPlayServerPlayerInfo infoPacket = new WrapperPlayServerPlayerInfo();
        infoPacket.setAction(PlayerInfoAction.REMOVE_PLAYER);
        infoPacket.setData(Arrays.asList(Utils.getPlayerInfoData(nicking, getGameProfile(nicking, nick))));

        infoPacket.sendPacket(seeing);
        if(nicking.getWorld() == seeing.getWorld()) {
            destroyPacket.sendPacket(seeing);
            Utils.updatePlayerFor(nicking, seeing);
        }


    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacketType() == WrapperPlayServerPlayerInfo.TYPE) {
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
    }

    private String getCutted(String input) {
        return input.length() > 16 ? input.substring(0, 16) : input;
    }

}
