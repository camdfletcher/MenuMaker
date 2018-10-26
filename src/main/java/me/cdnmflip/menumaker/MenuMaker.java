package me.cdnmflip.menumaker;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import me.cdnmflip.menumaker.listener.ClickListener;
import me.cdnmflip.menumaker.listener.CloseListener;
import me.cdnmflip.menumaker.struct.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public final class MenuMaker {

    @Getter
    private final Plugin plugin;
    @Getter
    private final MenuMaker instance;

    @Getter
    private final Set<Menu> menus = Sets.newHashSet();
    @Getter
    private final HashMap<UUID, Menu> menuRegistry = Maps.newHashMap();


    public MenuMaker(Plugin plugin) {
        this.plugin = plugin;
        this.instance = this;
    }

    /**
     * Required in order for the library to function
     * Should be placed in the #onLoad or #onEnable function of the implementing plugin
     */
    public void setup() {
        System.out.println("[MenuMaker] Initialized library. All menus are now linked!");

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new ClickListener(this), this.plugin);
        pm.registerEvents(new CloseListener(this), this.plugin);
    }

    /**
     * Not required, but recommended in the #onDisable method of the implementing plugin
     */
    public void destroy() {
        System.out.println("[MenuMaker] Detached library. No menus will work from this point onward.");
        menus.clear();
    }

    /**
     * Checks whether or not a player is viewing a {@link Menu}
     *
     * @param uuid The {@link UUID} of the {@link Player} you'd like to check
     * @return is the player viewing the menu? true/false
     */
    public boolean isViewingMenu(UUID uuid) {
        return menuRegistry.containsKey(uuid);
    }

    /**
     * Wrapper for the UUID method
     */
    public boolean isViewingMenu(Player player) {
        return isViewingMenu(player.getUniqueId());
    }

    /**
     * Returns the {@link Menu} object that a specified {@link UUID} is viewing (if applicable)
     *
     * @param uuid The {@link UUID} of the {@link Player} that you'd like to check
     * @return An {@link Optional} containing the {@link Menu} object, if present
     */
    public Optional<Menu> getViewingMenu(UUID uuid) {
        return Optional.ofNullable(menuRegistry.get(uuid));
    }

    /**
     * Wrapper for the UUID method
     */
    public Optional<Menu> getViewingMenu(Player player) {
        return getViewingMenu(player.getUniqueId());
    }

}
