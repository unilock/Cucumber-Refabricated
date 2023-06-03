package com.alex.cucumber.forge.client.event.world;

import com.alex.cucumber.forge.common.ForgeHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEvent {
    private final LevelAccessor world;
    private final BlockPos pos;
    private final BlockState state;
    public BlockEvent(LevelAccessor world, BlockPos pos, BlockState state)
    {
        this.pos = pos;
        this.world = world;
        this.state = state;
    }

    public LevelAccessor getLevel()
    {
        return world;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    public BlockState getState()
    {
        return state;
    }

    public static class BreakEvent extends BlockEvent
    {
        /** Reference to the Player who broke the block. If no player is available, use a EntityFakePlayerEntity */
        private final Player player;
        private int exp;

        public BreakEvent(Level level, BlockPos pos, BlockState state, Player player)
        {
            super(level, pos, state);
            this.player = player;

            if (state == null || !ForgeHooks.isCorrectToolForDrops(state, player)) // Handle empty block or player unable to break block scenario
            {
                this.exp = 0;
            }
            else
            {
                int fortuneLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
                int silkTouchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem());
                //TODO
                this.exp = /*state.getExpDrop(world, world.random, pos, fortuneLevel, silkTouchLevel)*/0;
            }
        }

        public Player getPlayerEntity()
        {
            return player;
        }

        /**
         * Get the experience dropped by the block after the event has processed
         *
         * @return The experience to drop or 0 if the event was canceled
         */
        public int getExpToDrop()
        {
            return /*this.isCanceled() ? 0 :*/ exp;
        }

        /**
         * Set the amount of experience dropped by the block after the event has processed
         *
         * @param exp 1 or higher to drop experience, else nothing will drop
         */
        public void setExpToDrop(int exp)
        {
            this.exp = exp;
        }
    }
}
