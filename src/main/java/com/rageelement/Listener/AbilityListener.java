package com.rageelement.Listener;


import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import com.projectkorra.projectkorra.event.PlayerBindChangeEvent;
import com.rageelement.RageElement;
import com.rageelement.abilities.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null) {
            return;
        }

        Action action = event.getAction();
        String abilityName = bPlayer.getBoundAbilityName();

        if (abilityName == null) {
            return;
        }

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            handleLeftClick(player, bPlayer, abilityName);
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }

        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (bPlayer == null) {
            return;
        }

        String abilityName = bPlayer.getBoundAbilityName();

        if (abilityName == null) {
            return;
        }

        if (abilityName.equalsIgnoreCase("Flex")) {
            new Flex(player);
        } else if (abilityName.equalsIgnoreCase("RKO")) {
            new RKO(player);
        }
    }

    private void handleLeftClick(Player player, BendingPlayer bPlayer, String abilityName) {
        switch (abilityName) {
            case "Outrage":
                new Outrage(player);
                break;
            case "BerserkerTackle":
                new BerserkerTackle(player);
                break;
            case "FuriousRoar":
                new FuriousRoar(player);
                break;
            case "BoxersGambit":
                new BoxersGambit(player);
                break;
            case "WildSwipe":
                new WildSwipe(player);
                break;
            case "DespiseTheWeak":
                new DespiseTheWeak(player);
                break;
            case "ChaoticStomp":
                new ChaoticStomp(player);
                break;
            case "FlyingElbow":
                new FlyingElbow(player);
                break;
        }
    }



    @EventHandler
    public void onBendingReload(BendingReloadEvent event) {
        RageElement.getPlugin().reloadConfig();
        event.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[" + "&6RageElement" + "&a]" + "&7 Config reloaded!"));
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        // Only proceed if the damaged entity is a player
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);
        if (bendingPlayer == null) {
            return;
        }

        EntityDamageEvent.DamageCause cause = event.getCause();
        RageFall rageFall = new RageFall(player);

        if (cause == EntityDamageEvent.DamageCause.FALL && bendingPlayer.canBendPassive(rageFall)) {
            event.setCancelled(true);
        }
    }



    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        RageAgility rageAgility = new RageAgility(player);
        BendingPlayer bendingPlayer = BendingPlayer.getBendingPlayer(player);
        if (bendingPlayer.canBendPassive(rageAgility)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 13, 1, true, false));
        }
    }

    @EventHandler
    public void onPlayerUseFuryHook(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Only respond to right-click air or block
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_AIR) return;

        // Check if player can bend FuryHook
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        FuryHook furyHook = new FuryHook(player);
        if (bPlayer == null) return;
        if (!bPlayer.canBend(furyHook)) return;
        if (!bPlayer.getBoundAbilityName().equalsIgnoreCase("FuryHook")) return;

        // Launch FuryHook ability
        new FuryHook(player);
    }




}
