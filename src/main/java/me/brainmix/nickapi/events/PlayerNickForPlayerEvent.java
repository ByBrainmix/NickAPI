package me.brainmix.nickapi.events;

import me.brainmix.nickapi.api.Nick;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerNickForPlayerEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player nicking;
    private Player receiving;
    private Nick nick;
    private Nick oldNick;

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public PlayerNickForPlayerEvent(Player nicking, Player receiving, Nick nick, Nick oldNick) {
        this.nicking = nicking;
        this.receiving = receiving;
        this.nick = nick;
        this.oldNick = oldNick;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static void setHandlerList(HandlerList handlerList) {
        PlayerNickForPlayerEvent.handlerList = handlerList;
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

}
