package com.rageelement.element;


import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.Element.ElementType;
import com.projectkorra.projectkorra.ProjectKorra;
import com.rageelement.configuration.ConfigManager;
import net.md_5.bungee.api.ChatColor;


public class Elements {

    public static final Element RAGE;

    public Elements() {
    }

    static {
        RAGE = new Element("Rage", ElementType.BENDING, ProjectKorra.plugin) {
            @Override
            public ChatColor getColor() {
                return ChatColor.of(ConfigManager.getConfig().getString("Rage.Color"));
            }
        };
    }
}
