/*
               Copyright © 2019 - 2021
               Mathys - AddItemCommand
            Created on 2021/07/13
 */
package com.kuosai.supplydrop.commands;

import com.kuosai.supplydrop.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItemCommand extends Command {

    private final Core core;
    public AddItemCommand(Core core,  String name, String description, List<String> aliases, String permission, String message) {
        super(name, description, ChatColor.RED + "Usage: /<command> <percentage>", aliases);
        if(!permission.equalsIgnoreCase("NULL")){
            super.setPermission(permission);
            super.setPermissionMessage(message);
        }

        this.core = core;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(super.getUsage().replace("<command>", commandLabel));
            return false;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getType() == Material.AIR){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("incorrectItemInHand")));
            return false;
        }

        try{
            float weight = Float.parseFloat(args[0]);

            if(weight >= 100 || weight <= 0){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("weightAsAnIncorrectValue").replace("%number%", String.valueOf(weight))));
                return false;
            }


            this.core.getItemManager().addItem(itemStack, weight);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("successfullyAddItem").replace("%itemName%", itemStack.getType().name().toLowerCase())));
            return true;
        }catch (Exception exception){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("weightCanOnlyBeNumber").replace("%value%", args[0])));
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return new ArrayList<>(Arrays.asList(String.valueOf(10),String.valueOf(20),String.valueOf(30),String.valueOf(40),String.valueOf(50)));
    }
}
