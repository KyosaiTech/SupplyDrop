/*
               Copyright © 2019 - 2021
               Mathys - ItemManager
            Created on 2021/07/12
 */
package com.kuosai.supplydrop.manager;


import com.kuosai.supplydrop.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ItemManager {

    private final Map<ItemStack, Float> weightMap = new HashMap<>();
    private final Core core;

    public ItemManager(Core core) {
        this.core = core;
        this.importItems();
    }

    public void importItems(){
        try{
            for (String keys : this.core.getConfigValues().getDataConfig().getConfigurationSection("items").getKeys(false)) {
                ConfigurationSection section = this.core.getConfigValues().getDataConfig().getConfigurationSection("items." + keys);
                ItemStack itemStack = section.getItemStack("item");
                float weight = (float) section.getDouble("weight");
                this.weightMap.put(itemStack, weight);
            }
        }catch (NullPointerException ignore){

        }
    }

    public void saveItems(){
        List<ItemStack> items = new ArrayList<>(Arrays.asList(this.weightMap.keySet().toArray(new ItemStack[0])));
        File file = new File(core.getDataFolder() + "/data/items.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("items", null);


        for (int i = 0; i < items.size(); i++) {
            System.out.println(i);
            config.set("items." + i + ".item", items.get(i));
            config.set("items." + i + ".weight", weightMap.get(items.get(i)));
        }

        try{
            config.save(file);
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void addItem(ItemStack itemStack, float weight){
        this.weightMap.put(itemStack, weight);
    }

    public void remove(ItemStack itemStack, Player player){
        if(this.weightMap.containsKey(itemStack)){
            this.weightMap.remove(itemStack);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("successfullyRemoveItem").replace("%itemName%", itemStack.getType().name().toLowerCase())));
        }else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.core.getConfigValues().getLangConfig().getString("itemNotFound").replace("%itemName%", itemStack.getType().name().toLowerCase())));
        }
    }

    public boolean hasItem() {
        if(weightMap.isEmpty()) return false;
        return true;
    }

    public ItemStack getRandomItem(){

        if(weightMap.isEmpty()) return null;

        float randomNumber = (float) Math.random() * weightMap.size();
        float accumulatedChance = 0f;

        for (Map.Entry<ItemStack, Float> itemStackWeightEntries : weightMap.entrySet()) {
            accumulatedChance += (((itemStackWeightEntries.getValue() * weightMap.size()) / 100));
            if(accumulatedChance >= randomNumber){
                return itemStackWeightEntries.getKey();
            }
        }

        Set<ItemStack> items = this.weightMap.keySet();


        return (ItemStack) items.toArray()[items.size() - 1];
    }

    public Map<ItemStack, Float> getItemMap() {
        return this.weightMap;
    }
}
