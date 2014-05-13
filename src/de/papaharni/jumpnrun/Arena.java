/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun;

import de.papaharni.jumpnrun.utils.Rnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Location;

/**
 *
 * @author Pappi
 */
public class Arena {
    
    private final String _arena;
    private final Location _pos1;
    private final Location _pos2;
    private final List<String> _rewards = new ArrayList<>();
    private int _maxRewards = 1;
    private Location _rewardchest = null;
    private final List<Location> _checkpoints = new ArrayList<>();
    
    //Portal bei Ausfall
    private Location _portalFallOutPos1 = null;
    private Location _portalFallOutPos2 = null;
    private Location _tpOnFall = null;
    
    //Portal für Gewinn
    private Location _portalWinPos1 = null;
    private Location _portalWinPos2 = null;
    private Location _tpOnWin = null;
    
    public Arena(String arena, Location pos1, Location pos2) {
        _arena = arena;
        _pos1 = pos1;
        _pos2 = pos2;
    }
    
    public String getArena() {
        return _arena;
    }
    
    
    
    public boolean isInArena(Location loc) {
        return isIn(loc, _pos1, _pos2);
    }
    
    public boolean isInOutPortal(Location loc) {
        if(_portalFallOutPos1 == null || _portalFallOutPos2 == null)
            return false;
        return isIn(loc, _portalFallOutPos1, _portalFallOutPos2);
    }
    
    public boolean isInWinPortal(Location loc) {
        if(_portalWinPos1 == null || _portalWinPos2 == null)
            return false;
        return isIn(loc, _portalWinPos1, _portalWinPos2);
    }
    
    private boolean isIn(Location loc1, Location loc2, Location loc3) {
        if(!(loc1.getBlockX() >= Math.min(loc2.getBlockX(),loc3.getBlockX()) && loc1.getBlockX() <= Math.max(loc2.getBlockX(),loc3.getBlockX())))
            return false;
        if(!(loc1.getBlockZ() >= Math.min(loc2.getBlockZ(),loc3.getBlockZ()) && loc1.getBlockZ() <= Math.max(loc2.getBlockZ(),loc3.getBlockZ())))
            return false;
        if(!(loc1.getBlockY() >= Math.min(loc2.getBlockY(),loc3.getBlockY()) && loc1.getBlockY() <= Math.max(loc2.getBlockY(),loc3.getBlockY())))
            return false;
        return true;
    }
    
    public int isCheckPoint(Location loc) {
        int i = 1;
        for(Location l: _checkpoints) {
            if(isInRange(loc.getBlockX(), l.getBlockX()) && isInRange(loc.getBlockZ(), l.getBlockZ()) && isInRange(loc.getBlockY(), l.getBlockY())) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    private boolean isInRange(int is, int check) {
        for(int i = (check-1); i <= (check+1); i++) {
            if(i == is)
                return true;
        }
        return false;
    }
    
    public void setTpOutPortal(Location loc1, Location loc2) {
        _portalFallOutPos1 = loc1;
        _portalFallOutPos2 = loc2;
    }
    
    public void setTpOutLocation(Location loc) {
        _tpOnFall = loc;
    }
    
    public Location getTpOutLocation() {
        return _tpOnFall;
    }
    
    public void setTpWinPortal(Location loc1, Location loc2) {
        _portalWinPos1 = loc1;
        _portalWinPos2 = loc2;
    }
    
    public void setTpWinLocation(Location loc) {
        _tpOnWin = loc;
    }
    
    public Location getTpWinLocation() {
        return _tpOnFall;
    }
    
    public void addCheckPoint(Location loc) {
        _checkpoints.add(loc);
    }
    
    public List<Location> getCheckPoints() {
        return _checkpoints;
    }
    
    public Location getCheckPoint(int i)  {
        if(i > _checkpoints.size())
            return null;
        return _checkpoints.get(i);
    }
    
    public void setRewardChest(Location loc) {
        _rewardchest = loc;
    }
    
    public Location getRewardChest() {
        return _rewardchest;
    }
    
    public void setRewardItem(String item) {
        _rewards.add(item);
    }
    
    public List<String> getRewards() {
        return _rewards;
    }
    
    public void remRewards(int i) {
        if(i == -1)
            _rewards.clear();
        if(i >= 0 && i < _rewards.size())
            _rewards.remove(i);
    }
    
    public List<String> getRandomRewards() {
        List<String> items = _rewards;
        int max = (int)Math.floor((double)(items.size()/2));
        if(max < 1)
            max = 1;
        if(max > _maxRewards)
            max = _maxRewards;
        
        max = Rnd.get(1,max);
        Collections.shuffle(items);
        return items.subList(0, max);
    }
}
