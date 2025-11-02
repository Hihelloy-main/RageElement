package com.rageelement.configuration;

import com.rageelement.RageElement;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public ConfigManager() {
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = RageElement.getPlugin().getConfig();

        config.addDefault("Rage.Berserk.HealthThreshold", 0.5);
        config.addDefault("Rage.Berserk.SpeedMultiplier", 2.0);
        config.addDefault("Rage.Berserk.DamageMultiplier", 1.5);
        config.addDefault("Rage.Berserk.JumpMultiplier", 0.5);
        config.addDefault("Rage.Berserk.ParticleCount", 20);
        config.addDefault("Rage.Berserk.Cooldown", 0);

        config.addDefault("Rage.BerserkerTackle.Cooldown", 12000);
        config.addDefault("Rage.BerserkerTackle.Distance", 20);
        config.addDefault("Rage.BerserkerTackle.Speed", 2.0);
        config.addDefault("Rage.BerserkerTackle.HitDamage", 4.0);
        config.addDefault("Rage.BerserkerTackle.MissDamage", 2.0);
        config.addDefault("Rage.BerserkerTackle.StunDuration", 1000);
        config.addDefault("Rage.BerserkerTackle.ParticleCount", 20);

       config.addDefault("Rage.BoxersGambit.Cooldown", 20000);
       config.addDefault("Rage.BoxersGambit.Range", 6);
       config.addDefault("Rage.BoxersGambit.SpeedDuration", 400);
       config.addDefault("Rage.BoxersGambit.SpeedAmplifier", 2);
       config.addDefault("Rage.BoxersGambit.SlownessDuration", 200);
       config.addDefault("Rage.BoxersGambit.SlownessAmplifier", 1);

       config.addDefault("Rage.ChaoticStomp.Cooldown", 25000);
       config.addDefault("Rage.ChaoticStomp.Radius", 15);
       config.addDefault("Rage.ChaoticStomp.PillarHeight", 3);
       config.addDefault("Rage.ChaoticStomp.PillarCount", 20);

        config.addDefault("Rage.DespiseTheWeak.Cooldown", 3000);
        config.addDefault("Rage.DespiseTheWeak.Damage", 3.0);
        config.addDefault("Rage.DespiseTheWeak.BacklashDamage", 4.0);
        config.addDefault("Rage.DespiseTheWeak.HealthThreshold", 6.0);
        config.addDefault("Rage.DespiseTheWeak.ParticleCount", 20);

        config.addDefault("Rage.Flex.Cooldown", 30000);
        config.addDefault("Rage.Flex.Duration", 2000);
        config.addDefault("Rage.Flex.StrengthDuration", 200);
        config.addDefault("Rage.Flex.StrengthAmplifier", 2);

        config.addDefault("Rage.FlyingElbow.Cooldown", 18000);
        config.addDefault("Rage.FlyingElbow.LaunchDamage", 1.0);
        config.addDefault("Rage.FlyingElbow.LandDamage", 4.0);
        config.addDefault("Rage.FlyingElbow.LaunchPower", 1.5);
        config.addDefault("Rage.FlyingElbow.DivePower", 2.0);
        config.addDefault("Rage.FlyingElbow.ParticleCount", 20);

        config.addDefault("Rage.FuriousRoar.Cooldown", 15000);
        config.addDefault("Rage.FuriousRoar.Damage", 3.0);
        config.addDefault("Rage.FuriousRoar.Range", 15);
        config.addDefault("Rage.FuriousRoar.ExpansionSpeed", 0.5);

        config.addDefault("Rage.FuryHook.Cooldown", 10000);
        config.addDefault("Rage.FuryHook.Damage", 6.0);
        config.addDefault("Rage.FuryHook.Range", 10);
        config.addDefault("Rage.FuryHook.HomingStrength", 1);

        config.addDefault("Rage.Outrage.Cooldown", 8000);
        config.addDefault("Rage.Outrage.Damage", 5.0);
        config.addDefault("Rage.Outrage.Distance", 7);
        config.addDefault("Rage.Outrage.ExplosionPower", 2.0);
        config.addDefault("Rage.Outrage.KnockbackPower", 2.0);
        config.addDefault("Rage.Outrage.ParticleCount", 20);

        config.addDefault("Rage.WildSwipe.Cooldown", 6000);
        config.addDefault("Rage.WildSwipe.Damage", 1.0);
        config.addDefault("Rage.WildSwipe.Range", 5);
        config.addDefault("Rage.WildSwipe.SwipeCount", 3);

        config.addDefault("Rage.RKO.Cooldown", 15000);
        config.addDefault("Rage.RKO.ChannelTime", 2000);
        config.addDefault("Rage.RKO.Damage", 2.0);
        config.addDefault("Rage.RKO.LaunchHeight", 5.0);
        config.addDefault("Rage.RKO.Radius", 5.0);

        config.addDefault("Rage.Color", "#DC143C");


        config.options().copyDefaults(true);
        RageElement.getPlugin().saveConfig();
    }

    public static FileConfiguration getConfig() {
        return RageElement.getPlugin().getConfig();
    }
}
