package me.cdnmflip.menumaker;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import me.cdnmflip.menumaker.listener.ClickListener;
import me.cdnmflip.menumaker.listener.CloseListener;
import me.cdnmflip.menumaker.struct.Menu;
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
    private static final Set<Menu> menus = Sets.newHashSet();
    @Getter
    private static final HashMap<UUID, Menu> menuRegistry = Maps.newHashMap();

    private static final List<Listener> listeners = Arrays.asList(
            new ClickListener(),
            new CloseListener()
    );

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
        listeners.forEach(listener -> pm.registerEvents(listener, plugin));
    }

    /**
     * Not required, but recommended in the #onDisable method of the implementing plugin
     */
    public void destroy() {
        System.out.println("[MenuMaker] Detached library. No menus will work from this point onward.");

        menus.clear();
        listeners.forEach(HandlerList::unregisterAll);
    }

}
