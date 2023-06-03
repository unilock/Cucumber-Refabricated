package com.alex.cucumber.forge.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

public class ComputeFovModifierEvent {
    private final Player player;
    private final float fovModifier;
    private float newFovModifier;

    @ApiStatus.Internal
    public ComputeFovModifierEvent(Player player, float fovModifier)
    {
        this.player = player;
        this.fovModifier = fovModifier;
        this.setNewFovModifier((float) Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get(), 1.0F, fovModifier));
    }

    /**
     * {@return the player affected by this event}
     */
    public Player getPlayerEntity()
    {
        return player;
    }

    /**
     * {@return the original field of vision (FOV) of the player, before any modifications or interpolation}
     */
    public float getFovModifier()
    {
        return fovModifier;
    }

    /**
     * {@return the current field of vision (FOV) of the player}
     */
    public float getNewFovModifier()
    {
        return newFovModifier;
    }

    /**
     * Sets the new field of vision (FOV) of the player.
     *
     * @param newFovModifier the new field of vision (FOV)
     */
    public void setNewFovModifier(float newFovModifier)
    {
        this.newFovModifier = newFovModifier;
    }
}
