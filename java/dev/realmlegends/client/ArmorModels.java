package dev.realmlegends.client;

import dev.realmlegends.Realm;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;

/**
 * 3D-модели брони. К стандартной «бипед»-модели добавляются кубы:
 * SKY — крылья и плавники, EMBER — рога, шипы и драконьи крылья, VOID — нимб и парящие кристаллы.
 * Вращения не используются (только ступенчатые кубы) — так модель выглядит одинаково при любой ориентации осей.
 */
public final class ArmorModels {
    private ArmorModels() { }

    public static BipedEntityModel<LivingEntity> build(Realm realm, ArmorItem.Type type) {
        ModelData md = BipedEntityModel.getModelData(new Dilation(type == ArmorItem.Type.LEGGINGS ? 0.5f : 1.0f), 0f);
        ModelPartData root = md.getRoot();
        switch (type) {
            case HELMET -> helmet(root.getChild("head"), realm);
            case CHESTPLATE -> chest(root.getChild("body"), root.getChild("right_arm"), root.getChild("left_arm"), realm);
            case LEGGINGS -> legs(root.getChild("right_leg"), root.getChild("left_leg"), realm);
            case BOOTS -> boots(root.getChild("right_leg"), root.getChild("left_leg"), realm);
        }
        return new BipedEntityModel<>(TexturedModelData.of(md, 64, 64).createModel());
    }

    // ---- вспомогательные ----
    private static void box(ModelPartData p, String name, boolean mirror, int u, int v,
                            float x, float y, float z, float sx, float sy, float sz) {
        if (mirror) x = -x - sx;
        p.addChild(name, ModelPartBuilder.create().uv(u, v).cuboid(x, y, z, sx, sy, sz), ModelTransform.NONE);
    }

    /** Куб справа и зеркально слева. */
    private static void pair(ModelPartData right, ModelPartData left, String name, int u, int v,
                             float x, float y, float z, float sx, float sy, float sz) {
        box(right, name + "_r", false, u, v, x, y, z, sx, sy, sz);
        box(left, name + "_l", true, u, v, x, y, z, sx, sy, sz);
    }

    // ---- шлем ----
    private static void helmet(ModelPartData head, Realm realm) {
        switch (realm) {
            case SKY -> {
                pair(head, head, "fin1", 0, 32, -5.5f, -7f, -1f, 1.5f, 5f, 7f);
                pair(head, head, "fin2", 0, 32, -6.5f, -8f, 1f, 1.0f, 5f, 5f);
                pair(head, head, "fin3", 0, 32, -7.5f, -9f, 3f, 1.0f, 4f, 3f);
                box(head, "crest", false, 32, 32, -0.5f, -11f, -4f, 1f, 3f, 8f);
            }
            case EMBER -> {
                pair(head, head, "horn1", 0, 32, -6f, -9f, -1f, 2f, 3f, 2f);
                pair(head, head, "horn2", 0, 32, -7f, -12f, -1f, 2f, 3f, 2f);
                pair(head, head, "horn3", 0, 32, -8f, -15f, -0.5f, 1.5f, 3f, 1.5f);
                pair(head, head, "horn4", 0, 32, -8.5f, -17f, -0.5f, 1f, 2f, 1f);
                box(head, "brow", false, 32, 32, -4.5f, -6f, -5f, 9f, 1f, 1f);
            }
            case VOID -> {
                box(head, "halo_f", false, 0, 32, -5f, -14f, -5f, 10f, 1f, 1f);
                box(head, "halo_b", false, 0, 32, -5f, -14f, 4f, 10f, 1f, 1f);
                box(head, "halo_l", false, 0, 32, -5f, -14f, -4f, 1f, 1f, 8f);
                box(head, "halo_r", false, 0, 32, 4f, -14f, -4f, 1f, 1f, 8f);
                for (int i = -2; i <= 2; i++) {
                    box(head, "spike" + i, false, 32, 32, i * 1.6f - 0.5f, -11f - (2 - Math.abs(i)), -4.5f, 1f, 3f + (2 - Math.abs(i)), 1f);
                }
            }
        }
    }

