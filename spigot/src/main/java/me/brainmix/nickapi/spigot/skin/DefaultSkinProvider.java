package me.brainmix.nickapi.spigot.skin;

import me.brainmix.nickapi.SkinProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class DefaultSkinProvider implements SkinProvider {

    private static final String MCURL = "https://sessionserver.mojang.com/session/minecraft/profile/%s";

    @Override
    public String getValue(UUID uuid) {
        String id = uuid.toString().replaceAll("-", "");
        String fullURL = String.format(MCURL, id);

        try(InputStream is = new URL(fullURL).openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            StringBuilder sb = new StringBuilder();
            int ic;
            while((ic = reader.read()) != -1) {
                sb.append((char) ic);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
