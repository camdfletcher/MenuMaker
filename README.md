<p align="center"><img src="https://i.imgur.com/sqCLkNK.png" alt="header"/></p>

### Premise
Historically, inventory/gui creation in Bukkit has been very tedious and unintuitive. Most of the time, if you are looking to create more than one inventory, chances are you've utilized a menu api that sped up the process of creating your gui's.

MenuMaker acts as a simple library that aims to allow developers to create functional, interactive, and easily understandable menus, with all of the complex and tedious parts taken care of for you. Out of sight, out of mind. :see_no_evil:

#### Workflow
Everything in MenuMaker is split up between 3 components
1. Menu
2. MenuPartition
3. Item

### Implementation
Below is an example of a menu created and handled by MenuMaker...

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
