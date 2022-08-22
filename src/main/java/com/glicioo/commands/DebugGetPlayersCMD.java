package com.glicioo.commands;

import com.glicioo.DataBaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugGetPlayersCMD implements CommandExecutor {
    DataBaseHandler db;
    public DebugGetPlayersCMD(DataBaseHandler db){
        this.db = db;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        System.out.println(db.getPlayers());

        return true;
    }
}
