package fr.koneiii.exo.items.types;

import fr.koneiii.exo.items.AItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KamikazItem extends AItem {

    public KamikazItem() {
        super("kamikaz", "§eKamikaz", Material.DIAMOND_AXE);
    }

    @Override
    public void onBlockBreak(Player player) {
        player.getWorld().createExplosion(player.getLocation(), 4.0f);
    }
}
