package com.alex.cucumber.client.screen.widget;

import com.alex.cucumber.Cucumber;
import com.alex.cucumber.util.Formatting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import team.reborn.energy.api.EnergyStorage;

public class EnergyBarWidget extends AbstractWidget {
    private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation(Cucumber.MOD_ID, "textures/gui/widgets.png");
    private final EnergyStorage energy;

    public EnergyBarWidget(int x, int y, EnergyStorage energy) {
        super(x, y, 14, 78, Component.literal("Energy Bar"));
        this.energy = energy;
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        int offset = this.getEnergyBarOffset();

        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height);
        gfx.blit(WIDGETS_TEXTURE, this.getX(), this.getY() + this.height - offset, 14, this.height - offset, this.width,  offset + 1);

        if (mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            var font = Minecraft.getInstance().font;
            var text = Component.literal(Formatting.number(this.energy.getAmount()).getString() + " / " + Formatting.energy(this.energy.getCapacity()).getString());

            gfx.renderTooltip(font, text, mouseX, mouseY);
        }
    }

    @Override
    public void renderWidget(GuiGraphics matrix, int mouseX, int mouseY, float partialTicks) { }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narration) { }

    private int getEnergyBarOffset() {
        long i = this.energy.getAmount();
        long j = this.energy.getCapacity();
        return (int)(j != 0 && i != 0 ? i * (long)this.height / (long)j : 0L);
    }
}
