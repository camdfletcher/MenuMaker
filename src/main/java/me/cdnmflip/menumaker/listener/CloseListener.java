package me.cdnmflip.menumaker.listener;

import me.cdnmflip.menumaker.MenuMaker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class CloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        MenuMaker.getMenuRegistry().remove(uuid); // Will only remove if a key is present
    }

}
