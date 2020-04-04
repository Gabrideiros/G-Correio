package me.gabrideiros.correio.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.gabrideiros.correio.Main;
import me.gabrideiros.correio.objects.Account;

public class AccountEvent implements Listener {

	@EventHandler
	public void Join(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		Main.getInstance().getAccountManager().loadAsync(p);

	}

	@EventHandler
	public void Quit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		Account account = Main.getInstance().getAccountManager().get(p.getUniqueId());
		if (account != null)
			Main.getInstance().getAccountManager().remove(account);

	}	

}
