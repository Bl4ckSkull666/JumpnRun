/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.papaharni.jumpnrun.utils;

import de.papaharni.jumpnrun.JumpnRun;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Pappi
 */
public final class Items {
    
    public static void giveReward(long time, List<String> itemList, String p) {
        Bukkit.getServer().getScheduler().runTaskLater(JumpnRun.getInstance(), new giveItemReward(itemList, p), (time*20));
    }
    
    public static boolean isItem(String str) {
        return ((getItem(str) != null)?true:false);
    }
    
    public static List<String> checkMoney(String p, List<String> itemList) {
        List<String> newList = new ArrayList<>();
        for(String str: itemList) {
            if(!str.startsWith("money ")) {
                newList.add(str);
                continue;
            }
            
            String[] strA = str.split(" ");
            if(strA.length < 2) {
                continue;
            }
            
            if(!Utils.isDouble(strA[1])) {
                continue;
            }
            
            JumpnRun.getEco().depositPlayer(p, Double.parseDouble(strA[1]));
            if(Bukkit.getPlayerExact(p) != null)
                Bukkit.getPlayerExact(p).sendMessage("Dir wurden " + strA[1] + " Coins gutgeschrieben.");
        }
        return itemList;
    }
    
    public static ItemStack getItem(String str) {
        String[] args = str.split(" ");
        if(args.length < 2) {
            JumpnRun.getInstance().getLogger().log(Level.INFO, str + " hat zu wenig Argumente Mindestens : Itemname Itemmenge - sind gefordert.");
            return null;
        }
        
        if(!Utils.isNumeric(args[1])) {
            JumpnRun.getInstance().getLogger().log(Level.INFO, args[1] + " muss eine Zahl sein");
            return null;
        }
        String[] itemname = args[0].split("\\:");
        if(Material.matchMaterial(itemname[0]) == null) {
            JumpnRun.getInstance().getLogger().log(Level.INFO, itemname[0] + " ist kein gultiges Item.");
            return null;
        }
        
        ItemStack i = new ItemStack(Material.matchMaterial(itemname[0]), Integer.parseInt(args[1]));
        if(i == null) {
            JumpnRun.getInstance().getLogger().log(Level.INFO, "Konnte kein Item aus " + str + " erstellen.");
            return null;
        }
        
        if(args.length >= 3) {
            for(int a = 2; a < args.length; a++) {
                ItemMeta im = i.getItemMeta();
                String[] sargs = args[a].split(":");
                if(sargs.length == 2) {
                    switch(sargs[0]) {
                        case "lore":
                            String[] msg = sargs[1].split("\\|");
                            if(msg.length > 4)
                                break;
                            List<String> lore = new ArrayList<>();
                            for(int b = 0; b < msg.length; b++) {
                                lore.add(ChatColor.translateAlternateColorCodes('&', msg[b].replaceAll("_", " ")));
                            }
                            im.setLore(lore);
                            break;
                        case "name":
                            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', sargs[1]));
                            break;
                        case "heal":
                            if(!Utils.isNumeric(sargs[1]))
                                continue;
                            int heal = Integer.parseInt(sargs[1]);
                            if(heal > 20)
                                heal = 20;
                            if(heal < 1)
                                heal = 1;
                            i.setDurability((short)heal);
                        default:
                            if(Enchantment.getByName(sargs[0].toLowerCase()) != null && Utils.isNumeric(sargs[1])) {
                                im.addEnchant(Enchantment.getByName(sargs[0].toLowerCase()), Integer.parseInt(sargs[1]), false);
                            } else if(checkEnchantment(sargs[0]) != null && Utils.isNumeric(sargs[1])) {
                                im.addEnchant(checkEnchantment(sargs[0]), Integer.parseInt(sargs[1]), false);
                            } else {
                                JumpnRun.getInstance().getLogger().log(Level.INFO, "Ignoriere " + sargs[0] + " da es nirgendwo rein passt.");
                            }
                            break;
                    }
                }
                i.setItemMeta(im);
            }
        }
        return i;
    }
    
    public static Enchantment checkEnchantment(String str) {
        switch(str.toLowerCase()) {
            case "power":
                return Enchantment.ARROW_DAMAGE;
            case "flame":
                return Enchantment.ARROW_FIRE;
            case "infinity":
                return Enchantment.ARROW_INFINITE;
            case "punch":
                return Enchantment.ARROW_KNOCKBACK;
            case "bane_of_arthropods":
                return Enchantment.DAMAGE_ARTHROPODS;
            case "baneofarthropods":
                return Enchantment.DAMAGE_ARTHROPODS;
            case "smite":
                return Enchantment.DAMAGE_UNDEAD;
            case "efficiency":
                return Enchantment.DIG_SPEED;
            case "unbreaking":
                return Enchantment.DURABILITY;
            case "fire_aspect":
                return Enchantment.FIRE_ASPECT;
            case "fireaspect":
                return Enchantment.FIRE_ASPECT;
            case "knockback":
                return Enchantment.KNOCKBACK;
            case "fortune":
                return Enchantment.LOOT_BONUS_BLOCKS;
            case "looting":
                return Enchantment.LOOT_BONUS_MOBS;
            case "respiration":
                return Enchantment.OXYGEN;
            case "protection":
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            case "blast_protection":
                return Enchantment.PROTECTION_EXPLOSIONS;
            case "blastprotection":
                return Enchantment.PROTECTION_EXPLOSIONS;
            case "feather_falling":
                return Enchantment.PROTECTION_FALL;
            case "featherfalling":
                return Enchantment.PROTECTION_FALL;
            case "fire_protection":
                return Enchantment.PROTECTION_FIRE;
            case "fireprotection":
                return Enchantment.PROTECTION_FIRE;
            case "projectile_protection":
                return Enchantment.PROTECTION_PROJECTILE;
            case "projectileprotection":
                return Enchantment.PROTECTION_PROJECTILE;
            case "silk_touch":
                return Enchantment.SILK_TOUCH;
            case "silktouch":
                return Enchantment.SILK_TOUCH;
            case "thorns":
                return Enchantment.THORNS;
            case "aqua_affinity":
                return Enchantment.WATER_WORKER;
            case "aquaaffinity":
                return Enchantment.WATER_WORKER;
            case "sharpness":
                return Enchantment.DAMAGE_ALL;
            default:
                return null;
        }
    }
}

class giveItemReward implements Runnable {
    private final List<String> _itemList;
    private final Player _p;
    private final String _pName; 
    public giveItemReward(List<String> itemList, String p) {
        _itemList = itemList;
        _p = Bukkit.getPlayerExact(p);
        _pName = p;
    }
    
    @Override
    public void run() {
        if(_p == null) {
            Items.giveReward(30, _itemList, _pName);
            return;
        }
        
        if(!_p.isOnline()) {
            Items.giveReward(30, _itemList, _pName);
            return;
        }
        
        if(_p.isDead()) {
            Items.giveReward(30, _itemList, _pName);
            return;
        }
        
        List<String> itemList = Items.checkMoney(_pName, _itemList);
        for(String str: itemList) {
            if(!Items.isItem(str))
                continue;
            
            _p.getInventory().addItem(Items.getItem(str));
        }
        _p.sendMessage("Du hast soeben deine Belohnung erhalten.");
    }
}
