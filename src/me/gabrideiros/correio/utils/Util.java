package me.gabrideiros.correio.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util {
	
	   public static int getFreeSlots(Player p) {
	    	int slots = 36;
	    	
	    	for (ItemStack item : p.getInventory().getContents()) {
	    		if (item != null && item.getType() != Material.AIR) {
	    			slots--;
	    		}					
	    	}				
	        return slots;	
	    } 

}
