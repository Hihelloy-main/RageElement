package com.rageelement.abilities.elements;


import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.rageelement.element.Elements;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class RageAbility extends ElementalAbility {

    public RageAbility(Player player) {
        super(player);
    }

    @Override
    public Element getElement() {
        return Elements.RAGE;
    }


    public void spawnParticles(Location loc, int count) {
        ParticleEffect.CRIMSON_SPORE.display(loc, count + 20);
    }

    public void spawnParticles(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra) {
        ParticleEffect.CRIMSON_SPORE.display(loc, amount + 20, offsetX, offsetY, offsetZ, extra);
    }

}
