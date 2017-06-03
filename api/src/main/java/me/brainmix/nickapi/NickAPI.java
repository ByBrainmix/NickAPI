package me.brainmix.nickapi;

import org.bukkit.Bukkit;

public final class NickAPI {

    private static NickAPIService service;

    static {
        if(Bukkit.getServicesManager().isProvidedFor(NickAPIService.class)) {
            service = Bukkit.getServicesManager().load(NickAPIService.class);
        } else {
            throw new IllegalAccessError("NickAPI is not loaded!");
        }
    }

    public static NickAPIService getService() {
        return service;
    }
}
