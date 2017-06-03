package me.brainmix.nickapi.events;

import me.brainmix.nickapi.Nick;
import me.brainmix.nickapi.NickAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class PlayerNickForPlayerEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player nicking;
    private Player receiving;
    private Nick nick;
    private Nick oldNick;
    private Set<NickUpdateType> updateTypes;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public PlayerNickForPlayerEvent(Player nicking, Player receiving, Nick nick, Nick oldNick, Set<NickUpdateType> updateTypes) {
        this.nicking = nicking;
        this.receiving = receiving;
        this.nick = nick;
        this.oldNick = oldNick;
        this.updateTypes = updateTypes;

        NickAPI.getService().getDefaultNick(null);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getNicking() {
        return nicking;
    }

    public Player getReceiving() {
        return receiving;
    }

    public Nick getNick() {
        return nick;
    }

    public void setNick(Nick nick) {
        this.nick = nick;
    }

    public Nick getOldNick() {
        return oldNick;
    }

    public Set<NickUpdateType> getUpdateTypes() {
        return updateTypes;
    }

}
