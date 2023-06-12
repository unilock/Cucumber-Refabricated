package com.alex.cucumber.forge.common;

import com.alex.cucumber.forge.client.event.ForgeEventFactory;
import com.alex.cucumber.forge.client.event.world.BlockEvent;
import com.alex.cucumber.item.tool.BaseScytheItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import org.jetbrains.annotations.NotNull;

public class ForgeHooks {
    public static boolean onPlayerAttackTarget(Player player, Entity target)
    {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof BaseScytheItem scytheItem)
            return stack.isEmpty() || !scytheItem.onLeftClickEntity(stack, player, target);
        return true;
    }

    public static boolean isCorrectToolForDrops(@NotNull BlockState state, @NotNull Player player)
    {
        if (!state.requiresCorrectToolForDrops())
            return ForgeEventFactory.doPlayerHarvestCheck(player, state, true);

        return player.hasCorrectToolForDrops(state);
    }

    public static int onBlockBreakEvent(Level level, GameType type, ServerPlayer player, BlockPos pos) {
        // Logic from tryHarvestBlock for pre-canceling the event
        boolean preCancelEvent = false;
        ItemStack itemstack = player.getMainHandItem();
        if (!itemstack.isEmpty() && !itemstack.getItem().canAttackBlock(level.getBlockState(pos), level, pos, player))
        {
            preCancelEvent = true;
        }

        if (type.isBlockPlacingRestricted())
        {
            if (type == GameType.SPECTATOR)
                preCancelEvent = true;

            if (!player.mayBuild())
            {
                if (itemstack.isEmpty() || !itemstack.hasAdventureModeBreakTagForBlock(level.registryAccess().registryOrThrow(Registries.BLOCK), new BlockInWorld(level, pos, false)))
                    preCancelEvent = true;
            }
        }

        // Tell client the block is gone immediately then process events
        if (level.getBlockEntity(pos) == null)
        {
            player.connection.send(new ClientboundBlockUpdatePacket(pos, level.getFluidState(pos).createLegacyBlock()));
        }

        // Post the block break event
        BlockState state = level.getBlockState(pos);
        var event = new BlockEvent.BreakEvent(level, pos, state, player);
        //event.setCanceled(preCancelEvent);
        //MinecraftForge.EVENT_BUS.post(event);

        // Handle if the event is canceled
        /*if (event.isCanceled())
        {
            // Let the client know the block still exists
            player.networkHandler.send(new ClientboundBlockUpdatePacket(world, pos));

            // Update any tile entity data for this block
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null)
            {
                Packet<?> pkt = blockEntity.getUpdatePacket();
                if (pkt != null)
                {
                    player.networkHandler.send(pkt);
                }
            }
        }*/
        return /*event.isCanceled() ? -1 :*/ event.getExpToDrop();
    }
}
