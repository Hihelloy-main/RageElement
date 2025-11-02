package com.rageelement.abilities;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.RageElement;
import com.rageelement.abilities.elements.RageAbility;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Flex extends RageAbility implements AddonAbility {

    private long cooldown;
    private long duration;
    private int strengthDuration;
    private int strengthAmplifier;

    private long startTime;
    private double startHealth;
    private boolean flexing;

    public Flex(Player player) {
        super(player);

        if (!bPlayer.canBend(this)) {
            return;
        }

        setFields();

        startTime = System.currentTimeMillis();
        startHealth = player.getHealth();
        flexing = true;

        start();
    }

    private void setFields() {
        cooldown = ConfigManager.getConfig().getLong("Rage.Flex.Cooldown");
        duration = ConfigManager.getConfig().getLong("Rage.Flex.Duration");
        strengthDuration = ConfigManager.getConfig().getInt("Rage.Flex.StrengthDuration");
        strengthAmplifier = ConfigManager.getConfig().getInt("Rage.Flex.StrengthAmplifier");
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
            remove();
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;

        if (flexing) {
            if (player.getHealth() < startHealth) {
                bPlayer.addCooldown(this);
                remove();
                return;
            }

            if (elapsed >= duration) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, strengthDuration, strengthAmplifier));
                ParticleEffect.HEART.display(player.getLocation().add(0, 2, 0), 10, 0.5, 0.5, 0.5, 0);
                bPlayer.addCooldown(this);
                remove();
                return;
            }

            ParticleEffect.ANGRY_VILLAGER.display(player.getLocation().add(0, 1, 0), 2, 0.3, 0.3, 0.3, 0);
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
        return "Flex";
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
        return "Flex for 2 seconds without taking damage to gain Strength.";
    }

    @Override
    public String getInstructions() {
        return "Shift to activate and hold still.";
    }
}
