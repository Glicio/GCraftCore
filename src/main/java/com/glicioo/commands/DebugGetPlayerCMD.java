package com.glicioo.commands;

import com.glicioo.DBPlayer;
import com.glicioo.DataBaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DebugGetPlayerCMD implements CommandExecutor {
    DataBaseHandler db;
    public DebugGetPlayerCMD(DataBaseHandler db){
        this.db = db;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        DBPlayer player = db.getPlayer(args[0]);
        if(!(player == null)){
            sender.sendMessage("Jogador:\nNome: "+player.getName()+"\nUUID: "+player.getUuid().toString());
            return true;
        }else{
            sender.sendMessage(ChatColor.RED+"Jogador não Encotrado");
            return false;
        }

    }
}
