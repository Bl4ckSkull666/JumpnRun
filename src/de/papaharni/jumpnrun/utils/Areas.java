/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun.utils;

import de.papaharni.jumpnrun.Arena;
import de.papaharni.jumpnrun.JumpnRun;
import org.bukkit.Location;

/**
 *
 * @author Pappi
 */
public final class Areas {
    public static boolean isInArena(Location loc) {
        return getInArena(loc) != null;
    }
    
    public static Arena getInArena(Location loc) {
        for(Arena a: JumpnRun.getAreas()) {
            if(a.isInArena(loc)) {
                return a;
            }
        }
        return null;
    }
    
    
}
