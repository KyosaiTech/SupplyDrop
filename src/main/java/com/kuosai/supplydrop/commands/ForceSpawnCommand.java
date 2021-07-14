/*
               Copyright © 2019 - 2021
               Mathys - ForceSpawnCommand
            Created on 2021/07/12
 */
package com.kuosai.supplydrop.commands;

import com.kuosai.supplydrop.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ForceSpawnCommand extends Command {

    private final Core core;

    public ForceSpawnCommand(Core core,  String name, String description, List<String> aliases, String permission, String message) {
        super(name, description, "Usage: /<command>", aliases);
        if(!permission.equalsIgnoreCase("NULL")){
            super.setPermission(permission);
            super.setPermissionMessage(message);
        }

        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        this.core.getSupplyManager().forceStop();
        this.core.getSupplyManager().spawnSupplyDrop();
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return super.tabComplete(sender, alias, args);
    }
}
