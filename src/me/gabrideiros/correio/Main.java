package me.gabrideiros.correio;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.gabrideiros.correio.command.MailCommand;
import me.gabrideiros.correio.database.ConnectionManager;
import me.gabrideiros.correio.inventory.InventoryManager;
import me.gabrideiros.correio.listeners.AccountEvent;
import me.gabrideiros.correio.objects.AccountManager;



public class Main extends JavaPlugin {
	
	public ConnectionManager connectionManager;
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	
	public AccountManager accountManager;
	public AccountManager getAccountManager() {
		return accountManager;
	}
	
    public InventoryManager invManager;
    public InventoryManager invManager() {
    	return invManager; 
    }

	
	
	public static Main getInstance() {
		return getPlugin(Main.class);
	}
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		
		accountManager = new AccountManager();
		connectionManager = new ConnectionManager();

		invManager = new InventoryManager(this);
		invManager.init();
		
		events();
		commands();
		
		Bukkit.getOnlinePlayers().forEach(p -> accountManager.loadAccount(p));
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void events() {
		getServer().getPluginManager().registerEvents(new AccountEvent(), this);
	}
	
	public void commands() {
		getCommand("correio").setExecutor(new MailCommand());
	}

}
