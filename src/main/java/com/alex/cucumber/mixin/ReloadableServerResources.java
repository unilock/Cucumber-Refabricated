package com.alex.cucumber.mixin;

import com.alex.cucumber.forge.event.TagsUpdatedEvent;
import net.minecraft.core.RegistryAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.alex.cucumber.crafting.TagMapper.onTagsUpdated;

@Mixin(net.minecraft.server.ReloadableServerResources.class)
public class ReloadableServerResources {
    @Inject(method = "updateRegistryTags(Lnet/minecraft/core/RegistryAccess;)V", at = @At(value = "TAIL"))
    private void injectMethod(RegistryAccess registryAccess, CallbackInfo ci) {
        var event = new TagsUpdatedEvent(registryAccess, false, false);
        onTagsUpdated(event);
    }
}
