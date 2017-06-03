package me.brainmix.nickapi;

import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public interface NickAPIService {

    void setNickFor(Player nicking, Player seeing, Nick nick);

    void setNickFor(Player nicking, Player seeing, String nickname);

    void setNick(Player nicking, Nick nick);

    void setNick(Player nicking, String nickname);

    Nick getNickFrom(Player nicking, Player seeing);

    Nick getNick(Player nicking);

    List<Nick> getNicks(Player nicking);

    Nick getDefaultNick(Player player);

    boolean isNickedFor(Player nicking, Player seeing);

    Skin getDefaultSkin(Player player);

    Skin getSkin(UUID uuid);

    Skin toSkin(String decoded);

    Skin createSkin(URL skinURL, SkinType type);

    void setSkinProvider(SkinProvider provider);

    void setTablistType(TablistType tablistType);

}
