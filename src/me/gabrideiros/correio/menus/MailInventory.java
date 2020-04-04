package me.gabrideiros.correio.menus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.gabrideiros.correio.Main;
import me.gabrideiros.correio.inventory.ClickableItem;
import me.gabrideiros.correio.inventory.SmartInventory;
import me.gabrideiros.correio.inventory.content.InventoryContents;
import me.gabrideiros.correio.inventory.content.InventoryProvider;
import me.gabrideiros.correio.inventory.content.Pagination;
import me.gabrideiros.correio.inventory.content.SlotIterator;
import me.gabrideiros.correio.inventory.content.SlotPos;
import me.gabrideiros.correio.inventory.itembuilder.ItemBuilder;
import me.gabrideiros.correio.objects.Account;
import me.gabrideiros.correio.objects.Mail;
import me.gabrideiros.correio.utils.Util;

public class MailInventory implements InventoryProvider {
	
	public static SmartInventory invreceived;

	public static final SmartInventory inv = SmartInventory.builder().provider(new MailInventory()).size(6, 9)
			.title("Correio").build();

	@Override
	public void init(Player player, InventoryContents contents) {

		Pagination pagination = contents.pagination();
		Account account = Main.getInstance().getAccountManager().get(player.getUniqueId());

		if (account == null) {
			contents.set(new SlotPos(2, 4),
					ClickableItem.empty(new ItemBuilder(Material.WEB, 1).setName("§cNenhuma encomenda")
							.setLore("§7Você não possuí nenhuma encomenda", "§7neste momento.").toItemStack()));
			return;
		}
		
		contents.set(new SlotPos(5, 4),
				ClickableItem.of(new ItemBuilder(Material.STORAGE_MINECART, 1).setName("§aRecolher encomendas")
						.setLore("§7Clique para recolher todas as", "§7suas encomendas.").toItemStack(), e -> {
							
							player.closeInventory();
							account.getMails().forEach(m -> { if (Util.getFreeSlots(player) >= 1) { player.getInventory().addItem(m.getItems()); } else {player.getWorld().dropItem(player.getLocation(), m.getItems());} });
							player.sendMessage("§aVocê recolheu todas as suas encomendas!");
							Main.getInstance().getAccountManager().deleteAll(account);
							return;
							
						}));
		
		
		List<Mail> mails = account.getMails();
		mails.addAll(account.getMails());

		ClickableItem[] items = new ClickableItem[mails.size()];

		for (int i = 0; i < items.length; i++) {
			
			Mail mail = mails.get(i);

			items[i] = ClickableItem.of(new ItemBuilder(1).setSkull(mail.getOwner()).setName("§aEncomenda #" + Integer.valueOf(i + 1))
					.setLore("", "§fEnviada por: §7" + mail.getOwner(), "§fData: §7" + new SimpleDateFormat("dd/MM/yyyy").format(new Date(mail.getData())), "", "§8Clique para abrir!").toItemStack(), e -> {
						
						invreceived = SmartInventory.builder().provider(new ReceiveInventory(mail)).size(4, 9)
								.title("Encomenda - " + mail.getOwner()).build();
						
						invreceived.open(player);
						
					});

			pagination.setItems(items);
			pagination.setItemsPerPage(21);

			SlotIterator slotIterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, new SlotPos(1, 1));

			slotIterator.blacklist(new SlotPos(1, 0));
			slotIterator.blacklist(new SlotPos(2, 0));
			slotIterator.blacklist(new SlotPos(3, 0));
			slotIterator.blacklist(new SlotPos(4, 0));
			slotIterator.blacklist(new SlotPos(5, 0));
			slotIterator.blacklist(new SlotPos(6, 0));
			slotIterator.blacklist(new SlotPos(1, 8));
			slotIterator.blacklist(new SlotPos(2, 8));
			slotIterator.blacklist(new SlotPos(3, 8));
			slotIterator.blacklist(new SlotPos(4, 8));
			slotIterator.blacklist(new SlotPos(5, 8));
			slotIterator.blacklist(new SlotPos(6, 8));
			pagination.addToIterator(slotIterator);

		}

		if (!pagination.isFirst())

		{
			contents.set(5, 3,
					ClickableItem.of(new ItemBuilder(Material.ARROW, 1).setName("§aPágina anterior").toItemStack(),
							e -> inv.open((Player) e.getWhoClicked(), pagination.previous().getPage())));
		}
		if (!pagination.isLast()) {
			contents.set(5, 5,
					ClickableItem.of(new ItemBuilder(Material.ARROW, 1).setName("§aPróxima página").toItemStack(),
							e -> inv.open(player, pagination.next().getPage())));
		}
	}

}
