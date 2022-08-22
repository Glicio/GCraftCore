package com.glicioo.commands;

import com.glicioo.DBPlayer;
import com.glicioo.DataBaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.util.UUID;

public class SetHomeCmd implements CommandExecutor {

    DataBaseHandler db;

    public SetHomeCmd(DataBaseHandler db){

        this.db = db;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player player){
            if(args.length > 0 ){
                if(db.getHome(player.getUniqueId(),args[0]) != null){
                    player.sendMessage(ChatColor.GREEN+"Sua Home \""+ChatColor.AQUA+args[0]+ChatColor.GREEN+"\" foi atualizada!");
                    db.updateHome(player.getUniqueId(),player.getLocation(),args[0]);

                }else{
                    player.sendMessage(ChatColor.GREEN+"Você marcou sua Home \""+ChatColor.BLUE+args[0]+ChatColor.GREEN+"\"!");
                    db.createHome(player.getUniqueId(),player.getLocation(),args[0]);
                }
                return true;
            }else{
                sender.sendMessage(ChatColor.RED+"Use /sethome [NOME DA HOME]");
            }

            return true;
        }

        return false;
    }
}
