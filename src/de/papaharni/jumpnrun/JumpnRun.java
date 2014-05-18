/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pappi
 */
public class JumpnRun  extends JavaPlugin {
    
    private static JumpnRun _instance;
    private final static Map<String, List<PlayerArena>> _playerAreas = new HashMap<>();
    private final static List<Arena> _areas = new ArrayList<>();
    private final static Map<String, Map<String, Integer>> _checkpoints = new HashMap<>();
    private static WorldEdit _we;
    private static Economy _economy = null;
    
    @Override
    public void onEnable() {
        _instance = this;
        if(getWorldEdit() == null || !setupEconomy()) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        _we = getWorldEdit().getWorldEdit();
    }
    
    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if(economyProvider != null)
            _economy = (Economy)economyProvider.getProvider();
        return _economy != null;
    }
    
    private WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldEditPlugin) plugin;
    }
    
    public static WorldEdit getWE() {
        return _we;
    }
    
    public static Economy getEco() {
        return _economy;
    }
    
    public static JumpnRun getInstance() {
        return _instance;
    }
    
    public static List<PlayerArena> getPlayerAreas(String p) {  
        if(!_playerAreas.containsKey(p.toLowerCase())) {
            List<PlayerArena> list = new ArrayList<>();
            return list;
        }
        return _playerAreas.get(p.toLowerCase());
    }
    
    public static void setPlayerAreas(String p, List<PlayerArena> list) {
        _playerAreas.put(p.toLowerCase(), list);
    }
    
    public static List<Arena> getAreas() {
        return _areas;
    }
    
    public static int getCheckPoint(String a, String p) {
        if(!_checkpoints.containsKey(a))
            return -1;
        if(!_checkpoints.get(a).containsKey(p))
            return -1;
        return _checkpoints.get(a).get(p);
    }
    
    public static void setCheckPoint(String a, String p, int i) {
        if(!_checkpoints.containsKey(a)) {
            Map<String, Integer> chpmap = new HashMap<>();
            _checkpoints.put(a, chpmap);
        }
        _checkpoints.get(a).put(p, i);
    }
    
    public static void delCheckPoint(String a, String p) {
        if(_checkpoints.containsKey(a))
            _checkpoints.get(a).remove(p);
    }
}
