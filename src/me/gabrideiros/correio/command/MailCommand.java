package me.gabrideiros.correio.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.gabrideiros.correio.Main;
import me.gabrideiros.correio.menus.MailInventory;

public class MailCommand implements CommandExecutor {
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if (args.length < 1) {
		   MailInventory.inv.open(p);
		   return true;
		}
		
		if (args[0].equalsIgnoreCase("enviar")) {
			if (args.length < 2) {
				p.sendMessage("§cUtilize /correio enviar <jogador>.");
				return true;
			}
			
			Player p2 = Bukkit.getPlayer(args[1]);
			
			if (p2 == null) {
				p.sendMessage("§cEste jogador não foi encontrado!");
				return true;
			}
			
			if (p2.getName().equals(p.getName())) {
				p.sendMessage("§cVocê não pode enviar uma encomenda para sí mesmo!");
				return true;
			}
			
			ItemStack item = p.getItemInHand().clone();
			
			if (item == null || item.getType() == Material.AIR) {
				p.sendMessage("§cSegure um item na mão para enviar!");
				return true;
			}
			
	        long data = System.currentTimeMillis();
			
			
			Main.getInstance().getAccountManager().insertAsync(p2, data, p, item);
			return true;
		}
		
		return true;
	}

}
