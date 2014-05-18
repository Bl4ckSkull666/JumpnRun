/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Pappi
 */
public class JnR implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        
        if(p == null) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }
        
        if(args.length < 1) {
            p.sendMessage("");
            return true;
        }
        
        switch(args[0]) {
            case "create":
                
                break;
            case "delete":
                
                break;
            case "outportal":
                
                break;
            case "outteleport":
                
                break;
            case "winportal":
                
                break;
            case "winteleport":
                
                break;
            case "addcheckpoint":
                
                break;
            case "listcheckpoint":
                
                break;
            case "remcheckpoint":
                
                break;
            case "gotocheckpoint":
                
                break;
            case "setrewardchest":
                
                break;
            case "addreward":
                
                break;
            case "listreward":
                
                break;
            case "remreward":
                
                break;
            case "setmaxreward":
                
                break;
            case "timereward":
                
                break;
            default:
                
                break;
        }
        return true;
    }
    
}
