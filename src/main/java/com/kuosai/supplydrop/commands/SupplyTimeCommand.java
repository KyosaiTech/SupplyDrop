/*
               Copyright © 2019 - 2021
               Mathys - SupplyTimeCommand
            Created on 2021/07/12
 */
package com.kuosai.supplydrop.commands;

import com.kuosai.supplydrop.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SupplyTimeCommand extends Command {

    private final Core core;

    public SupplyTimeCommand(Core core,  String name, String description, List<String> aliases, String permission, String message) {
        super(name, description, null, aliases);
        if(!permission.equalsIgnoreCase("NULL")){
            super.setPermission(permission);
            super.setPermissionMessage(message);
        }

        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("messageTimeLeft").replace("%time%", this.core.getSupplyManager().getTimeLeft())));
        return false;
    }
}
