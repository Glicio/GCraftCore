package com.glicioo;

import com.glicioo.utils.PasswordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DataBaseHandler {
    String url;
    String dbUsername;
    String dbPassword;
    public Connection con;

    public DataBaseHandler(FileConfiguration config){
        this.url = config.getString("db_url");
        this.dbUsername = config.getString("db_username");
        this.dbPassword = config.getString("db_password");
        try {
            this.con = createConnectio();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private Connection createConnectio() throws ClassNotFoundException, SQLException {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url,dbUsername,dbPassword);
    }

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int executeStatementSQL(String sql){
        try {
            Statement stm = this.con.createStatement();
            return stm.executeUpdate(sql);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void createHome(UUID playerUUID, Location pos,String name){
        String sql = "INSERT INTO homes (player_uuid,world,posX,posY,posZ,name) VALUES (?,?,?,?,?,?)";


        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1,playerUUID);
            smt.setString(2,pos.getWorld().getName());
            smt.setDouble(3, pos.getX());
            smt.setDouble(4, pos.getY());
            smt.setDouble(5, pos.getZ());
            smt.setString(6,name);
            smt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Location getHome(UUID playerUUID, String name){
        String sql = "SELECT world,posX,posY,posZ FROM homes WHERE player_uuid=? AND name=?;";

        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1,playerUUID);
            smt.setString(2,name);
            ResultSet res = smt.executeQuery();
            if(res.next()){
                return new Location(Bukkit.getWorld(res.getString("world")),res.getDouble("posX"),res.getDouble("posY"),res.getDouble("posZ"));
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateHome(UUID playerUUID, Location pos, String name){
        String sql = "UPDATE homes SET world=?,posX=?,posY=?,posZ=? WHERE player_uuid=? AND name=?;";

        try{
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1,pos.getWorld().getName());
            smt.setDouble(2, pos.getX());
            smt.setDouble(3, pos.getY());
            smt.setDouble(4, pos.getZ());
            smt.setObject(5, playerUUID);
            smt.setString(6, name);

            smt.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteHome(UUID playerUUID, String name){
        String sql = "DELETE FROM homes WHERE player_uuid=? AND name=?;";

        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1,playerUUID);
            smt.setString(2, name);
            smt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public DBPlayer getPlayer(String name){
        return getPlayer(null,name);
    }

    public DBPlayer getPlayer(UUID uuid){
        return getPlayer(uuid,null);
    }

    private DBPlayer getPlayer(UUID uuid, String name){
        String sql;
        if(uuid == null){
            sql = "SELECT * FROM users WHERE name = ?;";
        }else{
            sql = "SELECT * FROM users WHERE uuid = ?;";
        }
        try {
            PreparedStatement smt = con.prepareStatement(sql);
            if(uuid == null){
                smt.setString(1,name);
            }else{
                smt.setObject(1,uuid);
            }
            ResultSet res = smt.executeQuery();
            if(res.next()){
                String pname = res.getString("name");
                UUID puuid = (UUID) res.getObject("uuid");
                return new DBPlayer(puuid, pname);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int addPlayer(Player player){
        String sql = "INSERT INTO users ( uuid, name ) VALUES ( ?, ?)";
        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1, player.getUniqueId());
            smt.setString(2, player.getName());
            return smt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public String[] getPlayerPassword(UUID playerUUID){
        String sql = "SELECT * FROM users WHERE (uuid = ?);";
        String[] resultado = new String[2];
        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1, playerUUID);
            ResultSet res = smt.executeQuery();
            if(res.next()){
                resultado[0] = res.getString("password");
                resultado[1] = res.getString("salt");
                return resultado;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setPlayerPassword(UUID playerUUID,String password){

        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password,salt);
        String sql = "UPDATE users SET password = ?, salt = ? WHERE (uuid=?)";

        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setString(1,securePassword);
            smt.setString(2,salt);
            smt.setObject(3, playerUUID);
            smt.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkPlayerExists(Player player){
        UUID playerUUID = player.getUniqueId();

        String sql = "SELECT * FROM users WHERE (uuid=?)";
        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.setObject(1, playerUUID);
            ResultSet res = smt.executeQuery();
            if(res.next()){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public void createUsersTable(){
        String sql = "CREATE TABLE IF NOT EXISTS Users ("+
                "id serial PRIMARY KEY,"+
                "uuid UUID UNIQUE NOT NULL,"+
                "name VARCHAR (50) UNIQUE NOT NULL,"+
                "password VARCHAR ( 255 )," +
                "salt varchar (255), "+
                "created TIMESTAMP"+
                ")";
        int res = executeStatementSQL(sql);

        if(res > 0){
            System.out.println("Tabela de Usuários criada com sucesso!");
        }
    }

    public void createUserHomesTable(){
        String sql = "CREATE TABLE IF NOT EXISTS homes ( home_id serial PRIMARY KEY," +
                "player_uuid UUID REFERENCES users (uuid)," +
                "world VARCHAR(50)," +
                "posX DOUBLE PRECISION," +
                "posY DOUBLE PRECISION," +
                "posZ DOUBLE PRECISION," +
                "name VARCHAR(30) UNIQUE NOT NULL" +
                ")";

        try {
            PreparedStatement smt = con.prepareStatement(sql);
            smt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Array> getPlayers(){
        String sql = "SELECT * FROM users";
        ArrayList<Array> result = new ArrayList<Array>();
        try {
            PreparedStatement smt = con.prepareStatement(sql);
            ResultSet results = smt.executeQuery();
            ResultSetMetaData resultSetMetaData = results.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();
            if(columnCount == 0){
                return result;
            }else {
                while (results.next()) {
                    int row = results.getRow();
                    result.add(results.getArray(row));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return result;
    }


}
