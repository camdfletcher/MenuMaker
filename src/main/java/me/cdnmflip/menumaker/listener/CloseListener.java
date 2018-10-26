package me.cdnmflip.menumaker.listener;

import lombok.RequiredArgsConstructor;
import me.cdnmflip.menumaker.MenuMaker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class CloseListener implements Listener {

    private final MenuMaker instance;

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        instance.getMenuRegistry().remove(uuid); // Will only remove if a key is present
    }

}
