package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChaoticStomp extends RageAbility implements AddonAbility {

    private long cooldown;
    private int radius;
    private int pillarHeight;
    private int pillarCount;

    private List<Location> pillarLocations;
    private int currentPillar;
    private int currentHeight;

    public ChaoticStomp(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        pillarLocations = new ArrayList<>();
        Random random = new Random();
        Location center = player.getLocation();

        for (int i = 0; i < pillarCount; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = random.nextDouble() * radius;

            int x = (int) (center.getX() + Math.cos(angle) * distance);
            int z = (int) (center.getZ() + Math.sin(angle) * distance);
            int y = center.getWorld().getHighestBlockYAt(x, z);

            pillarLocations.add(new Location(center.getWorld(), x, y, z));
        }

        currentPillar = 0;
        currentHeight = 0;

        bPlayer.addCooldown(this);
        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.ChaoticStomp.Cooldown");
        radius = ConfigManager.getConfig().getInt("Rage.ChaoticStomp.Radius");
        pillarHeight = ConfigManager.getConfig().getInt("Rage.ChaoticStomp.PillarHeight");
        pillarCount = ConfigManager.getConfig().getInt("Rage.ChaoticStomp.PillarCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (currentPillar >= pillarLocations.size()) {
            remove();
            return;
        }

        if (currentHeight >= pillarHeight) {
            currentPillar++;
            currentHeight = 0;
            return;
        }

        Location pillarLoc = pillarLocations.get(currentPillar);
        Block block = pillarLoc.getWorld().getBlockAt(
            pillarLoc.getBlockX(),
            pillarLoc.getBlockY() + currentHeight + 1,
            pillarLoc.getBlockZ()
        );

        if (block.getType() == Material.AIR) {
            block.setType(Material.STONE);
        }

        currentHeight++;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public String getName() {
        return "ChaoticStomp";
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public boolean isSneakAbility() {
        return false;
    }

    @Override
    public String getAuthor() {
        return "Hihelloy";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public void load() {}

    @Override
    public void stop() {}

    @Override
    public String getDescription() {
        return "Stomp the ground to create random pillars around you.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
