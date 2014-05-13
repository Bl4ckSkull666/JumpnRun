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
    private Map<String, List<PlayerArena>> _playerAreas = new HashMap<>();
    private List<Arena> _areas = new ArrayList<>();
    
    private WorldEdit _we;
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
    
    public static JumpnRun getInstance() {
        return _instance;
    }
    
    public List<PlayerArena> getPlayerAreas(String p) {  
        if(!_playerAreas.containsKey(p.toLowerCase())) {
            List<PlayerArena> list = new ArrayList<>();
            return list;
        }
        return _playerAreas.get(p.toLowerCase());
    }
    
    public void setPlayerAreas(String p, List<PlayerArena> list) {
        _playerAreas.put(p.toLowerCase(), list);
    }
}
