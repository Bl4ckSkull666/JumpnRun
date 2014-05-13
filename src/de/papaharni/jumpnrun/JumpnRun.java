/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Pappi
 */
public class JumpnRun  extends JavaPlugin {
    
    private static JumpnRun _instance;
    private Map<String, List<PlayerArena>> _playerAreas = new HashMap<>();
    private List<Arena> _areas = new ArrayList<>();
    
    @Override
    public void onEnable() {
        _instance = this;
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
