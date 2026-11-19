package dev.realmlegends.entity;

import net.minecraft.util.Identifier;

/** Базовая ванильная модель моба (текстура берётся из игры и подкрашивается). */
public enum Model {
    ZOMBIE(0.6f, 1.95f, "textures/entity/zombie/zombie.png"),
    SKELETON(0.6f, 1.99f, "textures/entity/skeleton/skeleton.png"),
    SPIDER(1.4f, 0.9f, "textures/entity/spider/spider.png");

    public final float width, height;
    public final Identifier texture;

    Model(float w, float h, String tex) {
        this.width = w;
        this.height = h;
        this.texture = new Identifier(tex);
    }
}
