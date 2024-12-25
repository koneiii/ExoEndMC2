package fr.koneiii.exo.items;

import fr.koneiii.exo.Exo;
import fr.koneiii.exo.items.types.HerosSwordItem;
import fr.koneiii.exo.items.types.KamikazItem;
import fr.koneiii.exo.items.types.ThiefHoeItem;

import java.util.HashMap;
import java.util.Map;

public class ItemsManager {

    private Exo plugin;

    private Map<String, AItem> items;

    public ItemsManager(Exo plugin) {
        this.plugin = plugin;
        this.items = new HashMap<>();
        this.addItem(new KamikazItem());
        this.addItem(new ThiefHoeItem(this.plugin));
        this.addItem(new HerosSwordItem());
    }

    public AItem getItem(String id) {
        return this.items.get(id);
    }

    public void addItem(AItem item) {
        this.items.put(item.getId(), item);
    }

    public Map<String, AItem> getItems() {
        return items;
    }
}
