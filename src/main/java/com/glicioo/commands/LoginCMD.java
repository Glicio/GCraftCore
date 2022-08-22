package com.glicioo.commands;

import com.glicioo.DBPlayer;
import com.glicioo.DataBaseHandler;
import com.glicioo.utils.PasswordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class LoginCMD implements CommandExecutor {
    DataBaseHandler db;
    HashMap<UUID, DBPlayer> players;

    public LoginCMD(DataBaseHandler db,HashMap<UUID, DBPlayer> players){
        this.db = db;
        this.players = players;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player player){
            DBPlayer dbPlayer = players.get(player.getUniqueId());
            if(dbPlayer.getIsLogged()){
                player.sendMessage(ChatColor.GREEN+"Você já está logado!");
                return true;
            }else if( dbPlayer.hasPassword() && args.length > 0){
                if(PasswordUtils.verifyUserPassword(args[0],dbPlayer.getPassword(),dbPlayer.getSalt())){
                    dbPlayer.setIsLogged(true);
                    player.sendMessage(ChatColor.GREEN+"Logado com sucesso!");
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
