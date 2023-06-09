package com.alex.cucumber.forge.client.event;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
import java.util.Set;

public class ModelEvent {
    public static class BakingCompleted extends ModelEvent
    {
        private final ModelManager modelManager;
        private final Map<ResourceLocation, BakedModel> models;
        private final ModelBakery modelBakery;

        @ApiStatus.Internal
        public BakingCompleted(ModelManager modelManager, Map<ResourceLocation, BakedModel> models, ModelBakery modelBakery)
        {
            this.modelManager = modelManager;
            this.models = models;
            this.modelBakery = modelBakery;
        }

        /**
         * @return the model manager
         */
        public ModelManager getModelManager()
        {
            return modelManager;
        }

        /**
         * @return the modifiable registry map of models and their model names
         */
        public Map<ResourceLocation, BakedModel> getModels()
        {
            return models;
        }

        /**
         * @return the model loader
         */
        public ModelBakery getModelBakery()
        {
            return modelBakery;
        }
    }

    public static class RegisterAdditional extends ModelEvent
    {
        private final Set<ResourceLocation> models;

        @ApiStatus.Internal
        public RegisterAdditional(Set<ResourceLocation> models)
        {
            this.models = models;
        }

        /**
         * Registers a model to be loaded, along with its dependencies.
         */
        public void register(ResourceLocation model)
        {
            models.add(model);
        }
    }
}
