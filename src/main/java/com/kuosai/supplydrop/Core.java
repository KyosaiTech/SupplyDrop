/*
               Copyright © 2019 - 2021
               Mathys - Core
            Created on 2021/07/11
 */
package com.kuosai.supplydrop;

import com.kuosai.supplydrop.commands.*;
import com.kuosai.supplydrop.listeners.GUIInteract;
import com.kuosai.supplydrop.listeners.PlayerInteract;
import com.kuosai.supplydrop.listeners.PlayerJoin;
import com.kuosai.supplydrop.manager.ItemManager;
import com.kuosai.supplydrop.manager.SupplyManager;
import com.kuosai.supplydrop.utils.ConfigValue;
import com.kuosai.supplydrop.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Map;

public class Core extends JavaPlugin {

    private CommandMap bukkitCommandMap;
    private ConfigValue configValue;
    private YamlConfiguration langFile;

    private ItemManager itemManager;
    private SupplyManager supplyManager;

    private boolean hasNewUpdate = false;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        this.saveResource("messages/en_US.yml", false);
        this.saveResource("messages/fr_FR.yml", false);
        this.saveResource("messages/ru_RU.yml", false);
        this.saveResource("commands.yml", false);
        this.saveResource("data/items.yml", false);
    }

    @Override
    public void onEnable() {
        this.configValue = new ConfigValue(this.getConfig(), this);

        this.langFile = this.configValue.getLangConfig();

        initServerCommandMap();
        initServerListener();

        this.itemManager = new ItemManager(this);
        this.supplyManager = new SupplyManager(this);

        this.checkNewUpdate();

        if(this.configValue.isAutomaticSpawn()){
            this.supplyManager.automaticSpawn();
        }
    }

    @Override
    public void onDisable() {
        if(this.itemManager == null) return;
        this.itemManager.saveItems();
    }

    public void initServerCommandMap() {
        try {
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);
            this.bukkitCommandMap = (CommandMap) field.get(Bukkit.getPluginManager());

            registerCommands();
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private void registerCommands(){
        ConfigurationSection section = this.getConfigValues().getCommandConfig().getConfigurationSection("forcespawn");
        this.bukkitCommandMap.register("supplydrop", new ForceSpawnCommand(this, section.getString("name"), section.getString("description"), section.getStringList("aliases"), section.getString("permission"), section.getString("permission-message")));
        section = this.getConfigValues().getCommandConfig().getConfigurationSection("supplytime");
        this.bukkitCommandMap.register("supplydrop", new SupplyTimeCommand(this, section.getString("name"), section.getString("description"), section.getStringList("aliases"), section.getString("permission"), section.getString("permission-message")));
        section = this.getConfigValues().getCommandConfig().getConfigurationSection("guiremoveitem");
        this.bukkitCommandMap.register("supplydrop", new GuiRemoveItemCommand(this, section.getString("name"), section.getString("description"), section.getStringList("aliases"), section.getString("permission"), section.getString("permission-message")));
        section = this.getConfigValues().getCommandConfig().getConfigurationSection("additem");
        this.bukkitCommandMap.register("supplydrop", new AddItemCommand(this, section.getString("name"), section.getString("description"), section.getStringList("aliases"), section.getString("permission"), section.getString("permission-message")));
        section = this.getConfigValues().getCommandConfig().getConfigurationSection("sreload");
        this.bukkitCommandMap.register("supplydrop", new SReloadCommand(this, section.getString("name"), section.getString("description"), section.getStringList("aliases"), section.getString("permission"), section.getString("permission-message")));

    }

    private void initServerListener(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIInteract(), this);
    }

    private void checkNewUpdate(){
        new UpdateChecker(this, 94288).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                this.hasNewUpdate = false;
            } else {
                this.hasNewUpdate = true;
            }
        });
    }

    public ConfigValue getConfigValues() {
        return configValue;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public boolean hasNewUpdate() {
        return hasNewUpdate;
    }

    public SupplyManager getSupplyManager() {
        return this.supplyManager;
    }
}

