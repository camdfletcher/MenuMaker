package me.cdnmflip.menumaker.listener;

import lombok.RequiredArgsConstructor;
import me.cdnmflip.menumaker.MenuMaker;
import me.cdnmflip.menumaker.struct.Item;
import me.cdnmflip.menumaker.struct.Menu;
import me.cdnmflip.menumaker.struct.MenuPartition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class ClickListener implements Listener {

    private final MenuMaker instance;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int slotClicked = event.getSlot();

        Optional<Menu> optional = instance.getViewingMenu(player);

        if (!optional.isPresent())
            return; // We only care if the player is viewing a custom inventory

        Menu menu = optional.get();

        for (int i = menu.getPartitions().size() - 1; i >= 0; i--) {
            MenuPartition partition = menu.getPartitions().get(i);
            Optional<Item> optionalItem = partition.getItemOnPartition(slotClicked);

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();

                if (item.isCancelClick()) {
                    event.setCancelled(true);
                    event.setResult(Event.Result.DENY);
                }

                if (item.getAction() != null) item.getAction().accept(player, event);
                break;
            }
        }
    }

}
