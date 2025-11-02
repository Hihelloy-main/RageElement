package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.RageElement;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;



public class WildSwipe extends RageAbility implements AddonAbility {

    private long cooldown;
    private double damage;
    private int range;
    private int swipeCount;

    private int currentSwipe;
    private int swipeProgress;
    private boolean fromLeft;

    public WildSwipe(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        currentSwipe = 0;
        swipeProgress = 0;
        fromLeft = true;

        bPlayer.addCooldown(this);
        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.WildSwipe.Cooldown");
        damage = ConfigManager.getConfig().getDouble("Rage.WildSwipe.Damage");
        range = ConfigManager.getConfig().getInt("Rage.WildSwipe.Range");
        swipeCount = ConfigManager.getConfig().getInt("Rage.WildSwipe.SwipeCount");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        if (currentSwipe >= swipeCount) {
            remove();
            return;
        }

        swipeProgress++;

        if (swipeProgress > 5) {
            currentSwipe++;
            swipeProgress = 0;
            fromLeft = !fromLeft;
            return;
        }

        Vector direction = player.getLocation().getDirection().normalize();
        Vector perpendicular = direction.getCrossProduct(new Vector(0, 1, 0)).normalize();

        double swipeDistance = range + (currentSwipe * 0.5);
        double sideOffset = fromLeft ? -1 : 1;

        Location swipeLoc = player.getEyeLocation().clone()
            .add(direction.multiply(swipeDistance))
            .add(perpendicular.multiply(sideOffset * swipeProgress * 0.3));

        ParticleEffect.SWEEP_ATTACK.display(swipeLoc, 3, 0.2, 0.2, 0.2, 0);

        for (Entity entity : swipeLoc.getWorld().getNearbyEntities(swipeLoc, 1.5, 1.5, 1.5)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
              DamageHandler.damageEntity((LivingEntity) entity, damage, this);
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
        return "WildSwipe";
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
        return "Throw three wild swipes in succession, each slightly further away.";
    }

    @Override
    public String getInstructions() {
        return "Left click to activate.";
    }
}
