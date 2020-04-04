package me.gabrideiros.correio.inventory.itembuilder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }
    

    public ItemBuilder(Material m, int quantia) {
        is = new ItemStack(m, quantia);
    }
    
    public ItemBuilder (int quantia) {
    	is = new ItemStack(Material.SKULL_ITEM, quantia, (short) 3);
    	
    }

    public ItemBuilder setName(String nome) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(nome);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(flag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        is.setItemMeta(im);
        return this;
    }
    
    public ItemBuilder setSkull(String head) {
    	SkullMeta skullMeta = (SkullMeta) is.getItemMeta();
    	skullMeta.setOwner(head);
    	is.setItemMeta(skullMeta);
    	return this;
    }

    public ItemStack toItemStack() {
        return is;
    }

    public static ItemStack getSkull(String url)
    {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
