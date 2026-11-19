package dev.realmlegends.client;

import dev.realmlegends.ModEntities;
import dev.realmlegends.ModItems;
import dev.realmlegends.RealmLegends;
import dev.realmlegends.entity.MobDef;
import dev.realmlegends.entity.MobDefs;
import dev.realmlegends.item.RealmArmorItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RealmLegendsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (MobDef d : MobDefs.ALL) {
            EntityRendererRegistry.register(ModEntities.TYPES.get(d.id), ctx -> new CustomMobRenderer(ctx, d));
        }
        for (RealmArmorItem a : ModItems.ARMOR) {
            ArmorRenderer.register(new RealmArmorRenderer(a.realm, a.getType()), a);
        }
        ItemTooltipCallback.EVENT.register((stack, ctx, lines) -> {
            if (!RealmLegends.MOD_ID.equals(Registries.ITEM.getId(stack.getItem()).getNamespace())) return;
            for (String suffix : new String[]{".desc", ".desc2"}) {
                String key = stack.getTranslationKey() + suffix;
                if (I18n.hasTranslation(key)) lines.add(Text.translatable(key).formatted(Formatting.GOLD));
            }
        });
    }
}
