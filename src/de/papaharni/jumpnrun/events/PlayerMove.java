/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun.events;

import de.papaharni.jumpnrun.Arena;
import de.papaharni.jumpnrun.JumpnRun;
import de.papaharni.jumpnrun.utils.Areas;
import de.papaharni.jumpnrun.utils.Items;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Pappi
 */
public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Arena a = Areas.getInArena(e.getTo());
        if(a == null || p == null)
            return;

        if(a.isCheckPoint(e.getTo()) != -1) {
            JumpnRun.setCheckPoint(a.getArena(), p.getName(), a.isCheckPoint(e.getTo()));
        }
        
        if(a.isInOutPortal(e.getTo())) {
            if(JumpnRun.getCheckPoint(a.getArena(), p.getName()) > -1) {
                p.teleport(a.getCheckPoint(JumpnRun.getCheckPoint(a.getArena(), p.getName())));
                return;
            }
            
            if(a.getTpOutLocation() == null)
                return;
            p.teleport(a.getTpOutLocation());
            return;
        }
        
        if(a.isInWinPortal(e.getTo())) {
            if(a.getRewards().size() > 0) {
                List<String> itemList = a.getRandomRewards();
                if(a.getRewardChest() != null) {
                    //Speichere Reward in Virtueller Chest bis zur Öffnung der Chest.
                } else {
                    //Gib Reward direkt.
                    Items.giveReward(a.getRewardWaitTime(), itemList, a.getMoney(), p.getName());
                }
            }
            if(a.getTpWinLocation() == null)
                return;
            p.teleport(a.getTpWinLocation());
            return;
        }   
    }
}
