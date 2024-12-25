package fr.koneiii.exo.utils;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtils {

    /**
     * Convertit un ItemStack Bukkit en NMS ItemStack
     */
    private static net.minecraft.server.v1_8_R3.ItemStack getNMSStack(ItemStack item) {
        if (item == null) return null;
        return CraftItemStack.asNMSCopy(item);
    }

    /**
     * Convertit un NMS ItemStack en Bukkit ItemStack
     */
    private static ItemStack getBukkitStack(net.minecraft.server.v1_8_R3.ItemStack item) {
        if (item == null) return null;
        return CraftItemStack.asBukkitCopy(item);
    }

    /**
     * Obtient le NBTTagCompound d'un item, le crée s'il n'existe pas
     */
    public static NBTTagCompound getTag(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = getNMSStack(item);
        NBTTagCompound tag = nmsItem.hasTag() ? nmsItem.getTag() : new NBTTagCompound();
        if (!nmsItem.hasTag()) {
            nmsItem.setTag(tag);
        }
        return tag;
    }

    /**
     * Définit le NBTTagCompound d'un item
     */
    public static ItemStack setTag(ItemStack item, NBTTagCompound tag) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = getNMSStack(item);
        nmsItem.setTag(tag);
        return getBukkitStack(nmsItem);
    }

    /**
     * Vérifie si l'item a un tag NBT spécifique
     */
    public static boolean hasKey(ItemStack item, String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = getNMSStack(item);
        return nmsItem.hasTag() && nmsItem.getTag().hasKey(key);
    }

    /**
     * Définit une string dans le NBT
     */
    public static ItemStack setString(ItemStack item, String key, String value) {
        NBTTagCompound tag = getTag(item);
        tag.setString(key, value);
        return setTag(item, tag);
    }

    /**
     * Récupère une string du NBT
     */
    public static String getString(ItemStack item, String key) {
        NBTTagCompound tag = getTag(item);
        return tag.getString(key);
    }

    /**
     * Définit un int dans le NBT
     */
    public static ItemStack setInt(ItemStack item, String key, int value) {
        NBTTagCompound tag = getTag(item);
        tag.setInt(key, value);
        return setTag(item, tag);
    }

    /**
     * Récupère un int du NBT
     */
    public static int getInt(ItemStack item, String key) {
        NBTTagCompound tag = getTag(item);
        return tag.getInt(key);
    }

    /**
     * Définit un double dans le NBT
     */
    public static ItemStack setDouble(ItemStack item, String key, double value) {
        NBTTagCompound tag = getTag(item);
        tag.setDouble(key, value);
        return setTag(item, tag);
    }

    /**
     * Récupère un double du NBT
     */
    public static double getDouble(ItemStack item, String key) {
        NBTTagCompound tag = getTag(item);
        return tag.getDouble(key);
    }

    /**
     * Définit un boolean dans le NBT
     */
    public static ItemStack setBoolean(ItemStack item, String key, boolean value) {
        NBTTagCompound tag = getTag(item);
        tag.setBoolean(key, value);
        return setTag(item, tag);
    }

    /**
     * Récupère un boolean du NBT
     */
    public static boolean getBoolean(ItemStack item, String key) {
        NBTTagCompound tag = getTag(item);
        return tag.getBoolean(key);
    }

    /**
     * Définit une liste NBT
     */
    public static ItemStack setList(ItemStack item, String key, NBTTagList list) {
        NBTTagCompound tag = getTag(item);
        tag.set(key, list);
        return setTag(item, tag);
    }

    /**
     * Récupère une liste NBT
     */
    public static NBTTagList getList(ItemStack item, String key, int type) {
        NBTTagCompound tag = getTag(item);
        return tag.getList(key, type);
    }

    /**
     * Supprime une clé du NBT
     */
    public static ItemStack removeKey(ItemStack item, String key) {
        NBTTagCompound tag = getTag(item);
        tag.remove(key);
        return setTag(item, tag);
    }

    /**
     * Clone le NBT d'un item
     */
    public static NBTTagCompound cloneTag(ItemStack item) {
        NBTTagCompound tag = getTag(item);
        return (NBTTagCompound) tag.clone();
    }
}