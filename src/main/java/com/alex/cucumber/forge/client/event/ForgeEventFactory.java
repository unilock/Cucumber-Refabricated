package com.alex.cucumber.forge.client.event;

import com.alex.cucumber.forge.client.event.entity.player.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeEventFactory {
    public static boolean doPlayerHarvestCheck(Player player, BlockState state, boolean success)
    {
        PlayerEvent.HarvestCheck event = new PlayerEvent.HarvestCheck(player, state, success);
        //MinecraftForge.EVENT_BUS.post(event);
        return event.canHarvest();
    }
}
