package fr.koneiii.exo;

import fr.koneiii.exo.commands.ItemCommand;
import fr.koneiii.exo.items.ItemsManager;
import fr.koneiii.exo.listener.ItemsListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Exo extends JavaPlugin {

    private Economy econ;
    private ItemsManager itemsManager;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe("Vault not found, disabling Exo");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.itemsManager = new ItemsManager(this);
        this.getServer().getPluginManager().registerEvents(new ItemsListener(this), this);
        this.getServer().getPluginCommand("item").setExecutor(new ItemCommand(this));
        getLogger().info("Exo enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Exo disabled");
    }

    public Economy getEconomy() {
        return econ;
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}