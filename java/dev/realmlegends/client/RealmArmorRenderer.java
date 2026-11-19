package dev.realmlegends.client;

import dev.realmlegends.Realm;
import dev.realmlegends.RealmLegends;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

/** Рисует кастомную 3D-модель брони (крылья, рога, нимб, наплечники...). */
public class RealmArmorRenderer implements ArmorRenderer {
    private final ArmorItem.Type type;
    private final Realm realm;
    private final Identifier texture;
    private BipedEntityModel<LivingEntity> model;

    public RealmArmorRenderer(Realm realm, ArmorItem.Type type) {
        this.realm = realm;
        this.type = type;
        this.texture = RealmLegends.id("textures/models/armor/" + realm.key + ".png");
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity,
                       EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if (model == null) model = ArmorModels.build(realm, type);
        contextModel.copyBipedStateTo(model);
        model.setVisible(false);
        switch (type) {
            case HELMET -> model.head.visible = true;
            case CHESTPLATE -> { model.body.visible = true; model.rightArm.visible = true; model.leftArm.visible = true; }
            case LEGGINGS -> { model.body.visible = true; model.rightLeg.visible = true; model.leftLeg.visible = true; }
            case BOOTS -> { model.rightLeg.visible = true; model.leftLeg.visible = true; }
        }
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
    }
}
