package dev.realmlegends.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/** Эффект, который моб накладывает при успешной атаке. */
public enum Ability {
    NONE, FIRE, POISON, WITHER, SLOW, WEAK, BLIND, LEVITATE, LIFESTEAL, KNOCKBACK, FROST, TELEPORT, HUNGER;

    public void apply(CustomMob self, LivingEntity t) {
        switch (this) {
            case FIRE -> t.setOnFireFor(6);
            case POISON -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), self);
            case WITHER -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 0), self);
            case SLOW -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1), self);
            case WEAK -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 140, 0), self);
            case BLIND -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 80, 0), self);
            case LEVITATE -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 50, 0), self);
            case HUNGER -> t.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 1), self);
            case LIFESTEAL -> self.heal(Math.max(2f, (float) self.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 0.3f));
            case KNOCKBACK -> t.takeKnockback(1.2, self.getX() - t.getX(), self.getZ() - t.getZ());
            case FROST -> {
                t.setFrozenTicks(t.getMinFreezeDamageTicks() + 40);
                t.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 0), self);
            }
            default -> { }
        }
    }
}
