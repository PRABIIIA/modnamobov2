package dev.realmlegends;

import dev.realmlegends.item.RealmArmorItem;
import dev.realmlegends.item.RealmToolMaterial;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.*;

/** Бонусы легендарной брони, комплектов и инструментов. */
public final class RealmEffects {
    public record Eff(StatusEffect effect, int amp) { }

    // индекс: шлем, нагрудник, поножи, ботинки
    private static final Map<Realm, Eff[][]> ARMOR = new EnumMap<>(Realm.class);
    private static final Map<Realm, Eff[]> SET_BONUS = new EnumMap<>(Realm.class);
    private static final Set<UUID> FLYERS = new HashSet<>();

    private RealmEffects() { }

    private static Eff e(StatusEffect s, int a) { return new Eff(s, a); }

    public static void register() {
        ARMOR.put(Realm.SKY, new Eff[][]{
                {e(StatusEffects.NIGHT_VISION, 0), e(StatusEffects.WATER_BREATHING, 0)},
                {e(StatusEffects.HEALTH_BOOST, 0)},
                {e(StatusEffects.JUMP_BOOST, 1)},
                {e(StatusEffects.SLOW_FALLING, 0), e(StatusEffects.SPEED, 0)}});
        SET_BONUS.put(Realm.SKY, new Eff[]{e(StatusEffects.REGENERATION, 0), e(StatusEffects.RESISTANCE, 0)});

        ARMOR.put(Realm.EMBER, new Eff[][]{
                {e(StatusEffects.NIGHT_VISION, 0), e(StatusEffects.FIRE_RESISTANCE, 0)},
                {e(StatusEffects.STRENGTH, 0)},
                {e(StatusEffects.FIRE_RESISTANCE, 0), e(StatusEffects.SPEED, 0)},
                {e(StatusEffects.SPEED, 1)}});
        SET_BONUS.put(Realm.EMBER, new Eff[]{e(StatusEffects.RESISTANCE, 0), e(StatusEffects.REGENERATION, 0)});

        ARMOR.put(Realm.VOID, new Eff[][]{
                {e(StatusEffects.NIGHT_VISION, 0), e(StatusEffects.WATER_BREATHING, 0)},
                {e(StatusEffects.RESISTANCE, 0)},
                {e(StatusEffects.HASTE, 1), e(StatusEffects.JUMP_BOOST, 1)},
                {e(StatusEffects.SLOW_FALLING, 0), e(StatusEffects.SPEED, 1)}});
        SET_BONUS.put(Realm.VOID, new Eff[]{e(StatusEffects.REGENERATION, 1), e(StatusEffects.HEALTH_BOOST, 1)});

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 10 != 0) return;
            for (ServerPlayerEntity p : server.getPlayerManager().getPlayerList()) tickPlayer(p);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity p = handler.player;
            if (!p.isCreative() && !p.isSpectator() && p.getAbilities().allowFlying) FLYERS.add(p.getUuid());
        });
    }

    private static void give(ServerPlayerEntity p, Eff ef, int duration) {
        p.addStatusEffect(new StatusEffectInstance(ef.effect(), duration, ef.amp(), true, false, true));
    }

    private static void tickPlayer(ServerPlayerEntity p) {
        int[] counts = new int[Realm.values().length];
        boolean flight = false;
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        for (EquipmentSlot s : slots) {
            ItemStack st = p.getEquippedStack(s);
            if (st.getItem() instanceof RealmArmorItem a) {
                counts[a.realm.ordinal()]++;
                for (Eff ef : ARMOR.get(a.realm)[a.getType().ordinal()]) give(p, ef, 220);
                if (a.getType() == ArmorItem.Type.CHESTPLATE) flight = true;
            }
        }
        for (Realm r : Realm.values()) {
            if (counts[r.ordinal()] == 4) for (Eff ef : SET_BONUS.get(r)) give(p, ef, 220);
        }

        Item main = p.getMainHandStack().getItem();
        if (main instanceof ToolItem ti && !(main instanceof SwordItem) && ti.getMaterial() instanceof RealmToolMaterial) {
            give(p, e(StatusEffects.HASTE, 1), 60);
        }

        // Полёт от нагрудника
        if (!p.isCreative() && !p.isSpectator()) {
            if (flight) {
                if (!p.getAbilities().allowFlying) {
                    p.getAbilities().allowFlying = true;
                    p.sendAbilitiesUpdate();
                }
                FLYERS.add(p.getUuid());
            } else if (FLYERS.remove(p.getUuid())) {
                p.getAbilities().allowFlying = false;
                p.getAbilities().flying = false;
                p.sendAbilitiesUpdate();
            }
        }
    }

    // ---------- Оружие ----------
    public static void swordHit(Realm r, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient) return;
        switch (r) {
            case SKY -> {
                attacker.heal(2f);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100));
                if (attacker.getRandom().nextFloat() < 0.15f) {
                    target.damage(attacker.getDamageSources().magic(), 6f); // «небесная кара»
                }
            }
            case EMBER -> {
                target.setOnFireFor(8);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 0));
            }
            case VOID -> {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 40, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 60, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1));
            }
        }
    }

    public static void axeCleave(LivingEntity target, LivingEntity attacker) {
        World w = attacker.getWorld();
        if (w.isClient) return;
        DamageSource src = attacker instanceof PlayerEntity pl
                ? attacker.getDamageSources().playerAttack(pl)
                : attacker.getDamageSources().mobAttack(attacker);
        for (LivingEntity x : w.getEntitiesByClass(LivingEntity.class, target.getBoundingBox().expand(2.5, 0.5, 2.5),
                o -> o != target && o != attacker && !(o instanceof ArmorStandEntity) && !attacker.isTeammate(o))) {
            x.damage(src, 5f);
            x.takeKnockback(0.4, attacker.getX() - x.getX(), attacker.getZ() - x.getZ());
        }
    }
}
