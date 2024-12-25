package fr.koneiii.exo.listener;

import fr.koneiii.exo.Exo;
import fr.koneiii.exo.items.AItem;
import fr.koneiii.exo.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemsListener implements Listener {

    private Exo plugin;

    public ItemsListener(Exo plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        if(!event.getItem().hasItemMeta()) return;
        if(!event.getItem().getItemMeta().hasDisplayName()) return;

        ItemStack item = event.getItem();
        ItemBuilder itemBuilder = new ItemBuilder(item);
        if(itemBuilder.hasNBTTag("id")){
            String id = itemBuilder.getNBTString("id");
            if(this.plugin.getItemsManager().getItem(id) != null){
                if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                    this.plugin.getItemsManager().getItem(id).onRightClick(event);
                } else if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
                    this.plugin.getItemsManager().getItem(id).onLeftClick(event);
                }
            }

        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
        if(event.getDamager() == null) return;
        if(event.getDamager() instanceof Player player){
            ItemStack item = player.getItemInHand();
            if(item == null) return;
            if(!item.hasItemMeta()) return;
            if(!item.getItemMeta().hasDisplayName()) return;

            ItemBuilder itemBuilder = new ItemBuilder(item);
            if(itemBuilder.hasNBTTag("id")){
                String id = itemBuilder.getNBTString("id");
                if(this.plugin.getItemsManager().getItem(id) != null){
                    this.plugin.getItemsManager().getItem(id).onAttack(event);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        if(event.getPlayer() == null) return;
        ItemStack item = event.getPlayer().getItemInHand();
        if(item == null) return;
        if(!item.hasItemMeta()) return;
        if(!item.getItemMeta().hasDisplayName()) return;

        ItemBuilder itemBuilder = new ItemBuilder(item);
        if(itemBuilder.hasNBTTag("id")){
            String id = itemBuilder.getNBTString("id");
            if(this.plugin.getItemsManager().getItem(id) != null){
                this.plugin.getItemsManager().getItem(id).onBlockBreak(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){
        if(event.getPlayer() == null) return;
        if(event.getMessage().equals("debug")){
            for(AItem item : this.plugin.getItemsManager().getItems().values()){
                ItemStack itemStack = item.giveItem(event.getPlayer()).toItemStack();
                event.getPlayer().getInventory().addItem(itemStack);
            }
        }
    }

}
