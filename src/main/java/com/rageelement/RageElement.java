package com.rageelement;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.rageelement.Listener.AbilityListener;
import com.rageelement.abilities.*;
import com.rageelement.abilities.elements.RageAbility;
import com.rageelement.configuration.Config;
import com.rageelement.configuration.ConfigManager;
import com.rageelement.element.Elements;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class RageElement extends JavaPlugin {

    private static RageElement plugin;
    private static RageAbility rageElement;
    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;
       new ConfigManager();
       config = new Config("config.yml");
       config.newConfig("config.yml");



        registerRageElement();
        registerAbilities();
        Bukkit.getServer().getPluginManager().registerEvents(new AbilityListener(), this);

        getLogger().info("RageElement has been enabled!");
        getLogger().info("An element by Hihelloy for KorraMC");
    }

    @Override
    public void onDisable() {
        getLogger().info("RageElement has been disabled!");
    }

    private void registerRageElement() {
        new Elements();
        getLogger().info("Registered Rage element!");
    }

    private void registerAbilities() {
        CoreAbility.registerPluginAbilities(this, "com.rageelement.abilities");
        getLogger().info("Registered Rage abilities!");
    }

    public static RageElement getPlugin() {
        return plugin;
    }

    public static RageAbility getRageElement() {
        return rageElement;
    }
}
