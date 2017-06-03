package me.brainmix.nickapi.spigot.utils;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.brainmix.nickapi.Skin;
import me.brainmix.nickapi.spigot.NickPlugin;
import me.brainmix.nickapi.spigot.simple.SimpleSkin;
import me.brainmix.nickapi.spigot.skin.SkinFetcher;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Base64;
import java.util.Collection;
import java.util.UUID;

public class Utils {

    private static NickPlugin plugin;

    public static void init(NickPlugin plugin) {
        Utils.plugin = plugin;
    }

    public static UUID stringToUUID(String stringUUID) {
        return UUID.fromString(stringUUID.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
    }

    public static Skin getOnlineSkin(Player player) {
        GameProfile profile = ((CraftPlayer) player).getProfile();
        Collection<Property> textures = profile.getProperties().get("textures");
        Property property = textures.stream().findFirst().orElse(null);
        if(property == null) return null;
        byte[] decodedBytes = Base64.getDecoder().decode(property.getValue().getBytes());
        return SkinFetcher.GSON.fromJson(new String(decodedBytes), SimpleSkin.class);
    }

    public static Property getSkinProperty(Skin skin) {
        String json = SkinFetcher.GSON.toJson(skin, SimpleSkin.class);
        String encoded =  new String(Base64.getEncoder().encode(json.getBytes()));
        return new Property("textures", encoded);
    }

    public static PlayerInfoData getPlayerInfoData(Player player, GameProfile profile) {
        return new PlayerInfoData(WrappedGameProfile.fromHandle(profile), ((CraftPlayer) player).getHandle().ping, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(player.getDisplayName()));
    }

    public static void updatePlayerFor(Player nicking, Player seeing) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> seeing.hidePlayer(nicking), 1L);
        Bukkit.getScheduler().runTaskLater(plugin, () -> seeing.showPlayer(nicking), 10L);
    }

}
