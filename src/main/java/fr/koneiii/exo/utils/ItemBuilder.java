package fr.koneiii.exo.utils;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    private ItemStack is;

    private static ItemStack bannerRetour = null;

    private static ItemStack bannerSuivant = null;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        this.is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, short meta) {
        this.is = new ItemStack(m, amount, meta);
    }


    public ItemBuilder setHead(String paramString1) {
        SkullMeta localSkullMeta = (SkullMeta) this.is.getItemMeta();
        GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), "AmperiaHead");
        PropertyMap localPropertyMap = localGameProfile.getProperties();
        localPropertyMap.put("textures", new Property("textures", paramString1));
        try {
            Field localField = localSkullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(localSkullMeta, localGameProfile);
        } catch (NoSuchFieldException | IllegalAccessException localNoSuchFieldException) {
            localNoSuchFieldException.printStackTrace();
        }
        this.is.setItemMeta(localSkullMeta);
        return this;
    }

    public ItemBuilder setPattern(List<Pattern> pattern) {
        if (is.getType().equals(Material.BANNER)) {
            BannerMeta banner = (BannerMeta) this.is.getItemMeta();
            banner.setPatterns(pattern);
            this.is.setItemMeta(banner);
        }
        return this;
    }

    public ItemBuilder setBannerBase(DyeColor dyeColor) {
        if (is.getType().equals(Material.BANNER)) {
            BannerMeta banner = (BannerMeta) this.is.getItemMeta();
            banner.setBaseColor(dyeColor);
            this.is.setItemMeta(banner);
        }
        return this;
    }


    public ItemBuilder setSkullURL(String url) {
        if (url.isEmpty())
            return this;
        SkullMeta headMeta = (SkullMeta) this.is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "domei_heads");
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[]{url}).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        this.is.setItemMeta((ItemMeta) headMeta);
        return this;
    }


    public static ItemStack freeLoreBannerRetour() {
        if (bannerRetour == null) {
            bannerRetour = new ItemStack(Material.BANNER, 1, (short) 1);
            ItemMeta meta = bannerRetour.getItemMeta();
            meta.setDisplayName("§c§l⇦ Retour");
            BannerMeta banner = (BannerMeta) meta;
            banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_LEFT));
            banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            bannerRetour.setItemMeta(meta);
        }
        return bannerRetour.clone();
    }

    public static ItemStack freeLoreBannerSuivant() {
        if (bannerSuivant == null) {
            bannerSuivant = new ItemStack(Material.BANNER, 1, (short) 1);
            ItemMeta meta = bannerSuivant.getItemMeta();
            meta.setDisplayName("§c§l➡ Suivant");
            BannerMeta banner = (BannerMeta) meta;
            banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_RIGHT));
            banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_MIDDLE));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.STRIPE_BOTTOM));
            banner.addPattern(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            bannerSuivant.setItemMeta(meta);
        }
        return bannerSuivant.clone();
    }

    public ItemBuilder clone() throws CloneNotSupportedException {
        return new ItemBuilder(this.is.clone());
    }

    public ItemBuilder setDurability(short dur) {
        this.is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDyeColor(DyeColor color) {
        Dye dye = new Dye();
        dye.setColor(color);
        this.is.setData(dye);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        this.is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        this.is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta(im);
        } catch (ClassCastException classCastException) {
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = this.is.getItemMeta();
        if (im == null) {
            im = Bukkit.getItemFactory().getItemMeta(this.is.getType());
            if (im == null) {
                throw new IllegalStateException("Failed to create ItemMeta for " + this.is.getType());
            }
        }
        im.addEnchant(ench, level, true);
        this.is.setItemMeta(im);
        return this;
    }


    public ItemBuilder setInfinityDurability(boolean unbreakable) {
        ItemMeta im = this.is.getItemMeta();
        im.spigot().setUnbreakable(unbreakable);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }


    public ItemBuilder addLore(List<String> lore) {
        ItemMeta meta = this.is.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null)
            lores = new ArrayList<>();
        lores.addAll(lore);
        meta.setLore(lores);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta meta = this.is.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null)
            lores = new ArrayList<>();
        if (lore != null) {
            lores.add(lore);
        } else {
            lores.add(" ");
        }
        meta.setLore(lores);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String... strings) {
        ItemMeta meta = this.is.getItemMeta();
        List<String> lores = meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }
        lores.addAll(Arrays.asList(strings));
        meta.setLore(lores);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore2, String... lore1) {
        ItemMeta im = this.is.getItemMeta();
        List<String> finalList = Arrays.asList(lore1);
        finalList.addAll(lore2);
        im.setLore(finalList);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = this.is.getItemMeta();
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta(im);
        } catch (ClassCastException classCastException) {
        }
        return this;
    }

    public ItemBuilder addPattern(DyeColor color, PatternType patternType) {
        if (is.getType().equals(Material.BANNER)) {
            BannerMeta banner = (BannerMeta) this.is.getItemMeta();
            banner.addPattern(new Pattern(color, patternType));
            this.is.setItemMeta(banner);
        }
        return this;
    }

    public ItemBuilder setNBTString(String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        compound.setString(key, value);
        nmsItem.setTag(compound);
        this.is = CraftItemStack.asBukkitCopy(nmsItem);
        return this;
    }

    public ItemBuilder setNBTInt(String key, int value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        compound.setInt(key, value);
        nmsItem.setTag(compound);
        this.is = CraftItemStack.asBukkitCopy(nmsItem);
        return this;
    }

    public ItemBuilder setNBTDouble(String key, double value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        compound.setDouble(key, value);
        nmsItem.setTag(compound);
        this.is = CraftItemStack.asBukkitCopy(nmsItem);
        return this;
    }

    public ItemBuilder setNBTBoolean(String key, boolean value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        compound.setBoolean(key, value);
        nmsItem.setTag(compound);
        this.is = CraftItemStack.asBukkitCopy(nmsItem);
        return this;
    }

    public ItemBuilder setNBTStringList(String key, List<String> values) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        NBTTagCompound compound = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();

        for (String value : values) {
            tagList.add(new NBTTagString(value));
        }

        compound.set(key, tagList);
        nmsItem.setTag(compound);
        this.is = CraftItemStack.asBukkitCopy(nmsItem);
        return this;
    }

    public String getNBTString(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        if (nmsItem.hasTag() && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getString(key);
        }
        return null;
    }

    public Integer getNBTInt(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        if (nmsItem.hasTag() && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getInt(key);
        }
        return null;
    }

    public Double getNBTDouble(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        if (nmsItem.hasTag() && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getDouble(key);
        }
        return null;
    }

    public Boolean getNBTBoolean(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        if (nmsItem.hasTag() && nmsItem.getTag().hasKey(key)) {
            return nmsItem.getTag().getBoolean(key);
        }
        return null;
    }

    public boolean hasNBTTag(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        return nmsItem.hasTag() && nmsItem.getTag().hasKey(key);
    }

    public ItemBuilder removeNBTTag(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this.is);
        if (nmsItem.hasTag()) {
            nmsItem.getTag().remove(key);
            this.is = CraftItemStack.asBukkitCopy(nmsItem);
        }
        return this;
    }

    public ItemBuilder addFlag(ItemFlag... flag) {
        ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(flag);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemStack toItemStack() {
        return this.is;
    }

    public ItemBuilder setAmount(int min) {
        this.is.setAmount(min);
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta(im);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return this;
    }

}