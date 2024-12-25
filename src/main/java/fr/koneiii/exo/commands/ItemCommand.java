package fr.koneiii.exo.commands;

import fr.koneiii.exo.Exo;
import fr.koneiii.exo.items.AItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand implements CommandExecutor {

    private Exo plugin;


    public ItemCommand(Exo plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                for (AItem item : this.plugin.getItemsManager().getItems().values()) {
                    ItemStack itemStack = item.giveItem(player).toItemStack();
                    player.getInventory().addItem(itemStack);
                    player.sendMessage("§aVous avez reçu l'item " + item.getName() + ".");
                }
                return true;
            } else if(strings.length == 1) {
                AItem item = this.plugin.getItemsManager().getItem(strings[0]);
                if (item != null) {
                    ItemStack itemStack = item.giveItem(player).toItemStack();
                    player.getInventory().addItem(itemStack);
                    player.sendMessage("§aVous avez reçu l'item " + item.getName() + ".");
                    return true;
                } else {
                    player.sendMessage("§cCet item n'existe pas.");
                    return false;
                }
            } else {
                player.sendMessage("§cUtilisation: /item [item]");
                return false;
            }
        } else {
            commandSender.sendMessage("§cVous devez être un joueur pour exécuter cette commande.");
        }
        return false;
    }
}
