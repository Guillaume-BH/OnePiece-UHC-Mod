package fr.lastril.onepiecemod.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Easily create {@link ItemStack}, without messing your hands. <i>Note that if you do
 * use this in one of your projects, leave this notice.</i> <i>Please do credit
 * me if you do use this in one of your projects.</i>
 *
 * @author Maygo
 * @version 1.8
 */
public class CustomItem {

    private final ItemStack itemStack;
    private final Material material;

    /**
     * Create a new CustomItem from scratch.
     *
     * @param m The material to create the CustomItem with.
     */
    public CustomItem(Material m) {
        this(m, 1);
    }

    /**
     * Create a new CustomItem over an existing itemstack.
     *
     * @param is The itemstack to create the CustomItem over.
     */
    public CustomItem(ItemStack is) {
        this.itemStack = is;
        this.material = is.getType();
    }

    /**
     * Create a new CustomItem from scratch.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     */
    public CustomItem(Material m, int amount) {
        itemStack = new ItemStack(m, amount);
        this.material = m;
    }

    /**
     * Create a new CustomItem from scratch.
     *
     * @param m      The material of the item.
     * @param amount The amount of the item.
     * @param data   The data of the item.
     */
    public CustomItem(Material m, int amount, int data) {
        itemStack = new ItemStack(m, amount, (byte) data);
        this.material = m;
    }

    /**
     * Clone the CustomItem into a new one.
     *
     * @return The cloned instance.
     */
    @Override
    public CustomItem clone() {
        return new CustomItem(itemStack);
    }

    /**
     * Change the durability of the item.
     *
     * @param dur The durability to set it to.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setDurability(short dur) {
        itemStack.setDurability(dur);
        return this;
    }

    /**
     * Set the displayname of the item.
     *
     * @param name The name to change it to.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setName(String name) {
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(name);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Add an unsafe enchantment.
     *
     * @param ench  The enchantment to add.
     * @param level The level to put the enchant on.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addUnsafeEnchantment(Enchantment ench, int level) {
        itemStack.addUnsafeEnchantment(ench, level);
        return this;
    }

    /**
     * Remove a certain enchant from the item.
     *
     * @param ench The enchantment to remove
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem removeEnchantment(Enchantment ench) {
        itemStack.removeEnchantment(ench);
        return this;
    }

    /**
     * Set the skull owner for the item. Works on skulls only.
     *
     * @param owner The name of the skull's owner.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) itemStack.getItemMeta();
            im.setOwner(owner);
            itemStack.setItemMeta(im);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Set the head texture. Works on skulls only.
     *
     * @param texture The hash of the texture.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setTexture(String texture) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        propertyMap.put("textures", new Property("textures", texture));
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        Class<?> c_skullMeta = skullMeta.getClass();
        try {
            Field f_profile = c_skullMeta.getDeclaredField("profile");
            f_profile.setAccessible(true);
            f_profile.set(skullMeta, profile);
            f_profile.setAccessible(false);
            this.itemStack.setItemMeta(skullMeta);
            return this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Add an enchant to the item.
     *
     * @param ench  The enchant to add
     * @param level The level
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addEnchant(Enchantment ench, int level) {
        ItemMeta im = itemStack.getItemMeta();
        im.addEnchant(ench, level, true);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Add an item flag to the item.
     *
     * @param flags The itemflags to add
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addItemFlags(ItemFlag... flags) {
        ItemMeta im = itemStack.getItemMeta();
        im.addItemFlags(flags);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Add multiple enchants at once.
     *
     * @param enchantments The enchants to add.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addEnchantments(Map<Enchantment, Integer> enchantments) {
        itemStack.addEnchantments(enchantments);
        return this;
    }

    /**
     * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
     *
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setInfinityDurability() {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    /**
     * Sets the item unbreakable.
     *
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setUnbreakable(boolean state) {
        itemStack.getItemMeta().spigot().setUnbreakable(state);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setLore(String... lore) {
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Re-sets the lore.
     *
     * @param lore The lore to set it to.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setLore(List<String> lore) {
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param line The line to remove.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem removeLoreLine(String line) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line)) return this;
        lore.remove(line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Remove a lore line.
     *
     * @param index The index of the lore line to remove.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem removeLoreLine(int index) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size()) return this;
        lore.remove(index);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addLoreLine(String line) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore()) lore = new ArrayList<>(im.getLore());
        lore.add(line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Add a lore line.
     *
     * @param line The lore line to add.
     * @param pos  The index of where to put it.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem addLoreLine(String line, int pos) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    /**
     * Sets the dye color on an item.
     * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
     *
     * @param color The color to put.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setDyeColor(DyeColor color) {
        this.itemStack.setDurability(color.getData());
        return this;
    }

    /**
     * Sets the dye color of a wool item. Works only on wool.
     *
     * @param color The DyeColor to set the wool item to.
     * @return Le {@link CustomItem} actuel
     * @see CustomItem#setDyeColor(DyeColor)
     * @deprecated As of version 1.2 changed to setDyeColor.
     */
    @Deprecated
    public CustomItem setWoolColor(DyeColor color) {
        if (!itemStack.getType().equals(Material.WOOL)) return this;
        this.itemStack.setDurability(color.getData());
        return this;
    }

    /**
     * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
     *
     * @param color The color to set it to.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) itemStack.getItemMeta();
            im.setColor(color);
            itemStack.setItemMeta(im);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the item (potion) into a splash potion.
     *
     * @param splash If the potion should be a splash potion.
     * @return Le {@link CustomItem} actuel
     */
    public CustomItem setSplash(boolean splash) {
        if (this.itemStack.getType() == Material.POTION) {
            Potion potion = Potion.fromItemStack(this.itemStack);
            potion.setSplash(splash);
        }
        return this;
    }

    /**
     * Retrieves the itemstack from the CustomItem.
     *
     * @return The itemstack created/modified by the CustomItem instance.
     */
    public ItemStack toItemStack() {
        return itemStack;
    }

    /**
     * Returns the material of the item.
     *
     * @return The material of the item.
     */
    public Material getMaterial() {
        return material;
    }
}
