package me.gabrideiros.correio.objects;


import org.bukkit.inventory.ItemStack;

public class Mail {
	
	private String owner;
	private ItemStack items;
	private long data;
	
	public Mail(String owner, ItemStack items, long data) {
		this.owner = owner;
		this.items = items;
		this.data = data;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public ItemStack getItems() {
		return items;
	}

	public void setItems(ItemStack items) {
		this.items = items;
	}

	public long getData() {
		return data;
	}

	public void setData(long data) {
		this.data = data;
	}
	
}
