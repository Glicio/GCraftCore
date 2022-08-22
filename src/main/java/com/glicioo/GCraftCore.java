package com.glicioo;


import com.glicioo.commands.DebugGetPlayerCMD;
import com.glicioo.commands.DebugGetPlayersCMD;
import com.glicioo.utils.CommnadsRegister;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class GCraftCore extends JavaPlugin {

    DataBaseHandler db;
    HashMap<UUID, DBPlayer> players = new HashMap<>();



    public GCraftCore()  {

    }

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        this.db = new DataBaseHandler(config);


        db.createUsersTable();
        db.createUserHomesTable();

        CommnadsRegister cmdRegister = new CommnadsRegister(this, players);
        cmdRegister.registerCommands(this.db);

        this.getServer().getPluginManager().registerEvents(new PlayerHandler(this.db,this.players), this);


    }

    public void onDisable() {
        try {
            if (db.con!=null && !db.con.isClosed()){
                db.closeConnection();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
