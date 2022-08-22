package com.glicioo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugGetPos implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player jogador = ((Player) sender).getPlayer();
            jogador.sendMessage(jogador.getLocation().toString());
            return true;
        }
        return false;
    }
}
