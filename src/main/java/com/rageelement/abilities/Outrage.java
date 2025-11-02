package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import static com.projectkorra.projectkorra.util.DamageHandler.damageEntity;

public class Outrage extends RageAbility implements AddonAbility {

    private long cooldown;
    private double damage;
    private int distance;
    private double explosionPower;
    private double knockbackPower;

    private Location explosionLocation;
    private Vector direction;
    private int travelDistance;
    private boolean exploded;
    private int particleCount;

    public Outrage(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        direction = new Vector(
            (Math.random() - 0.5) * 2,
            (Math.random() - 0.5) * 2,
            (Math.random() - 0.5) * 2
        ).normalize();

        explosionLocation = player.getLocation().clone();
        travelDistance = 0;
        exploded = false;

        player.setVelocity(direction.clone().multiply(-1).multiply(knockbackPower));

        bPlayer.addCooldown(this);
        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.Outrage.Cooldown");
        damage = ConfigManager.getConfig().getDouble("Rage.Outrage.Damage");
        distance = ConfigManager.getConfig().getInt("Rage.Outrage.Distance");
        explosionPower = ConfigManager.getConfig().getDouble("Rage.Outrage.ExplosionPower");
        knockbackPower = ConfigManager.getConfig().getDouble("Rage.Outrage.KnockbackPower");
        particleCount = ConfigManager.getConfig().getInt("Rage.Outrage.ParticleCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (travelDistance >= distance || exploded) {
            remove();
            return;
        }

        explosionLocation.add(direction);
        travelDistance++;

        spawnParticles(explosionLocation, particleCount);

        for (Entity entity : explosionLocation.getWorld().getNearbyEntities(explosionLocation, 2, 2, 2)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                LivingEntity target = (LivingEntity) entity;
                damageEntity(target, damage, this);
                target.setVelocity(direction.clone().multiply(knockbackPower));
                exploded = true;
                break;
            }
        }
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return explosionLocation;
    }

    @Override
    public String getName() {
        return "Outrage";
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
        return "Creates an explosion that flies out in a random direction.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
