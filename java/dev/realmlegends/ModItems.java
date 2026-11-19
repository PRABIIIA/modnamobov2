package dev.realmlegends;

import dev.realmlegends.entity.MobDef;
import dev.realmlegends.entity.MobDefs;
import dev.realmlegends.item.RealmArmorItem;
import dev.realmlegends.item.RealmArmorMaterial;
import dev.realmlegends.item.RealmToolMaterial;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ModItems {
    public static final List<Item> ALL = new ArrayList<>();
    public static final List<RealmArmorItem> ARMOR = new ArrayList<>();

    private ModItems() { }

    private static Item add(String name, Item item) {
        Item r = Registry.register(Registries.ITEM, RealmLegends.id(name), item);
        ALL.add(r);
        return r;
    }

    public static void register() {
        Supplier<Item.Settings> legendary = () -> new FabricItemSettings().fireproof().rarity(Rarity.EPIC);

        for (Realm realm : Realm.values()) {
            RealmArmorMaterial am = RealmArmorMaterial.of(realm);
            RealmToolMaterial tm = RealmToolMaterial.of(realm);
            final Realm r = realm;

            for (ArmorItem.Type t : ArmorItem.Type.values()) {
                RealmArmorItem a = new RealmArmorItem(realm, am, t, legendary.get());
                add(realm.key + "_" + t.getName(), a);
                ARMOR.add(a);
            }

            add(realm.key + "_sword", new SwordItem(tm, 3, -2.4f, legendary.get()) {
                @Override
                public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    RealmEffects.swordHit(r, target, attacker);
                    return super.postHit(stack, target, attacker);
                }
            });
            add(realm.key + "_axe", new AxeItem(tm, 5.0f, -3.0f, legendary.get()) {
                @Override
                public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    RealmEffects.axeCleave(target, attacker);
                    return super.postHit(stack, target, attacker);
                }
            });
            add(realm.key + "_pickaxe", new PickaxeItem(tm, 1, -2.8f, legendary.get()) { });
            add(realm.key + "_shovel", new ShovelItem(tm, 1.5f, -3.0f, legendary.get()));
            add(realm.key + "_hoe", new HoeItem(tm, -3, 0.0f, legendary.get()) { });
        }

        for (MobDef d : MobDefs.ALL) {
            int secondary = ((d.color >> 17) & 0x7F) << 16 | ((d.color >> 9) & 0x7F) << 8 | ((d.color >> 1) & 0x7F);
            Item.Settings s = new FabricItemSettings();
            if (d.boss) s.rarity(Rarity.EPIC);
            add(d.id + "_spawn_egg", new SpawnEggItem(ModEntities.TYPES.get(d.id), d.color, secondary, s));
        }

        Registry.register(Registries.ITEM_GROUP, RealmLegends.id("main"), FabricItemGroup.builder()
                .icon(() -> new ItemStack(Registries.ITEM.get(RealmLegends.id("void_chestplate"))))
                .displayName(Text.translatable("itemGroup.realmlegends.main"))
                .entries((ctx, entries) -> { for (Item i : ALL) entries.add(i); })
                .build());
    }
}
