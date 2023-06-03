package com.alex.cucumber.forge.client.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class PlayerEvent {
    private final Player player;

    public PlayerEvent(Player player)
    {
        //super(player);
        this.player = player;
    }

    public Player getEntity()
    {
        return player;
    }
    
    public static class HarvestCheck extends PlayerEvent
    {
        private final BlockState state;
        private boolean success;

        public HarvestCheck(Player player, BlockState state, boolean success)
        {
            super(player);
            this.state = state;
            this.success = success;
        }

        public BlockState getTargetBlock() { return this.state; }
        public boolean canHarvest() { return this.success; }
        public void setCanHarvest(boolean success){ this.success = success; }
    }
}
