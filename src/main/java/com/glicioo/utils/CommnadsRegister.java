package com.glicioo.utils;

import com.glicioo.DBPlayer;
import com.glicioo.DataBaseHandler;
import com.glicioo.GCraftCore;
import com.glicioo.commands.*;

import java.util.HashMap;
import java.util.UUID;

public class CommnadsRegister {
    GCraftCore plugin;
    HashMap<UUID, DBPlayer> players;
    public CommnadsRegister(GCraftCore plugin, HashMap<UUID, DBPlayer> players){
        this.plugin = plugin;
        this.players = players;
    }

    public void registerCommands(DataBaseHandler db){
        plugin.getCommand("getplayers").setExecutor(new DebugGetPlayersCMD(db));
        plugin.getCommand("getplayer").setExecutor(new DebugGetPlayerCMD(db));
        plugin.getCommand("registrar").setExecutor(new RegisterCMD(db,players));
        plugin.getCommand("logar").setExecutor(new LoginCMD(db,players));
        plugin.getCommand("sethome").setExecutor(new SetHomeCmd(db));
        plugin.getCommand("home").setExecutor(new HomeCMD(db));
        plugin.getCommand("delhome").setExecutor(new DelHomeCMD(db));
    }
}
