package dev.realmlegends.item;

import dev.realmlegends.Realm;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum RealmToolMaterial implements ToolMaterial {
    SKY(Realm.SKY, 2800, 11.0f, 5.0f),
    EMBER(Realm.EMBER, 3500, 13.0f, 6.0f),
    VOID(Realm.VOID, 4500, 15.0f, 7.0f);

    private final Realm realm;
    private final int durability;
    private final float speed, damage;

    RealmToolMaterial(Realm realm, int durability, float speed, float damage) {
        this.realm = realm; this.durability = durability; this.speed = speed; this.damage = damage;
    }

    public static RealmToolMaterial of(Realm r) { return values()[r.ordinal()]; }

    @Override public int getDurability() { return durability; }
    @Override public float getMiningSpeedMultiplier() { return speed; }
    @Override public float getAttackDamage() { return damage; }
    @Override public int getMiningLevel() { return 4; }
    @Override public int getEnchantability() { return 22; }
    @Override public Ingredient getRepairIngredient() {
        return switch (realm) {
            case SKY -> Ingredient.ofItems(Items.DIAMOND);
            case EMBER -> Ingredient.ofItems(Items.NETHERITE_INGOT);
            case VOID -> Ingredient.ofItems(Items.NETHER_STAR);
        };
    }
}
