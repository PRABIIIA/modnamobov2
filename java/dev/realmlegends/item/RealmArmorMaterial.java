package dev.realmlegends.item;

import dev.realmlegends.Realm;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum RealmArmorMaterial implements ArmorMaterial {
    SKY(Realm.SKY, 55, new int[]{4, 9, 7, 4}, 4.0f, 0.10f),
    EMBER(Realm.EMBER, 60, new int[]{4, 9, 7, 4}, 5.0f, 0.15f),
    VOID(Realm.VOID, 70, new int[]{5, 10, 8, 5}, 6.0f, 0.20f);

    private static final int[] BASE = {11, 16, 15, 13}; // шлем, нагрудник, поножи, ботинки

    private final Realm realm;
    private final int durabilityMul;
    private final int[] protection; // шлем, нагрудник, поножи, ботинки
    private final float toughness, knockback;

    RealmArmorMaterial(Realm realm, int durabilityMul, int[] protection, float toughness, float knockback) {
        this.realm = realm; this.durabilityMul = durabilityMul; this.protection = protection;
        this.toughness = toughness; this.knockback = knockback;
    }

    public static RealmArmorMaterial of(Realm r) { return values()[r.ordinal()]; }

    @Override public int getDurability(ArmorItem.Type type) { return BASE[type.ordinal()] * durabilityMul; }
    @Override public int getProtection(ArmorItem.Type type) { return protection[type.ordinal()]; }
    @Override public int getEnchantability() { return 25; }
    @Override public SoundEvent getEquipSound() { return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE; }
    @Override public Ingredient getRepairIngredient() {
        return switch (realm) {
            case SKY -> Ingredient.ofItems(Items.DIAMOND);
            case EMBER -> Ingredient.ofItems(Items.NETHERITE_INGOT);
            case VOID -> Ingredient.ofItems(Items.NETHER_STAR);
        };
    }
    @Override public String getName() { return "realmlegends_" + realm.key; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockback; }
}
