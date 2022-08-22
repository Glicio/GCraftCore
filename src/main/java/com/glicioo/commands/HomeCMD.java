package com.glicioo.commands;

import com.glicioo.DataBaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class HomeCMD implements CommandExecutor {

    DataBaseHandler db;

    public HomeCMD(DataBaseHandler db){
        this.db = db;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player player){

            if(args.length > 0){
                Location home = db.getHome(player.getUniqueId(),args[0]);
                if(home != null){
                    player.sendMessage(ChatColor.GREEN+"Teleportando para a Home \""+ChatColor.AQUA+args[0]+ChatColor.GREEN+"\"!");
                    player.teleport(home);
                }else{
                    player.sendMessage(ChatColor.RED+"Essa home não existe, use /sethome [NOME DA HOME] para definir uma home!");
                }
            }else{
                player.sendMessage(ChatColor.RED+"Use /home "+ChatColor.BLUE+"[NOME DA HOME]"+ChatColor.RED+" para ir para sua Home!");
            }
            return true;
        }
        return false;
    }
}
