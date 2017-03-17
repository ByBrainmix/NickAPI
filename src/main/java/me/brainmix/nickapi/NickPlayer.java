package me.brainmix.nickapi;

import me.brainmix.nickapi.api.Nick;
import me.brainmix.nickapi.api.Skin;
import org.bukkit.entity.Player;

public class NickPlayer {

    private final Player player;
    private Nick nick;
    private final Nick defaultNick;
    private final Skin defaultSkin;

    public NickPlayer(Player player, Nick nick, Nick defaultNick, Skin defaultSkin) {
        this.player = player;
        this.nick = nick;
        this.defaultNick = defaultNick;
        this.defaultSkin = defaultSkin;
    }

    public Player getPlayer() {
        return player;
    }

    public Nick getNick() {
        return nick;
    }

    public void setNick(Nick nick) {
        this.nick = nick;
    }

    public Nick getDefaultNick() {
        return defaultNick;
    }

    public Skin getDefaultSkin() {
        return defaultSkin;
    }

}
