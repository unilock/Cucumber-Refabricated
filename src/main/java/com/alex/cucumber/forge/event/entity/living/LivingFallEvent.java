package com.alex.cucumber.forge.event.entity.living;

import net.minecraft.world.entity.LivingEntity;

public class LivingFallEvent
{
    private final LivingEntity livingEntity;
    private float distance;
    private float damageMultiplier;
    public LivingFallEvent(LivingEntity entity, float distance, float damageMultiplier)
    {
        this.livingEntity = entity;
        this.setDistance(distance);
        this.setDamageMultiplier(damageMultiplier);
    }
    public LivingEntity getEntity()
    {
        return livingEntity;
    }
    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }
    public float getDamageMultiplier() { return damageMultiplier; }
    public void setDamageMultiplier(float damageMultiplier) { this.damageMultiplier = damageMultiplier; }
}
