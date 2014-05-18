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
    private long _rewardWaitTime = 0;
    private Location _rewardchest = null;
    private final List<Location> _checkpoints = new ArrayList<>();
    private double _minMoney = 0;
    private double _maxMoney = 0;
    
    //Portal bei Ausfall
    private Location _portalFallOutPos1 = null;
    private Location _portalFallOutPos2 = null;
    private Location _tpOnFall = null;
    
    //Portal für Gewinn
    private Location _portalWinPos1 = null;
    private Location _portalWinPos2 = null;
    private Location _tpOnWin = null;
    
    private boolean _vehicleCollision = true;
    
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
    
    /*public boolean leaveArena(Location from, Location to) {
        return enterArena(to, from);
    }
    
    public boolean enterArena(Location from, Location to) {
        if(from.getBlockX() < Math.min(_pos1.getBlockX(), _pos2.getBlockX()) && to.getBlockX() >= Math.min(_pos1.getBlockX(), _pos2.getBlockX())) {
            if(from.getBlockZ() < Math.min(_pos1.getBlockZ(), _pos2.getBlockZ()) && to.getBlockZ() >= Math.min(_pos1.getBlockZ(), _pos2.getBlockZ())) {
                if(from.getBlockY() < Math.min(_pos1.getBlockY(), _pos2.getBlockY()) && to.getBlockY() >= Math.min(_pos1.getBlockY(), _pos2.getBlockY())) {
                    
                }
            }
        }
        
        return true;
    }*/
    
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
    
    public void setMaxReward(int i) {
        if(i < 1)
            i = 1;
        _maxRewards = i;
    }
    
    public long getRewardWaitTime() {
        return _rewardWaitTime;
    }
    
    public void setRewardWaitTime(long i) {
        _rewardWaitTime = i;
    }
    
    public List<String> getRandomRewards() {
        List<String> items = _rewards;
        Collections.shuffle(items);
        return items.subList(0, Rnd.get(1, Math.min(_maxRewards, _rewards.size())));
    }
    
    public boolean getVehicleCollision() {
        return _vehicleCollision;
    }
    
    public void setVehicleCollision(boolean bol) {
        _vehicleCollision = bol;
    }
    
    public void setMoney(double min, double max) {
        _minMoney = min;
        _maxMoney = max;
    }
    
    public double getMoney() {
        if(_minMoney <= 0.0 && _maxMoney <= 0.0)
            return 0.0;
        return (double)Rnd.get((int)_minMoney, (int)_maxMoney);
    }
}
