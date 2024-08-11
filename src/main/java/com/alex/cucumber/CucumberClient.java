package com.alex.cucumber;

import com.alex.cucumber.forge.client.ForgeHooksClient;
import io.github.fabricators_of_create.porting_lib.event.client.FieldOfViewEvents;
import io.github.fabricators_of_create.porting_lib.event.common.RecipesUpdatedCallback;
import net.fabricmc.api.ClientModInitializer;

public class CucumberClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FieldOfViewEvents.MODIFY.register(ForgeHooksClient::getFieldOfViewModifier);
        RecipesUpdatedCallback.EVENT.register(ForgeHooksClient::onRecipesUpdated);
    }
}