    // ---- нагрудник ----
    private static void chest(ModelPartData body, ModelPartData rArm, ModelPartData lArm, Realm realm) {
        pair(rArm, lArm, "pauldron", 0, 48, -4.5f, -3.5f, -3f, 6f, 3f, 6f);
        switch (realm) {
            case SKY -> {
                pair(rArm, lArm, "pside", 0, 48, -5.5f, -2f, -2f, 1f, 5f, 4f);
                for (int i = 0; i < 4; i++) { // крыло из перьев, расходящееся вверх
                    float top = -1f - 3f * i, bot = 8f - 2f * i;
                    pair(body, body, "wing" + i, 0, 32, -6f - 3f * i, top, 3.2f, 3f, bot - top, 0.6f);
                }
            }
            case EMBER -> {
                pair(rArm, lArm, "pspike", 0, 48, -3.5f, -8f, -1f, 2f, 5f, 2f);
                pair(rArm, lArm, "pspike2", 0, 48, -3f, -11f, -0.5f, 1f, 3f, 1f);
                for (int i = 0; i < 3; i++) { // кожистые крылья с когтями
                    float top = -4f - 4f * i, bot = 9f - 3f * i;
                    pair(body, body, "wing" + i, 0, 32, -6f - 4f * i, top, 3.2f, 4f, bot - top, 0.6f);
                    pair(body, body, "claw" + i, 32, 32, -6f - 4f * i, top - 2f, 3.2f, 1f, 2f, 1f);
                }
                box(body, "ridge", false, 32, 32, -1f, -2f, 3f, 2f, 12f, 2f);
            }
            case VOID -> {
                pair(rArm, lArm, "pshard", 0, 48, -3.5f, -8f, -1f, 1.5f, 5f, 1.5f);
                pair(body, body, "crystalA", 0, 32, -7f, -6f, 3f, 2f, 10f, 2f);
                pair(body, body, "crystalB", 0, 32, -10f, -2f, 3.5f, 2f, 8f, 2f);
                pair(body, body, "crystalC", 0, 32, -4f, -11f, 3f, 2f, 12f, 2f);
                box(body, "core", false, 32, 32, -2f, 2f, 2.5f, 4f, 4f, 2f);
            }
        }
    }

    // ---- поножи ----
    private static void legs(ModelPartData rLeg, ModelPartData lLeg, Realm realm) {
        pair(rLeg, lLeg, "knee", 0, 32, -3f, 4f, -3.5f, 6f, 3f, 1.5f);
        switch (realm) {
            case SKY -> pair(rLeg, lLeg, "thigh", 0, 32, -3.8f, 0f, -1f, 1f, 5f, 3f);
            case EMBER -> pair(rLeg, lLeg, "kspike", 0, 32, -1f, 4.5f, -6f, 2f, 2f, 2.5f);
            case VOID -> pair(rLeg, lLeg, "rune", 0, 32, -3.6f, 2f, -1f, 1f, 6f, 2f);
        }
    }

    // ---- ботинки ----
    private static void boots(ModelPartData rLeg, ModelPartData lLeg, Realm realm) {
        switch (realm) {
            case SKY -> {
                pair(rLeg, lLeg, "bwing1", 0, 32, -4f, 7f, -1f, 1.5f, 4f, 5f);
                pair(rLeg, lLeg, "bwing2", 0, 32, -5f, 8f, 1f, 1f, 3f, 4f);
            }
            case EMBER -> {
                pair(rLeg, lLeg, "spur", 0, 32, -1f, 9f, 2.5f, 2f, 2f, 3.5f);
                pair(rLeg, lLeg, "toe", 0, 32, -1f, 10f, -5.5f, 2f, 1.5f, 3f);
            }
            case VOID -> pair(rLeg, lLeg, "ring", 0, 32, -3f, 8f, -3f, 6f, 0.8f, 6f);
        }
    }
}
