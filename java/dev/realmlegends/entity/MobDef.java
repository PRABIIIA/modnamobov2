package dev.realmlegends.entity;

import dev.realmlegends.ChestTier;
import dev.realmlegends.Realm;

public final class MobDef {
    public record Reward(ChestTier chest, float chance) { }

    private static final double[] HP = {24, 40, 64, 100};
    private static final double[] DMG = {4, 6, 8, 11};

    public final String id;
    public final Realm realm;
    public final Model model;
    public final float scale;
    public final int color;
    public final int tier;
    public final boolean boss;
    public final Ability[] abilities;
    public final double health, damage, speed;
    public final Reward[] rewards;

    private MobDef(String id, Realm realm, Model model, float scale, int color, int tier, boolean boss,
                   Ability[] abilities, double health, double damage, double speed, Reward[] rewards) {
        this.id = id; this.realm = realm; this.model = model; this.scale = scale; this.color = color;
        this.tier = tier; this.boss = boss; this.abilities = abilities;
        this.health = health; this.damage = damage; this.speed = speed; this.rewards = rewards;
    }

    /** Обычный моб: характеристики считаются из тира (1..4) и мира. */
    public MobDef(String id, Realm realm, Model model, float scale, int color, int tier, Ability... abilities) {
        this(id, realm, model, scale, color, tier, false, abilities,
                HP[tier - 1] * hpMul(realm), DMG[tier - 1] * dmgMul(realm),
                (model == Model.SPIDER ? 0.30 : 0.25) - (scale - 1f) * 0.03, new Reward[0]);
    }

    public static MobDef boss(String id, Realm realm, Model model, float scale, int color,
                              double hp, double dmg, double speed, Ability[] abilities, Reward... rewards) {
        return new MobDef(id, realm, model, scale, color, 4, true, abilities, hp, dmg, speed, rewards);
    }

    private static double hpMul(Realm r) { return r == Realm.SKY ? 1.0 : r == Realm.EMBER ? 1.15 : 1.3; }
    private static double dmgMul(Realm r) { return r == Realm.SKY ? 1.0 : r == Realm.EMBER ? 1.1 : 1.2; }

    public int spawnWeight() {
        if (boss) return 1;
        return switch (tier) { case 1 -> 6; case 2 -> 4; case 3 -> 2; default -> 1; };
    }
}
