<p align="center"><img src="https://i.imgur.com/sqCLkNK.png" alt="Header"/></p>

### Premise
Historically, inventory/gui creation in Bukkit has been very tedious and unintuitive. Most of the time, if you are looking to create more than one inventory, chances are you've utilized a menu api that sped up the process of creating your gui's.

MenuMaker acts as a simple library that aims to allow developers to create functional, interactive, and easily understandable menus, with all of the complex and tedious parts taken care of for you. Out of sight, out of mind. :see_no_evil:

### Workflow
#### Components
Everything in MenuMaker is split up between 3 components
1. `Menu`
2. `MenuPartition`
3. `Item`

<p align="center"><img src="https://i.imgur.com/tmM2rTu.png" alt="Workflow"/></p>

At the lowest level, `Item`s act as an interface that allow developers to bind specific items to actions that occur when clicked. `Item`s are what make up `MenuPartition`s, the next component on our list.

`MenuPartitions` can be equated to "layers" of a `Menu`. Similar to layers one may utilize in Photoshop, `MenuPartitions` (analogous to layers), allow you to overlay and stack different layouts ontop of each other in your menu. For example, if your menu needs to have glass borders, it is recommended you place those borders in a separate partition. The first partition you register will always be the bottom-most layer in the menu. If you would like to overlay a partition on-top of another, make sure it is registered after the target partition. 

Lastly, `Menu`, is the parent object that houses all of the former components. `Menu` is the primary interface that will be used to coordinate the construction and display of your custom menu. The `Menu` class contains all of the necessary functions required in order to present your menu to a player.

#### Implementation
Before you're able to begin using MenuMaker, you must first add it to your dependency list, and register it in your `onEnable()` or `onLoad()` method (in your main class).

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

```xml
<dependency>
    <groupId>com.github.codenameflip</groupId>
    <artifactId>MenuMaker</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

```java
private MenuMaker menuMaker;

@Override
public void onEnable() {
    // ...
    menuMaker = new MenuMaker(this);
    menuMaker.setup();
}

@Override
public void onDisable() {
    // (optional, but recommended)
    menuMaker.destroy();
}
```

Once you're hooked into the API, below is an example of a menu created and handled by MenuMaker...

```java
Menu menu = new Menu("A cool menu!", InventoryType.CHEST);

Item boringItem = Item.builder()
        .itemStack(new ItemStack(Material.GOLD_INGOT))
        .cancelClick(true)
        .build();

Item coolItem = Item.builder()
        .itemStack(new ItemStack(Material.GOLD_BLOCK))
        .cancelClick(true)
        .action((player, event) -> player.sendMessage(ChatColor.BLUE + "You clicked the cool item!"))
        .build();

menu.appendPartition(new MenuPartition("000111000", (partition) -> {
    // partition.nextItem(boringItem);
    // partition.nextItem(boringItem);      Populates all 3 slots
    // partition.nextItem(boringItem);
    
    // Or, the cleaner alternative...
    Repeatedly.execute(3, () -> partition.nextItem(boringItem));
}));
menu.appendPartition(new MenuPartition("000010000", (partition) -> partition.nextItem(coolItem)));

menu.showPlayer(targetPlayer);
```
