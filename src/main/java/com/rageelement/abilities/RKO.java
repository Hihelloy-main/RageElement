package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RKO extends RageAbility implements AddonAbility {

    private long cooldown;
    private long channelTime;
    private double damage;
    private double launchHeight;
    private double radius;

    private long startTime;
    private double startHealth;
    private boolean channeling;

    public RKO(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            remove();
            return;
        }

        setFields();

        startTime = System.currentTimeMillis();
        startHealth = player.getHealth();
        channeling = true;

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.RKO.Cooldown");
        channelTime = ConfigManager.getConfig().getLong("Rage.RKO.ChannelTime");
        damage = ConfigManager.getConfig().getDouble("Rage.RKO.Damage");
        launchHeight = ConfigManager.getConfig().getDouble("Rage.RKO.LaunchHeight");
        radius = ConfigManager.getConfig().getDouble("Rage.RKO.Radius");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (!player.isSneaking()) {
            bPlayer.addCooldown(this);
            remove();
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;
        if (!channeling) {
            remove();
            return;
        }


        if (player.getHealth() < startHealth) {
            bPlayer.addCooldown(this);
            remove();
            return;
        }

        Location center = player.getLocation();


        int particleCount = 25;
        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1.8F);
        for (int i = 0; i < particleCount; i++) {
            double angle = 2 * Math.PI * i / particleCount;
            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);
            Location particleLoc = center.clone().add(x, 0.2, z);
            center.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, 0, 0, 0, 0, dust);
        }


        if (elapsed >= channelTime) {
            for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
                if (entity instanceof LivingEntity target && !target.getUniqueId().equals(player.getUniqueId())) {

                    DamageHandler.damageEntity(target, damage, this);


                    Location targetLoc = target.getLocation().clone();
                    target.teleport(targetLoc.add(0, 1.5, 0));


                    Vector velocity = target.getVelocity();
                    velocity.setY(launchHeight);
                    target.setVelocity(velocity);


                    target.getWorld().spawnParticle(Particle.EXPLOSION, target.getLocation().add(0, 1.9, 0), 2, 0.2, 0.2, 0.2, 0);
                }
            }


            center.getWorld().spawnParticle(Particle.LAVA, center, 30, radius, 0.5, radius, 0.05);

            bPlayer.addCooldown(this);
            remove();
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
        return "RKO";
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public boolean isSneakAbility() {
        return true;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return true;
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
        return "Channel for a short time without taking damage to launch nearby enemies into the air and deal damage.";
    }

    @Override
    public String getInstructions() {
        return "Hold sneak for the channel duration without taking damage.";
    }
}
