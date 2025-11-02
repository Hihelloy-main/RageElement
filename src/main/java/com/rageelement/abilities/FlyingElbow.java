package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



public class FlyingElbow extends RageAbility implements AddonAbility {

    private long cooldown;
    private double launchDamage;
    private double landDamage;
    private double launchPower;
    private double divePower;

    private boolean launched;
    private boolean diving;
    private Vector diveDirection;
    private long launchTime;
    private int particleCount;

    public FlyingElbow(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        launched = false;
        diving = false;
        diveDirection = null;

        Vector launchVector = new Vector(0, launchPower, 0);
        player.setVelocity(launchVector);

        for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof LivingEntity) {
               DamageHandler.damageEntity((LivingEntity) entity, launchDamage, this);
            }
        }

       DamageHandler.damageEntity(player, launchDamage, this);
        spawnParticles(player.getLocation(), particleCount);

        launched = true;
        launchTime = System.currentTimeMillis();

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.FlyingElbow.Cooldown");
        launchDamage = ConfigManager.getConfig().getDouble("Rage.FlyingElbow.LaunchDamage");
        landDamage = ConfigManager.getConfig().getDouble("Rage.FlyingElbow.LandDamage");
        launchPower = ConfigManager.getConfig().getDouble("Rage.FlyingElbow.LaunchPower");
        divePower = ConfigManager.getConfig().getDouble("Rage.FlyingElbow.DivePower");
        particleCount = ConfigManager.getConfig().getInt("Rage.FlyingElbow.ParticleCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (launched && !diving) {
            if (System.currentTimeMillis() - launchTime > 500) {
                diveDirection = player.getLocation().getDirection().normalize();
                diveDirection.setY(-0.5);
                diving = true;
            }
        }

        if (diving) {
            player.setVelocity(diveDirection.clone().multiply(divePower));
            spawnParticles(player.getLocation(), particleCount);

            if (player.isOnGround()) {
                for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                    if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                       DamageHandler.damageEntity((LivingEntity) entity, landDamage, this);
                    }
                }

                spawnParticles(player.getLocation(), particleCount * 2);
                bPlayer.addCooldown(this);
                remove();
            }
        }
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
        return "FlyingElbow";
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
        return "Launch into the air, then dive down with a powerful elbow strike.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
