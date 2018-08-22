package me.cdnmflip.menumaker.struct;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.cdnmflip.menumaker.MenuMaker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class Menu {

    @Getter
    private final String title;
    @Getter
    private final InventoryType type;
    @Getter
    private final List<MenuPartition> partitions = Lists.newArrayList();
    @Getter
    private Inventory lastBuiltInventory = null;

    public Menu(String title, InventoryType type) {
        this.title = title;
        this.type = type;
        MenuMaker.getMenus().add(this); // Register with the API
    }

    /**
     * Adds a {@link MenuPartition} object to the {@link Menu}'s partitions registry
     *
     * @param partition The {@link MenuPartition} that you'd like to register
     * @return The {@link Menu} you just modified (for builder purposes)
     */
    public Menu appendPartition(MenuPartition partition) {
        this.partitions.add(partition);
        return this;
    }

    /**
     * Generates an {@link Inventory} object that will be displayed to a player
     *
     * @return
     */
    private Inventory constructInventory() {
        int maxSize = 0;

        for (MenuPartition partition : partitions) {
            int size = partition.getRequiredInventorySize();
            if (size > maxSize) maxSize = size;
        }

        Inventory inventory = this.type == InventoryType.CHEST
                ? Bukkit.createInventory(null, maxSize, this.title)
                : Bukkit.createInventory(null, this.type, this.title);

        partitions.forEach(p -> {
            p.getExecutor().accept(p);
            p.construct(inventory);
        });

        this.lastBuiltInventory = inventory;
        return inventory;
    }

    /**
     * Displays a generated menu to a {@link Player}
     *
     * @param player The player you'd like to show the menu to
     */
    public void showPlayer(Player player) {
        MenuMaker.getMenuRegistry().put(player.getUniqueId(), this);
        player.openInventory(constructInventory());
    }

}
