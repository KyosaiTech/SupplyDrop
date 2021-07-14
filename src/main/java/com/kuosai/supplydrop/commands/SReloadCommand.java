/*
               Copyright © 2019 - 2021
               Mathys - SReloadCommand
            Created on 2021/07/14
 */
package com.kuosai.supplydrop.commands;

import com.kuosai.supplydrop.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SReloadCommand extends Command {

    private final Core core;

    public SReloadCommand(Core core, String name, String description, List<String> aliases, String permission, String message) {
        super(name, description, "Usage: /<command>", aliases);
        if(!permission.equalsIgnoreCase("NULL")){
            super.setPermission(permission);
            super.setPermissionMessage(message);
        }

        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        this.core.getConfigValues().reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("commandReloadSuccessfull")));
        return false;
    }
}
