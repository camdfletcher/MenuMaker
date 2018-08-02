package me.cdnmflip.menumaker.listener;

import me.cdnmflip.menumaker.MenuMaker;
import me.cdnmflip.menumaker.struct.Item;
import me.cdnmflip.menumaker.struct.Menu;
import me.cdnmflip.menumaker.struct.MenuPartition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        int slotClicked = event.getSlot();

        if (!MenuMaker.getMenuRegistry().containsKey(player.getUniqueId())) return; // We only care if the player is viewing a custom inventory

        Menu menu = MenuMaker.getMenuRegistry().get(player.getUniqueId());

        for (int i = menu.getPartitions().size(); i >= 0; i--) {
            MenuPartition partition = menu.getPartitions().get(i);
            Optional<Item> optionalItem = partition.getItemOnPartition(slotClicked);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();

                if (item.isCancelClick()) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                }

                if (item.getAction() != null) item.getAction().accept(player, event);
            }
        }
    }

}
