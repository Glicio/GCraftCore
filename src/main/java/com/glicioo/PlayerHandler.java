package com.glicioo;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayerHandler implements Listener {
    DataBaseHandler db;
    HashMap<UUID, DBPlayer> players;
    public PlayerHandler(DataBaseHandler db, HashMap<UUID, DBPlayer> players){
        this.db = db;
        this.players = players;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(!db.checkPlayerExists(e.getPlayer())){ db.addPlayer(e.getPlayer()); }
        DBPlayer player = new DBPlayer(e.getPlayer());
        player.setIsLogged(false);
        String[] playerPasswordInfo = db.getPlayerPassword(e.getPlayer().getUniqueId());
        player.setPasswordSecured(playerPasswordInfo[0]);
        player.setSalt(playerPasswordInfo[1]);
        //Adicionado jogador ao HashMap de jogadores
        this.players.put(e.getPlayer().getUniqueId(), player);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        //Removendo Jogador do hash de jogadores
        this.players.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e){
        DBPlayer player = players.get(e.getPlayer().getUniqueId());
        if(!player.getIsLogged()){
            e.setCancelled(true);
            if (player.hasPassword()) {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não está Logado use /logar [senha] para logar!");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não tem uma conta!\n use /registrar [senha] [repetir senha] para se registrar!");
            }

        }
    }

    @EventHandler
    public void onPlayerCmd(PlayerCommandPreprocessEvent e ){
        DBPlayer player = players.get(e.getPlayer().getUniqueId());
        String cmd = e.getMessage().split(" ")[0];
        //e.getPlayer().sendMessage("Comando: "+cmd+"\n"+cmd.equals("/logar"));
        if(!player.getIsLogged() && !(cmd.equals("/logar") || cmd.equals("/registrar"))){
            e.setCancelled(true);
            if (player.hasPassword()) {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não está Logado use /logar [senha] para logar!");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não tem uma conta!\n use /registrar [senha] [repetir senha] para se registrar!");
            }

        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        DBPlayer player = players.get(e.getPlayer().getUniqueId());

        if(!player.getIsLogged()){
            e.setCancelled(true);
            if (player.hasPassword()) {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não está Logado use /logar [senha] para logar!");
            } else {
                e.getPlayer().sendMessage(ChatColor.RED + "Você não tem uma conta!\n use /registrar [senha] [repetir senha] para se registrar!");
            }

        }
    }
}
