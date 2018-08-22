package me.cdnmflip.menumaker;

import lombok.Getter;
import me.cdnmflip.menumaker.struct.Item;
import me.cdnmflip.menumaker.struct.Menu;
import me.cdnmflip.menumaker.struct.MenuPartition;
import me.cdnmflip.menumaker.utilities.Repeatedly;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin implements CommandExecutor {

    @Getter
    private MenuMaker menuMaker;

    @Override
    public void onEnable() {
        menuMaker = new MenuMaker(this);
        menuMaker.setup();

        getCommand("menutest").setExecutor(this);
    }

    @Override
    public void onDisable() {
        menuMaker.destroy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (!sender.isOp()) return true;

        Player player = (Player) sender;
        Item boringItem = Item.builder()
                .itemStack(new ItemStack(Material.GOLD_INGOT))
                .cancelClick(true)
                .action((p, event) -> p.sendMessage("You clicked a boring item. :("))
                .build();

        Item fancyItem = Item.builder()
                .itemStack(new ItemStack(Material.GOLD_BLOCK))
                .cancelClick(true)
                .action((p, event) -> p.sendMessage("You clicked a fancy item!"))
                .build();

        Menu menu = new Menu("Fancy custom menu", InventoryType.CHEST);
        menu.appendPartition(new MenuPartition("000111000", partition -> Repeatedly.execute(3, () -> partition.nextItem(boringItem))));
        menu.appendPartition(new MenuPartition("000010000", partition -> partition.nextItem(fancyItem)));

        menu.showPlayer(player);
        return true;
    }

}
