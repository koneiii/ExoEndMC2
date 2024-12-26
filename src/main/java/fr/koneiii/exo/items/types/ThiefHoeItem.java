package fr.koneiii.exo.items.types;

import fr.koneiii.exo.Exo;
import fr.koneiii.exo.items.AItem;
import fr.koneiii.exo.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class ThiefHoeItem extends AItem {

    private Exo exo;
    private static final int DISTANCE = 50;

    public ThiefHoeItem(Exo exo) {
        super("thiefhoe", "§dHoue du voleur", Material.DIAMOND_HOE);
        this.exo = exo;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        ItemBuilder item = new ItemBuilder(event.getItem());
        if (item.getNBTString("cooldown") != null && System.currentTimeMillis() - Long.parseLong(item.getNBTString("cooldown")) < 10000) {
            event.getPlayer().sendMessage("§cVous devez attendre 10 minutes avant de réutiliser cet objet.");
            return;
        }

        event.getPlayer().getNearbyEntities(DISTANCE, DISTANCE, DISTANCE).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .max((p1, p2) -> (int) (exo.getEconomy().getBalance(p1) - exo.getEconomy().getBalance(p2)))
                .ifPresentOrElse(
                        target -> event.getPlayer().sendMessage("§eLa personne la plus riche à proximité est " + target.getName() +
                                " avec " + exo.getEconomy().getBalance(target) + "€."),
                        () -> event.getPlayer().sendMessage("§cAucun joueur à proximité.")
                );

        item.setNBTString("cooldown", String.valueOf(System.currentTimeMillis()));
        event.getPlayer().setItemInHand(item.toItemStack());
    }


}
