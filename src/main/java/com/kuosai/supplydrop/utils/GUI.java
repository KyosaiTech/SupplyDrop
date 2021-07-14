/*
               Copyright © 2019 - 2021
               Mathys - GUI
            Created on 2021/07/14
 */
package com.kuosai.supplydrop.utils;

import com.kuosai.supplydrop.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUI implements InventoryHolder {

    private final Player player;
    private final Core core;
    private Inventory inventory;

    public GUI(Player player, Core core) {
        this.player = player;
        this.core = core;
    }

    public abstract String getName();
    public abstract int getSize();
    public abstract void handle(InventoryClickEvent event);
    public abstract void setItems();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void open(){
        this.inventory = Bukkit.createInventory(this, getSize(), ChatColor.translateAlternateColorCodes('&', getName()));
        this.setItems();
        this.player.openInventory(this.getInventory());
    }
}
