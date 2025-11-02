package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



public class DespiseTheWeak extends RageAbility implements AddonAbility {

    private long cooldown;
    private double damage;
    private double backlashDamage;
    private double healthThreshold;

    private Vector direction;
    private Location currentLocation;
    private int distance;
    private int particleCount;

    public DespiseTheWeak(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        direction = player.getLocation().getDirection().normalize();
        currentLocation = player.getEyeLocation().clone();
        distance = 0;

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.DespiseTheWeak.Cooldown");
        damage = ConfigManager.getConfig().getDouble("Rage.DespiseTheWeak.Damage");
        backlashDamage = ConfigManager.getConfig().getDouble("Rage.DespiseTheWeak.BacklashDamage");
        healthThreshold = ConfigManager.getConfig().getDouble("Rage.DespiseTheWeak.HealthThreshold");
        particleCount = ConfigManager.getConfig().getInt("Rage.DespiseTheWeak.ParticleCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (distance >= 3) {
            bPlayer.addCooldown(this);
            remove();
            return;
        }

        currentLocation.add(direction.clone().multiply(0.5));
        distance++;

        spawnParticles(currentLocation, particleCount);

        for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, 1, 1, 1)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                LivingEntity target = (LivingEntity) entity;

               DamageHandler.damageEntity(target, damage, this);

                if (target.getHealth() > healthThreshold) {
                   DamageHandler.damageEntity(player, backlashDamage, this);
                }

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
        return "DespiseTheWeak";
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
        return "A standard punch. Deals damage to weak enemies, but damages you if they're strong.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
