/*
               Copyright © 2019 - 2021
               Mathys - PlayerInteract
            Created on 2021/07/12
 */
package com.kuosai.supplydrop.listeners;

import com.kuosai.supplydrop.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    private final Core core;

    public PlayerInteract(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if(block == null) return;

        if(block.hasMetadata("supplydrop")){
            block.removeMetadata("supplydrop", core);
            block.breakNaturally();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("supplyDropCaptured").replace("%player%", player.getName()).replace("%x%", String.valueOf(block.getX())).replace("%y%", String.valueOf(block.getY())).replace("%z%", String.valueOf(block.getZ()))));

            if(block.getLocation().equals(this.core.getSupplyManager().getCurrentDrop())){
                this.core.getSupplyManager().automaticSpawn();
            }
        }
    }
}
