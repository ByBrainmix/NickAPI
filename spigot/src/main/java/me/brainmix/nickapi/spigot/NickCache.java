package me.brainmix.nickapi.spigot;

import me.brainmix.nickapi.Nick;
import me.brainmix.nickapi.spigot.utils.SinglePair;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NickCache {

    private Map<SinglePair<Player>, Nick> cache = new HashMap<>();

    public void add(Player nicking, Player seeing, Nick nick) {
        cache.put(new SinglePair<>(nicking, seeing), nick);
    }

    public List<Nick> remove(Player nicking) {
        List<SinglePair<Player>> pairs = cache.keySet().stream().filter(p -> p.getKey() == nicking).collect(Collectors.toList());
        List<Nick> output = new ArrayList<>();
        if(pairs.isEmpty()) return output;
        pairs.forEach(pair -> output.add(cache.remove(pair)));
        return removeDuplicates(output);
    }

    public Nick remove(Player nicking, Player seeing) {
        SinglePair<Player> pair = cache.keySet().stream().filter(p -> p.getKey() == nicking && p.getValue() == seeing).findFirst().orElse(null);
        if(pair == null) return null;
        return cache.remove(pair);
    }

    public boolean contains(Player nicking) {
        return cache.keySet().stream().filter(p -> p.getKey() == nicking).findFirst().isPresent();
    }


    public boolean contains(Player nicking, Player seeing) {
        return cache.keySet().stream().filter(p -> p.getKey() == nicking && p.getValue() == seeing).findFirst().isPresent();
    }

    public List<Nick> getNicks(Player nicking, Nick defaultNick) {
        List<SinglePair<Player>> pairs = getPairs(nicking);
        List<Nick> output = new ArrayList<>();
        if(pairs.isEmpty()) return output;
        pairs.forEach(pair -> output.add(cache.getOrDefault(pair, defaultNick)));
        return removeDuplicates(output);
    }

    public Nick getNick(Player nicking, Player seeing, Nick defaultNick) {
        SinglePair<Player> pair = cache.keySet().stream().filter(p -> p.getKey() == nicking && p.getValue() == seeing).findFirst().orElse(null);
        return pair == null ? defaultNick : cache.getOrDefault(pair, defaultNick);
    }

    private List<SinglePair<Player>> getPairs(Player nicking) {
        return cache.keySet().stream().filter(p -> p.getKey() == nicking).collect(Collectors.toList());
    }

    private <T> List<T> removeDuplicates(List<T> list) {
        List<T> output = new ArrayList<>();
        list.forEach(item -> {
            if(!output.contains(item)) output.add(item);
        });
        return output;
    }


}
