package com.glicioo.commands;

import com.glicioo.DBPlayer;
import com.glicioo.DataBaseHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class RegisterCMD implements CommandExecutor {
    DataBaseHandler db;
    HashMap<UUID, DBPlayer> players;
    public RegisterCMD(DataBaseHandler db, HashMap<UUID, DBPlayer> players){
        this.players = players;
        this.db = db;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player){
            String playerPassword = players.get(player.getUniqueId()).getPassword();
            if(!(playerPassword == null)){
                player.sendMessage(ChatColor.GREEN+"Você já está registrado, digite /logar [senha] para logar!");
                return true;
            }else{
                if (args.length >= 2 && args[0].equals(args[1])) {
                    db.setPlayerPassword(player.getUniqueId(),args[0]);
                    DBPlayer dbPlayer = players.get(player.getUniqueId());
                    dbPlayer.setIsLogged(true);
                    player.sendMessage(ChatColor.GREEN+"Registrado com sucesso!");

                }else{
                    player.sendMessage(ChatColor.RED+"Use /registrar [senha] [repetir senha] para registrar-se!");
                }

                return true;
            }

        }
        return false;
    }
}
