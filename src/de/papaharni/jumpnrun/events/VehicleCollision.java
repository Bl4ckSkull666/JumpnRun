/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun.events;

import de.papaharni.jumpnrun.Arena;
import de.papaharni.jumpnrun.utils.Areas;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCollisionEvent;

/**
 *
 * @author Pappi
 */
public class VehicleCollision implements Listener {
    
    @EventHandler
    public void onVehicleCollision(VehicleCollisionEvent e) {
        Arena a = Areas.getInArena(e.getVehicle().getLocation());
        if(a == null)
            return;
            
        if(a.getVehicleCollision())
            return;
        
        e.getVehicle().getLastDamageCause().setDamage(0.0);
    }    
}
