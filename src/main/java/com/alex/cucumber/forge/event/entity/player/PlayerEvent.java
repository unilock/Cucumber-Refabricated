package com.alex.cucumber.forge.event.entity.player;

import net.minecraft.world.entity.player.Player;

public class PlayerEvent {
    private final Player player;

    public PlayerEvent(Player player)
    {
        this.player = player;
    }

    public Player getEntity()
    {
        return player;
    }
}
