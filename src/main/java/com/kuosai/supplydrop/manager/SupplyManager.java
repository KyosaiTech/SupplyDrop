/*
               Copyright © 2019 - 2021
               Mathys - SupplyManager
            Created on 2021/07/11
 */
package com.kuosai.supplydrop.manager;

import com.kuosai.supplydrop.Core;
import com.kuosai.supplydrop.utils.ItemBuilder;
import com.kuosai.supplydrop.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SupplyManager {

    private final Core core;

    private int timeLeft;

    private boolean forceStop = false;

    private Location currentDrop = null;

    public SupplyManager(Core core) {
        this.core = core;
    }

    public void spawnSupplyDrop(){
        Location randomLocation = LocationUtils.randomLocation(this.core.getConfigValues().getRandomLocationWorld(), core);

        Bukkit.broadcastMessage(this.core.getConfigValues().getLangConfig().getString("supplyDropSpawned").replace("%x%", String.valueOf(randomLocation.getX()))
                                                                                                                .replace("%y%", String.valueOf(randomLocation.getY()))
                                                                                                                .replace("%z%", String.valueOf(randomLocation.getZ()))
                                                                                                                .replace("&", "§"));

        randomLocation.getBlock().setType(Material.CHEST);
        randomLocation.getBlock().setMetadata("supplydrop", new FixedMetadataValue(this.core, randomLocation.getY()));
        this.currentDrop = randomLocation.getBlock().getLocation();
        setSupplyLoot((Chest) randomLocation.getBlock().getState());

        spawnParticles(randomLocation);
    }

    private void setSupplyLoot(Chest chest){
        Inventory inventory = chest.getBlockInventory();

        if (!this.core.getItemManager().hasItem()) {
            inventory.setItem(13, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(ChatColor.RED + "NO ITEM HAS BEEN SET").toItemStack());
            return;
        }

        for (int i = 0; i < this.core.getConfigValues().getSupplyAmountItems(); i++) {
            ItemStack it = this.core.getItemManager().getRandomItem();
            if(it == null) continue;
            int random = new Random().nextInt(inventory.getSize() - 1);
            inventory.setItem(random, it);
        }
    }

    public void forceStop(){
        this.forceStop = true;
    }

    public Location getCurrentDrop() {
        return currentDrop;
    }

    @SuppressWarnings("all")
    public void spawnParticles(Location location){

        new BukkitRunnable() {
            @Override
            public void run() {
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0D, 3.0D, 0D, 0.01);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0D, 2.0D, 0D, 0.01);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0D, 4.0D, 0D, 0.005);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0.1D, 4.0D, 0.1D, 0.01);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0.1D, 3.0D, 0.1D, 0.01);
                location.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0, 0.1D, 3.5D, -0.1D, 0.015);


                if (!location.getBlock().hasMetadata("supplydrop")) {
                    cancel();
                }
            }
        }.runTaskTimer(this.core, 35, 35);


    }

    public void automaticSpawn(){
        this.timeLeft = this.core.getConfigValues().getAutomaticSpawnTime();
        new BukkitRunnable() {
            @Override
            public void run() {
                if(forceStop){
                    forceStop = false;
                    cancel();
                    return;
                }
                timeLeft--;

                if(timeLeft <= 0){
                    spawnSupplyDrop();
                    cancel();
                }
            }
        }.runTaskTimer(this.core, 20, 20);
    }

    public String getTimeLeft() {
        int timeCopy = this.timeLeft / 1000;

        long s = timeCopy % 60;
        long m = (timeCopy / 60) % 60;
        long h = (timeCopy / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

}
