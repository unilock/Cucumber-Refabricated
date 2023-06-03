package com.alex.cucumber.forge.client.event;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

public class TextureStitchEvent {
    private final TextureAtlas atlas;

    @ApiStatus.Internal
    public TextureStitchEvent(TextureAtlas atlas)
    {
        this.atlas = atlas;
    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    public static class Pre extends TextureStitchEvent
    {
        private final Set<ResourceLocation> sprites;

        @ApiStatus.Internal
        public Pre(TextureAtlas map, Set<ResourceLocation> sprites)
        {
            super(map);
            this.sprites = sprites;
        }

        public boolean addSprite(ResourceLocation sprite)
        {
            return this.sprites.add(sprite);
        }
    }
}
