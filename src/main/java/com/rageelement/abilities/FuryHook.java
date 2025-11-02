package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.ConfigManager;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class FuryHook extends RageAbility implements AddonAbility {

    private long cooldown;
    private double damage;
    private int range;
    private double homingStrength;

    private Location currentLoc;
    private LivingEntity target;

    public FuryHook(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            remove();
            return;
        }

        setFields();

        currentLoc = player.getEyeLocation();
        target = findNearestTarget();

        if (target != null) {
            start();
        } else {
            bPlayer.addCooldown(this);
            remove();
        }
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.FuryHook.Cooldown");
        damage = ConfigManager.getConfig().getDouble("Rage.FuryHook.Damage");
        range = ConfigManager.getConfig().getInt("Rage.FuryHook.Range");
        homingStrength = ConfigManager.getConfig().getDouble("Rage.FuryHook.HomingStrength");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this) || player.isDead() || !player.isOnline()) {
            remove();
            return;
        }

        if (currentLoc.distance(player.getEyeLocation()) > range || target == null || target.isDead()) {
            bPlayer.addCooldown(this);
            remove();
            return;
        }


        Vector direction = target.getLocation().add(0, 1, 0).toVector().subtract(currentLoc.toVector()).normalize();
        currentLoc.add(direction.multiply(2.0));

        Location start = player.getEyeLocation();
        Vector step = currentLoc.toVector().subtract(start.toVector()).normalize().multiply(0.5);
        Location temp = start.clone();

        while (temp.distance(currentLoc) > 0.5) {
            player.getWorld().spawnParticle(Particle.END_ROD, temp, 2, 0.1, 0.1, 0.1, 0.05);
            player.getWorld().spawnParticle(Particle.CRIT, temp, 2, 0.1, 0.1, 0.1, 0.05);
            temp.add(step);
        }

        player.getWorld().playSound(currentLoc, Sound.ENTITY_BLAZE_SHOOT, 1.0f, 2.0f);


        for (Entity entity : currentLoc.getWorld().getNearbyEntities(currentLoc, 1.3, 1.3, 1.3)) {
            if (entity instanceof LivingEntity le && !le.equals(player)) {
                DamageHandler.damageEntity(le, damage, this);

                le.getWorld().spawnParticle(Particle.EXPLOSION, le.getLocation(), 4, 0.2, 0.2, 0.2, 0.1);
                le.getWorld().playSound(le.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.5f);

                bPlayer.addCooldown(this);
                remove();
                return;
            }
        }
    }

    private LivingEntity findNearestTarget() {
        Location eye = player.getEyeLocation();
        double closest = range;
        LivingEntity nearest = null;
        for (Entity e : eye.getWorld().getNearbyEntities(eye, range, range, range)) {
            if (e instanceof LivingEntity le && !le.equals(player)) {
                double dist = e.getLocation().distance(eye);
                if (dist < closest && player.hasLineOfSight(e)) {
                    closest = dist;
                    nearest = le;
                }
            }
        }
        return nearest;
    }

    @Override
    public long getCooldown() { return cooldown; }

    @Override
    public Location getLocation() { return currentLoc != null ? currentLoc : player.getLocation(); }

    @Override
    public String getName() { return "FuryHook"; }

    @Override
    public boolean isHarmlessAbility() { return false; }

    @Override
    public boolean isIgniteAbility() { return false; }

    @Override
    public boolean isExplosiveAbility() { return false; }

    @Override
    public boolean isSneakAbility() { return false; }

    @Override
    public String getAuthor() { return "Hihelloy"; }

    @Override
    public String getVersion() { return "1.0.0"; }

    @Override
    public void load() { }

    @Override
    public void stop() { }

    @Override
    public String getDescription() {
        return "Instantly fires a homing energy beam that damages the first enemy it hits.";
    }

    @Override
    public String getInstructions() {
        return "Left click to fire a homing energy beam.";
    }
}
