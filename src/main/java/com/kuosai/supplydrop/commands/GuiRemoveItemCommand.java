/*
               Copyright © 2019 - 2021
               Mathys - GuiRemoveItemCommand
            Created on 2021/07/13
 */
package com.kuosai.supplydrop.commands;

import com.kuosai.supplydrop.Core;
import com.kuosai.supplydrop.utils.GUI;
import com.kuosai.supplydrop.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class GuiRemoveItemCommand extends Command {

    private final Core core;
    public GuiRemoveItemCommand(Core core,  String name, String description, List<String> aliases, String permission, String message) {
        super(name, description, "Usage: /<command>", aliases);
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

        GUI gui = new GUI(player, core) {
            @Override
            public String getName() {
                return "&l&cItems Management";
            }

            @Override
            public int getSize() {
                return 9*6;
            }

            @Override
            public void handle(InventoryClickEvent event) {
                event.setCancelled(true);

                if(event.getClick().isLeftClick()){
                    ItemBuilder builder = new ItemBuilder(event.getCurrentItem());
                    ItemStack it = builder.getFromMetadata();

                    core.getItemManager().remove(it, player);
                    setItems();
                }
            }

            @Override
            public void setItems() {
                this.getInventory().clear();
                for (Map.Entry<ItemStack, Float> entries : core.getItemManager().getItemMap().entrySet()) {
                    ItemStack itemStack = new ItemStack(entries.getKey());
                    itemStack.setItemMeta(entries.getKey().getItemMeta());
                    ItemBuilder builder = new ItemBuilder(itemStack);
                    builder.addLoreLine(ChatColor.RED +  "Percentage : " + entries.getValue() + " %");
                    builder.addLoreLine(ChatColor.RED + "Left-Click to Remove");
                    builder.addMetaData(entries.getKey());
                    getInventory().addItem(itemStack);
                }
            }
        };

        gui.open();
        return false;
    }
}
