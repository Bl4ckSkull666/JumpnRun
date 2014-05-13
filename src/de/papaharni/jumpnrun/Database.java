/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Pappi
 */
public final class Database {
    public static void savePlayerData(String p) {
        if(!JumpnRun.getInstance().getDataFolder().exists())
            JumpnRun.getInstance().getDataFolder().mkdir();
        
        File jruserf = new File(JumpnRun.getInstance().getDataFolder(), "players");
        if(!jruserf.exists())
            jruserf.mkdir();
        
        File user = new File(jruserf, p + ".yml");
        FileConfiguration conf = YamlConfiguration.loadConfiguration(user);
        
        for(PlayerArena a: JumpnRun.getInstance().getPlayerAreas(p)) {
            conf.set("areas." + a.getArena() + ".wins", a.getWins());
            conf.set("areas." + a.getArena() + ".played", a.getPlayed());
        }
        try {
            conf.save(user);
        } catch(IOException ex) {
            JumpnRun.getInstance().getLogger().log(Level.WARNING, "Error on save User Area datas.", ex);
        }
    }
    
    public static void loadPlayerData(String p) {
        if(!JumpnRun.getInstance().getDataFolder().exists())
            JumpnRun.getInstance().getDataFolder().mkdir();
        
        File jruserf = new File(JumpnRun.getInstance().getDataFolder(), "players");
        if(!jruserf.exists())
            jruserf.mkdir();
        
        File user = new File(jruserf, p + ".yml");
        List<PlayerArena> list = new ArrayList<>();
        if(user.exists()) {
            FileConfiguration conf = YamlConfiguration.loadConfiguration(user);
            for(String key : conf.getConfigurationSection("areas").getKeys(false)) {
                list.add(new PlayerArena(key, conf.getInt(key + ".played", 0), conf.getInt(key + ".wins", 0)));
            }
        }
        JumpnRun.getInstance().setPlayerAreas(p, list);            
    }
    
    public static void saveAreasData() {
        
    }
    
    public static void loadAreasData() {
        
    }
}
