package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



public class FuriousRoar extends RageAbility implements AddonAbility {

    private long cooldown;
    private double damage;
    private int range;
    private double expansionSpeed;

    private Location center;
    private Vector direction;
    private double currentRadius;

    public FuriousRoar(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        center = player.getEyeLocation().clone();
        direction = player.getLocation().getDirection().normalize();
        currentRadius = 0;

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 2.0f, 0.8f);

        bPlayer.addCooldown(this);
        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.FuriousRoar.Cooldown");
        damage = ConfigManager.getConfig().getDouble("Rage.FuriousRoar.Damage");
        range = ConfigManager.getConfig().getInt("Rage.FuriousRoar.Range");
        expansionSpeed = ConfigManager.getConfig().getDouble("Rage.FuriousRoar.ExpansionSpeed");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (currentRadius >= range) {
            remove();
            return;
        }

        currentRadius += expansionSpeed;
        Location shockwaveCenter = center.clone().add(direction.clone().multiply(currentRadius));

        for (double angle = 0; angle < Math.PI * 2; angle += Math.PI / 8) {
            Vector perpendicular = direction.clone().getCrossProduct(new Vector(0, 1, 0)).normalize();
            Vector particleOffset = perpendicular.clone().multiply(Math.cos(angle) * currentRadius)
                .add(new Vector(0, 1, 0).multiply(Math.sin(angle) * currentRadius));
            Location particleLoc = center.clone().add(direction.clone().multiply(currentRadius * 0.5)).add(particleOffset);
            spawnParticles(particleLoc, 10, 0, 0, 0, 0);
        }

        for (Entity entity : shockwaveCenter.getWorld().getNearbyEntities(shockwaveCenter, currentRadius, currentRadius, currentRadius)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
                if (entity.getLocation().distance(shockwaveCenter) <= currentRadius + 1) {
                   DamageHandler.damageEntity((LivingEntity) entity, damage, this);
                }
            }
        }
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public Location getLocation() {
        return center;
    }

    @Override
    public String getName() {
        return "FuriousRoar";
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
        return "Let out a powerful roar that creates an expanding shockwave.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
