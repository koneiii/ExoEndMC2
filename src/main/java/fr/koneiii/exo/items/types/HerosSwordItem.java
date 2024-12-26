package fr.koneiii.exo.items.types;

import fr.koneiii.exo.items.AItem;
import fr.koneiii.exo.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class HerosSwordItem extends AItem {

    public HerosSwordItem() {
        super("herossword", "§dÉpée du héros", Material.DIAMOND_SWORD);
    }

    @Override
    public void onLeftClick(PlayerInteractEvent event) {
        ItemBuilder itemBuilder = new ItemBuilder(event.getItem());
        if(!itemBuilder.hasNBTTag("owner") || !itemBuilder.getNBTString("owner")
                .equals(event.getPlayer().getUniqueId().toString())) {
            event.getPlayer().sendMessage(itemBuilder.hasNBTTag("owner") ? "§cVous ne pouvez pas utiliser cette épée !" :
                    "§aVous avez lié cette épée à vous !");
            if(!itemBuilder.hasNBTTag("owner")) {
                itemBuilder.setNBTString("owner", event.getPlayer().getUniqueId().toString());
                event.getPlayer().setItemInHand(itemBuilder.toItemStack());
            }
        }
    }

    @Override
    public void onAttack(EntityDamageByEntityEvent event) {
        ItemBuilder itemBuilder = new ItemBuilder(((Player) event.getDamager()).getItemInHand());
        if(itemBuilder.hasNBTTag("owner") && !Objects.equals(itemBuilder.getNBTString("owner"),
                event.getDamager().getUniqueId().toString()))
            event.setCancelled(true);
    }
}
