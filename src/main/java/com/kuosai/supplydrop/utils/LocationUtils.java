/*
               Copyright © 2019 - 2021
               Mathys - LocationUtils
            Created on 2021/07/11
 */
package com.kuosai.supplydrop.utils;

import com.kuosai.supplydrop.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Random;

public class LocationUtils {

    public static Location randomLocation(World world, Core core) {
        Random random = new Random();

        int[] minMaxCoords = core.getConfigValues().getMinMaxCoords();

        int minX = minMaxCoords[0];
        int maxX = minMaxCoords[1];
        int minZ = minMaxCoords[2];
        int maxZ = minMaxCoords[3];

        int x = random.nextInt((maxX + 1) - minX) + minX;
        int z = random.nextInt((maxZ + 1) - minZ) + minZ;
        int y = world.getHighestBlockYAt(x, z);

        Location location = new Location(world, x, y, z);

        if(!core.getConfigValues().isSpawnOverWater()){
            while (location.getBlock().getType() == Material.WATER){
                int anotherX = random.nextInt((maxX + 1) - minX) + minX;
                int anotherZ = random.nextInt((maxZ + 1) - minZ) + minZ;
                int anotherY = world.getHighestBlockYAt(anotherX, anotherZ);

                location.setX(anotherX);
                location.setY(anotherY);
                location.setZ(anotherZ);
            }
        }

        location.setX(location.getX() + 0.5);
        location.setY(location.getY() + 1);
        location.setZ(location.getZ() + 0.5);
        return location;
    }

}
