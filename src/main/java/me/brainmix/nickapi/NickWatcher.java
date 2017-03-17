package me.brainmix.nickapi;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.mojang.authlib.GameProfile;
import me.brainmix.nickapi.api.Nick;
import me.brainmix.nickapi.events.PlayerNickForPlayerEvent;
import me.brainmix.nickapi.packetwrappers.WrapperPlayServerEntityDestroy;
import me.brainmix.nickapi.packetwrappers.WrapperPlayServerNamedEntitySpawn;
import me.brainmix.nickapi.packetwrappers.WrapperPlayServerPlayerInfo;
import me.brainmix.nickapi.utils.SinglePair;
import me.brainmix.nickapi.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class NickWatcher extends PacketAdapter {

    private NickAPIPlugin plugin;
    private Map<SinglePair<Player>, Nick> queue = new HashMap<>();

    public NickWatcher(NickAPIPlugin plugin) {
        super(plugin, WrapperPlayServerNamedEntitySpawn.TYPE, WrapperPlayServerPlayerInfo.TYPE);
        this.plugin = plugin;
        ProtocolLibrary.getProtocolManager().addPacketListener(this);
    }

    public void update(Player nicking, Player seeing, Nick nick) {
        Nick oldNick = NickAPI.getNickFrom(nicking, seeing);
        PlayerNickForPlayerEvent event = new PlayerNickForPlayerEvent(nicking, seeing, nick, oldNick);
        Bukkit.getPluginManager().callEvent(event);
        queue.put(new SinglePair<>(nicking, seeing), event.getNick());
        //plugin.getNickCache().add(nicking, seeing, event.getNick());
    }

    private GameProfile getGameProfile(Player nicking, Nick nick) {
        String name = getCutted(nick.getNickname());
        UUID uuid = nicking.getUniqueId();
        GameProfile profile = new GameProfile(uuid, name);
        profile.getProperties().put("textures", Utils.getSkinProperty(nick.getSkin()));
        return profile;
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
            WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo(event.getPacket());
            List<PlayerInfoData> infoData = packet.getData();

            event.setPacket(packet.getHandle());
        }

    }

    @Override
    public void onPacketReceiving(PacketEvent event) {

    }

    private String getCutted(String input) {
        return input.length() > 16 ? input.substring(0, 16) : input;
    }

}
