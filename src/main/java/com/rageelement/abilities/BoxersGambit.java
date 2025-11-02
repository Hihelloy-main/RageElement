package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.rageelement.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.RageElement;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BoxersGambit extends RageAbility implements AddonAbility {

    private long cooldown;
    private int range;
    private int speedDuration;
    private int speedAmplifier;
    private int slownessDuration;
    private int slownessAmplifier;

    private Location currentLocation;
    private Vector direction;
    private int distanceTravelled;
    private boolean hit;

    public BoxersGambit(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        currentLocation = player.getEyeLocation().clone();
        direction = player.getLocation().getDirection().normalize();
        distanceTravelled = 0;
        hit = false;

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.BoxersGambit.Cooldown");
        range = ConfigManager.getConfig().getInt("Rage.BoxersGambit.Range");
        speedDuration = ConfigManager.getConfig().getInt("Rage.BoxersGambit.SpeedDuration");
        speedAmplifier = ConfigManager.getConfig().getInt("Rage.BoxersGambit.SpeedAmplifier");
        slownessDuration = ConfigManager.getConfig().getInt("Rage.BoxersGambit.SlownessDuration");
        slownessAmplifier = ConfigManager.getConfig().getInt("Rage.BoxersGambit.SlownessAmplifier");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (distanceTravelled >= range) {
            if (!hit) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, slownessDuration, slownessAmplifier));
            }
            bPlayer.addCooldown(this);
            remove();
            return;
        }

        currentLocation.add(direction);
        distanceTravelled++;

        ParticleEffect.CRIT.display(currentLocation, 3, 0.1, 0.1, 0.1, 0);

        for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, 1, 1, 1)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration, speedAmplifier));
                hit = true;
                bPlayer.addCooldown(this);
                remove();
                return;
            }
        }
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    @Override
    public String getName() {
        return "BoxersGambit";
    }

    @Override
    public boolean isHarmlessAbility() {
        return true;
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
        return "Throw a jab. Hit to gain speed, miss to get slowness.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
