package me.cdnmflip.menumaker.struct;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

@Builder
@RequiredArgsConstructor
public class Item {

    @Getter
    private final ItemStack itemStack;
    @Getter
    private final BiConsumer<Player, InventoryClickEvent> action;
    @Getter
    private final boolean cancelClick;

}
