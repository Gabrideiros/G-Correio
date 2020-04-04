package me.gabrideiros.correio.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.gabrideiros.correio.Main;
import me.gabrideiros.correio.inventory.ClickableItem;
import me.gabrideiros.correio.inventory.content.InventoryContents;
import me.gabrideiros.correio.inventory.content.InventoryProvider;
import me.gabrideiros.correio.inventory.itembuilder.ItemBuilder;
import me.gabrideiros.correio.objects.Account;
import me.gabrideiros.correio.objects.Mail;
import me.gabrideiros.correio.utils.Util;

public class ReceiveInventory implements InventoryProvider {

	private Mail mail;

	public ReceiveInventory(Mail mail) {
		this.mail = mail;
	}
	@Override
	public void init(Player player, InventoryContents contents) {

		Account account = Main.getInstance().getAccountManager().get(player.getUniqueId());
	
		
    contents.set(1, 4, ClickableItem.empty(this.mail.getItems()));
		
	contents.set(3, 5, ClickableItem.of(new ItemBuilder(Material.STORAGE_MINECART).setName("§aRecolher encomenda").setLore("§7Clique para recolher a encomenda", "§7de " + this.mail.getOwner() + ".").toItemStack(), e -> {
			    
		    Player p2 = Bukkit.getPlayer(this.mail.getOwner());
		    
		    if (Util.getFreeSlots(player) >= 1){ player.getInventory().addItem(this.mail.getItems());} else {player.getWorld().dropItem(player.getLocation(), this.mail.getItems());}
			if (p2 != null) p2.sendMessage("§f" + player.getName() + "§a aceitou sua encomeda!");
				
		    player.sendMessage("§aEncomenda recolhida com sucesso!");
			player.closeInventory();
			account.getMails().remove(this.mail);
			
			if (account.getMails().size() < 1) Main.getInstance().getAccountManager().remove(account);
			Main.getInstance().getAccountManager().delete(player, this.mail.getData());
			
		}));

		contents.set(3, 4, ClickableItem.of(new ItemBuilder(Material.ARROW, 1).setName("§aVoltar").toItemStack(),
				e -> MailInventory.inv.open(player)));
		
	
	}


}
