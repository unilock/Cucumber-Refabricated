package com.alex.cucumber.forge.event.entity.player;

import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;

public class PlayerXpEvent extends PlayerEvent {
    public PlayerXpEvent(Player player)
    {
        super(player);
    }

    public static class PickupXp extends PlayerXpEvent
    {

        private final ExperienceOrb orb;

        public PickupXp(Player player, ExperienceOrb orb)
        {
            super(player);
            this.orb = orb;
        }

        public ExperienceOrb getOrb()
        {
            return orb;
        }
    }
}
