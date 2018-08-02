package me.cdnmflip.menumaker.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private final ItemStack ITEM_STACK;

    public ItemStackBuilder(Material mat) {
        this.ITEM_STACK = new ItemStack(mat);
    }

    public ItemStackBuilder(ItemStack item) {
        this.ITEM_STACK = item;
    }

    public ItemStackBuilder withAmount(int amount) {
        this.ITEM_STACK.setAmount(amount);
        return this;
    }

    public ItemStackBuilder withName(String name) {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.setDisplayName(this.color(name));

        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(String name) {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) lore = new ArrayList<>();

        lore.add(this.color(name));
        meta.setLore(lore);

        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withDurability(int durability) {
        this.ITEM_STACK.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder withData(int data) {
        this.ITEM_STACK.setDurability((short) data);
        return this;
    }

    public ItemStackBuilder withFlag(ItemFlag flag) {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.addItemFlags(flag);

        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment, int level) {
        this.ITEM_STACK.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment) {
        this.ITEM_STACK.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder withType(Material material) {
        this.ITEM_STACK.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.setLore(new ArrayList<>());

        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        for (Enchantment enchantment : this.ITEM_STACK.getEnchantments().keySet())
            this.ITEM_STACK.removeEnchantment(enchantment);

        return this;
    }

    public ItemStackBuilder withColor(Color color) {
        Material type = this.ITEM_STACK.getType();

        if (type != Material.LEATHER_BOOTS && type != Material.LEATHER_CHESTPLATE && type != Material.LEATHER_HELMET && type != Material.LEATHER_LEGGINGS) {
            throw new IllegalArgumentException("withColor is only applicable for leather armor!");
        } else {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.ITEM_STACK.getItemMeta();
            meta.setColor(color);

            this.ITEM_STACK.setItemMeta(meta);
            return this;
        }
    }

    public ItemStackBuilder withGlow() {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        this.ITEM_STACK.setItemMeta(meta);
        this.ITEM_STACK.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

        return this;
    }

    public ItemStack build() {
        return this.ITEM_STACK;
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
