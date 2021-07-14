/*
               Copyright © 2019 - 2021
               Mathys - ConfigValue
            Created on 2021/07/11
 */
package com.kuosai.supplydrop.utils;

import com.kuosai.supplydrop.Core;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigValue {

    private boolean pluginUpdate;
    private boolean spawnOverWater;
    private boolean automaticSpawn;
    private int automaticSpawnTime;
    private int supplyAmountItems;

    private int[] minMaxCoords;

    private World randomLocationWorld;

    private YamlConfiguration langConfig;
    private final YamlConfiguration dataConfig;
    private YamlConfiguration commandConfig;

    private final Core core;

    public ConfigValue(FileConfiguration config, Core core) {
        this.pluginUpdate = config.getBoolean("pluginUpdate");
        this.spawnOverWater = config.getBoolean("spawnOverWater");
        this.automaticSpawn = config.getBoolean("automaticSpawn");
        this.automaticSpawnTime = config.getInt("automaticSpawnTime");
        this.supplyAmountItems = config.getInt("supplyAmountItems");
        this.langConfig = YamlConfiguration.loadConfiguration(new File(core.getDataFolder() + "/messages/" + (config.getString("messageFile") == null ? "en_US" : config.getString("messageFile")) + ".yml"));
        this.dataConfig = YamlConfiguration.loadConfiguration(new File(core.getDataFolder() + "/data/items.yml"));
        this.commandConfig = YamlConfiguration.loadConfiguration(new File(core.getDataFolder() + "/commands.yml"));

        this.minMaxCoords = new int[]{config.getInt("randomLocation.minimumX"),config.getInt("randomLocation.maximumX"), config.getInt("randomLocation.minimumZ"),config.getInt("randomLocation.maximumZ")};

        this.randomLocationWorld = Bukkit.getWorld(config.getString("world") == null ? "world" : config.getString("world"));

        this.core = core;
    }

    public boolean isPluginUpdate() {
        return pluginUpdate;
    }

    public boolean isSpawnOverWater() {
        return spawnOverWater;
    }

    public boolean isAutomaticSpawn() {
        return automaticSpawn;
    }

    public int getAutomaticSpawnTime() {
        return automaticSpawnTime;
    }

    public YamlConfiguration getLangConfig() {
        return langConfig;
    }

    public World getRandomLocationWorld() {
        return randomLocationWorld;
    }

    public YamlConfiguration getDataConfig() {
        return dataConfig;
    }

    public int[] getMinMaxCoords() {
        return minMaxCoords;
    }

    public int getSupplyAmountItems() {
        return supplyAmountItems;
    }


    public YamlConfiguration getCommandConfig() {
        return commandConfig;
    }


    public void reloadConfig(){
        this.core.reloadConfig();
        FileConfiguration config = this.core.getConfig();
        this.pluginUpdate = config.getBoolean("pluginUpdate");
        this.spawnOverWater = config.getBoolean("spawnOverWater");
        this.automaticSpawn = config.getBoolean("automaticSpawn");
        this.automaticSpawnTime = config.getInt("automaticSpawnTime");
        this.supplyAmountItems = config.getInt("supplyAmountItems");
        this.langConfig = YamlConfiguration.loadConfiguration(new File(core.getDataFolder() + "/messages/" + (config.getString("messageFile") == null ? "en_US" : config.getString("messageFile")) + ".yml"));
        this.randomLocationWorld = Bukkit.getWorld(config.getString("world") == null ? "world" : config.getString("world"));
    }
}
