package com.alex.cucumber.mixin;

import com.alex.cucumber.forge.event.TagsUpdatedEvent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.cucumber.crafting.TagMapper.onTagsUpdated;

@Mixin(net.minecraft.client.multiplayer.ClientPacketListener.class)
public abstract class ClientPacketListener {

    @Shadow @Final private Connection connection;

    @Shadow public abstract RegistryAccess registryAccess();

    @Inject(method = "handleUpdateTags", at = @At(value = "TAIL"))
    private void injectMethod(ClientboundUpdateTagsPacket clientboundUpdateTagsPacket, CallbackInfo ci) {
        var event = new TagsUpdatedEvent(this.registryAccess(), true, connection.isMemoryConnection());
        onTagsUpdated(event);
    }
}
