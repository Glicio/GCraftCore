package com.glicioo.commands;

import com.glicioo.DataBaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCMD implements CommandExecutor {
    DataBaseHandler db;

    public DelHomeCMD(DataBaseHandler db){

        this.db = db;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player player){

            if(args.length > 0){
                Location home = db.getHome(player.getUniqueId(), args[0]);
                if(home != null){
                    db.deleteHome(player.getUniqueId(), args[0]);
                    player.sendMessage(ChatColor.RED+"Home \""+args[0]+"\" deletada!");
                }else{
                    player.sendMessage(ChatColor.RED+"Você não tem uma Home com o nome: "+args[0]);
                }
            }else{
                player.sendMessage(ChatColor.RED+"Use /delhome [Nome da home]");
            }
            return true;
        }
        return false;
    }
}
