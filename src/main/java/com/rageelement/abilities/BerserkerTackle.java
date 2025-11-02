package com.rageelement.abilities;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BerserkerTackle extends RageAbility implements AddonAbility {

    private long cooldown;
    private int distance;
    private double speed;
    private double hitDamage;
    private double missDamage;
    private int stunDuration;

    private Vector direction;
    private Location currentLocation;
    private int travelledDistance;
    private boolean hitTarget;
    private int particleCount;

    public BerserkerTackle(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        direction = player.getLocation().getDirection().normalize();
        currentLocation = player.getLocation().clone();
        travelledDistance = 0;
        hitTarget = false;

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.BerserkerTackle.Cooldown");
        distance = ConfigManager.getConfig().getInt("Rage.BerserkerTackle.Distance");
        speed = ConfigManager.getConfig().getDouble("Rage.BerserkerTackle.Speed");
        hitDamage = ConfigManager.getConfig().getDouble("Rage.BerserkerTackle.HitDamage");
        missDamage = ConfigManager.getConfig().getDouble("Rage.BerserkerTackle.MissDamage");
        stunDuration = ConfigManager.getConfig().getInt("Rage.BerserkerTackle.StunDuration");
        particleCount = ConfigManager.getConfig().getInt("Rage.BerserkerTackle.ParticleCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }


        if (travelledDistance >= distance) {
            if (!hitTarget) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, stunDuration / 50, 10));
             DamageHandler.damageEntity(player, missDamage, this);
            }
            bPlayer.addCooldown(this);
            remove();
            return;
        }


        Location nextLocation = currentLocation.clone().add(direction.clone().multiply(speed));
        Block nextBlock = nextLocation.getBlock();


        if (GeneralMethods.isSolid(nextBlock) || isWater(nextBlock)) {
            spawnParticles(currentLocation, particleCount);
            bPlayer.addCooldown(this);


            if (!hitTarget) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, stunDuration / 50, 10));
                DamageHandler.damageEntity(player, missDamage, this);
            }

            remove();
            return;
        }


        currentLocation = nextLocation;
        player.teleport(currentLocation);
        travelledDistance++;

        spawnParticles(currentLocation, particleCount);


        for (Entity entity : currentLocation.getWorld().getNearbyEntities(currentLocation, 1.5, 1.5, 1.5)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                LivingEntity target = (LivingEntity) entity;
               DamageHandler.damageEntity(target, hitDamage, this);
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, stunDuration / 50, 10));
                hitTarget = true;
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
        return "BerserkerTackle";
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
        return "Dash forward rapidly. Hit an enemy to stun them, miss to stun yourself.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
