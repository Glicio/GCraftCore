package com.glicioo;


import org.bukkit.entity.Player;

import java.util.UUID;

public class DBPlayer{
    UUID uuid;
    String name;
    private Boolean isLogged;
    private String passwordSecured;
    private String salt;

    DBPlayer(Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }
    DBPlayer(UUID uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt(){
        return salt;
    }

    public void setPasswordSecured(String password){
        this.passwordSecured = password;
    }

    public String getPassword(){
        return this.passwordSecured;
    }

    public boolean hasPassword(){
        if(this.passwordSecured != null){
            return true;
        }
        return false;
    }
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setIsLogged(Boolean value){
        this.isLogged = value;
    }
    public boolean getIsLogged(){
        return this.isLogged;
    }
}
