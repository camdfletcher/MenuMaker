package me.cdnmflip.menumaker.struct;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class MenuPartition {

    @Getter
    private final HashMap<Integer, Item> contents = Maps.newHashMap();
    @Getter
    private final Consumer<MenuPartition> executor;
    private final List<Integer> availableSlots = Lists.newArrayList();
    private int lastUsedIndex = 0;
    private String layout;

    /**
     * Constructs a new MenuPartition object
     * <p>
     * NOTE:
     * Layouts are written in binary format...
     * "0" = No item should be placed in that slot
     * "1" = An item should be placed in that slot
     * <p>
     * The partition will be filled up sequentially in the order of defined "1"s
     * <p>
     * You cannot set an item for an individual slot, you must specify it as "1"
     * in the layout, and then use {@link #nextItem(Item)} to insert into the next
     * available slot
     * <p>
     * Rows will automatically be adjusted to accommodate for the largest partition in a {@link Menu}
     * <p>
     * For example, in the layout:
     * "000111000"
     * The first item to be filled with an item would be the first "1" that occurs
     * The second item to be filled would be the second "1" that occurs in the layout
     * ... and so on ...
     * <p>
     * By adding a space after 9 characters, a new row will be generated.
     * E.g.: "000000000 000000001"
     * - This would generate 2 rows, with one item in the last slot on the second row.
     * <p>
     * Partitions will stack on top of each other, in order of definition.
     * If you want to overlay a partition on-top of another, simply register it last in the Menu
     *
     * @param layout   How you would like the inventory to be structured
     * @param executor Called whenever the partition is being created, put all of the item code in here
     */
    public MenuPartition(String layout, Consumer<MenuPartition> executor) {
        this.layout = layout;
        this.executor = executor;

        int i = 0;
        for (String slot : layout.split("")) {
            if (slot.equals(" ")) continue;
            if (slot.equals("1")) availableSlots.add(i);
            i++;
        }
    }

    /**
     * Inserts an {@link Item} instance into the next available slot in the layout
     *
     * @param item The item you'd like to append to the partition
     */
    public void nextItem(Item item) {
        try {
            int slot = availableSlots.get(lastUsedIndex);
            contents.put(slot, item);
            lastUsedIndex++;
        } catch (IndexOutOfBoundsException e) {
            contents.put(layout.replaceAll(" ", "").length() - 1, item); // Default to last slot
        }
    }

    /**
     * Checks and returns an {@link Item} if present, at a specific slot location
     *
     * @param slot The slot you'd like to lookup
     * @return An optional containing an {@link Item} object
     */
    public Optional<Item> getItemOnPartition(int slot) {
        return Optional.ofNullable(contents.get(slot));
    }

    /**
     * Returns the number of slots that are needed in order to fit this partition's items/layout
     *
     * @return An integer multiple of 9 (0 < x <= 54)
     */
    int getRequiredInventorySize() {
        double size = layout.replaceAll(" ", "").length();
        return (int) (Math.ceil(size / 9) * 9);
    }

    /**
     * Sets an {@link Inventory}'s contents according to the items specified in this partitions layout
     *
     * @param inventory The inventory you'd like to fill
     */
    void construct(Inventory inventory) {
        this.contents.forEach((slot, item) -> {
            inventory.setItem(slot, item.getItemStack());
        });
    }


}
