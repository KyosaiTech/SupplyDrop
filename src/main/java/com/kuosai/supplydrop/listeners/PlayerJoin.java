/*
               Copyright © 2019 - 2021
               Mathys - PlayerJoin
            Created on 2021/07/12
 */
package com.kuosai.supplydrop.listeners;

import com.kuosai.supplydrop.Core;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private final Core core;

    public PlayerJoin(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("supplydrop.newversion") && this.core.getConfigValues().isPluginUpdate() && this.core.hasNewUpdate()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("supplyDropUpdate")));
        }
    }
}
