package fr.koneiii.exo.items;

import fr.koneiii.exo.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AItem {

    private String id;
    private String name;
    private Material material;

    public AItem(String id, String name, Material material) {
        this.id = id;
        this.name = name;
        this.material = material;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public void onUse(Player player){

    }

    public void onLeftClick(PlayerInteractEvent event){

    }

    public void onRightClick(PlayerInteractEvent event){

    }

    public void onAttack(EntityDamageByEntityEvent event){

    }

    public void onBlockBreak(Player player){

    }

    public void onBlockPlace(Player player){

    }

    public ItemBuilder giveItem(Player player){
        return new ItemBuilder(this.material)
                .setName(this.name)
                .setNBTString("id", this.id);

    }
}
