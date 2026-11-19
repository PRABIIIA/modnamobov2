package dev.realmlegends.client;

import dev.realmlegends.entity.CustomMob;
import dev.realmlegends.entity.MobDef;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/** Ванильные модели зомби/скелета/паука + масштаб + цветной оверлей. */
public class CustomMobRenderer extends MobEntityRenderer<CustomMob, EntityModel<CustomMob>> {
    private final MobDef def;

    public CustomMobRenderer(EntityRendererFactory.Context ctx, MobDef def) {
        super(ctx, makeModel(ctx, def), 0.5f * def.scale);
        this.def = def;
        addFeature(new TintLayer(this, def));
    }

    private static EntityModel<CustomMob> makeModel(EntityRendererFactory.Context ctx, MobDef def) {
        return switch (def.model) {
            case ZOMBIE -> new MobBipedModel(ctx.getPart(EntityModelLayers.ZOMBIE));
            case SKELETON -> new MobBipedModel(ctx.getPart(EntityModelLayers.SKELETON));
            case SPIDER -> new SpiderEntityModel<CustomMob>(ctx.getPart(EntityModelLayers.SPIDER));
        };
    }

    @Override
    public Identifier getTexture(CustomMob entity) { return def.model.texture; }

    @Override
    protected void scale(CustomMob entity, MatrixStack matrices, float amount) {
        matrices.scale(def.scale, def.scale, def.scale);
    }

    static class MobBipedModel extends BipedEntityModel<CustomMob> {
        MobBipedModel(ModelPart root) { super(root); }

        @Override
        public void setAngles(CustomMob e, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
            super.setAngles(e, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, e.isAttacking(), this.handSwingProgress, animationProgress);
        }
    }

    static class TintLayer extends FeatureRenderer<CustomMob, EntityModel<CustomMob>> {
        private final float r, g, b, a;
        private final Identifier texture;

        TintLayer(FeatureRendererContext<CustomMob, EntityModel<CustomMob>> ctx, MobDef def) {
            super(ctx);
            this.r = ((def.color >> 16) & 255) / 255f;
            this.g = ((def.color >> 8) & 255) / 255f;
            this.b = (def.color & 255) / 255f;
            this.a = def.boss ? 0.7f : 0.6f;
            this.texture = def.model.texture;
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumerProvider vcp, int light, CustomMob entity,
                           float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                           float headYaw, float headPitch) {
            if (entity.isInvisible()) return;
            VertexConsumer vc = vcp.getBuffer(RenderLayer.getEntityTranslucent(texture));
            getContextModel().render(matrices, vc, light, LivingEntityRenderer.getOverlay(entity, 0f), r, g, b, a);
        }
    }
}
