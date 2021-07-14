/*
               Copyright © 2019 - 2021
               Mathys - GUIInteract
            Created on 2021/07/14
 */
package com.kuosai.supplydrop.listeners;

import com.kuosai.supplydrop.utils.GUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GUIInteract implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory inventory = event.getInventory();

        if(inventory.getHolder() instanceof GUI){
            if(event.getCurrentItem() == null) return;
            GUI gui = (GUI) inventory.getHolder();
            gui.handle(event);
        }
    }
}
