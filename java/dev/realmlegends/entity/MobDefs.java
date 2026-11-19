package dev.realmlegends.entity;

import dev.realmlegends.ChestTier;
import dev.realmlegends.Realm;

import java.util.ArrayList;
import java.util.List;

/** АВТОГЕНЕРАЦИЯ: tools/generate.py. Не редактируйте вручную. */
public final class MobDefs {
    public static final List<MobDef> ALL = new ArrayList<>();

    private MobDefs() { }

    private static void reg(MobDef d) { ALL.add(d); }

    static {
        reg(new MobDef("moss_ghoul", Realm.SKY, Model.ZOMBIE, 1.0f, 0x4a7c3a, 1, Ability.POISON));
        reg(new MobDef("bog_stalker", Realm.SKY, Model.SKELETON, 1.0f, 0x3b6b57, 1, Ability.SLOW));
        reg(new MobDef("cave_crawler", Realm.SKY, Model.SPIDER, 1.0f, 0x5c4a3a, 1));
        reg(new MobDef("frost_wight", Realm.SKY, Model.ZOMBIE, 1.1f, 0x9fd8ff, 2, Ability.FROST));
        reg(new MobDef("dune_husk", Realm.SKY, Model.ZOMBIE, 1.0f, 0xd9b86a, 1, Ability.HUNGER));
        reg(new MobDef("thorn_reaver", Realm.SKY, Model.SKELETON, 1.1f, 0x2f6f2f, 2, Ability.POISON));
        reg(new MobDef("stone_sentinel", Realm.SKY, Model.ZOMBIE, 1.3f, 0x7f7f7f, 2, Ability.KNOCKBACK));
        reg(new MobDef("hollow_knight", Realm.SKY, Model.SKELETON, 1.15f, 0x8a8fa3, 2, Ability.WEAK));
        reg(new MobDef("web_weaver", Realm.SKY, Model.SPIDER, 1.3f, 0x3a2f4f, 2, Ability.SLOW));
        reg(new MobDef("swamp_hag", Realm.SKY, Model.ZOMBIE, 1.0f, 0x556b2f, 2, Ability.BLIND));
        reg(new MobDef("grave_watcher", Realm.SKY, Model.SKELETON, 1.2f, 0x4b4b5a, 3, Ability.WEAK));
        reg(new MobDef("magma_skitter", Realm.SKY, Model.SPIDER, 0.9f, 0xff6a1a, 2, Ability.FIRE));
        reg(new MobDef("crystal_spider", Realm.SKY, Model.SPIDER, 1.1f, 0x66e0ff, 2, Ability.SLOW));
        reg(new MobDef("rot_titan", Realm.SKY, Model.ZOMBIE, 1.7f, 0x3d5c2a, 3, Ability.POISON, Ability.KNOCKBACK));
        reg(new MobDef("shadow_stalker", Realm.SKY, Model.SKELETON, 1.0f, 0x2a2a3a, 2, Ability.BLIND));
        reg(new MobDef("bone_colossus", Realm.SKY, Model.SKELETON, 1.8f, 0xd8d2b8, 3, Ability.KNOCKBACK));
        reg(new MobDef("vine_strangler", Realm.SKY, Model.ZOMBIE, 1.2f, 0x2e8b3d, 2, Ability.SLOW));
        reg(new MobDef("storm_wraith", Realm.SKY, Model.SKELETON, 1.1f, 0x8fa8ff, 3, Ability.LEVITATE));
        reg(new MobDef("lava_brute", Realm.SKY, Model.ZOMBIE, 1.4f, 0xd84a10, 3, Ability.FIRE));
        reg(new MobDef("glow_recluse", Realm.SKY, Model.SPIDER, 1.0f, 0xb6ff5a, 1, Ability.POISON));
        reg(new MobDef("iron_revenant", Realm.SKY, Model.ZOMBIE, 1.3f, 0xb0b4bc, 3, Ability.KNOCKBACK, Ability.WEAK));
        reg(new MobDef("mire_leech", Realm.SKY, Model.ZOMBIE, 0.8f, 0x6b3f3f, 1, Ability.LIFESTEAL));
        reg(new MobDef("ashen_hunter", Realm.SKY, Model.SKELETON, 1.05f, 0x6e6e6e, 2, Ability.HUNGER));
        reg(new MobDef("sand_scorpion", Realm.SKY, Model.SPIDER, 1.2f, 0xe0b060, 2, Ability.POISON));
        reg(new MobDef("cursed_miner", Realm.SKY, Model.ZOMBIE, 1.1f, 0x8b6b3a, 1, Ability.WEAK));
        reg(new MobDef("deep_lurker", Realm.SKY, Model.ZOMBIE, 1.25f, 0x1f3a5f, 3, Ability.BLIND, Ability.SLOW));
        reg(new MobDef("amber_stinger", Realm.SKY, Model.SPIDER, 0.8f, 0xffb000, 1, Ability.POISON));
        reg(new MobDef("spectral_jester", Realm.SKY, Model.SKELETON, 0.9f, 0xd0a0ff, 1, Ability.TELEPORT));
        reg(new MobDef("elder_ghoul", Realm.SKY, Model.ZOMBIE, 1.5f, 0x5a4a6a, 4, Ability.WITHER, Ability.LIFESTEAL));
        reg(new MobDef("gloom_matriarch", Realm.SKY, Model.SPIDER, 1.6f, 0x2a1a3a, 4, Ability.POISON, Ability.BLIND));
        reg(new MobDef("ash_walker", Realm.EMBER, Model.ZOMBIE, 1.0f, 0x8a8a8a, 1, Ability.FIRE));
        reg(new MobDef("magma_husk", Realm.EMBER, Model.ZOMBIE, 1.1f, 0xc8480f, 2, Ability.FIRE));
        reg(new MobDef("cinder_skeleton", Realm.EMBER, Model.SKELETON, 1.0f, 0x3a3a3a, 1, Ability.FIRE));
        reg(new MobDef("blaze_spider", Realm.EMBER, Model.SPIDER, 1.1f, 0xff8a00, 2, Ability.FIRE));
        reg(new MobDef("obsidian_golem", Realm.EMBER, Model.ZOMBIE, 1.7f, 0x2a1a4a, 3, Ability.KNOCKBACK));
        reg(new MobDef("crimson_stalker", Realm.EMBER, Model.SKELETON, 1.1f, 0xa01030, 2, Ability.WITHER));
        reg(new MobDef("warped_ghoul", Realm.EMBER, Model.ZOMBIE, 1.0f, 0x1f8f8f, 2, Ability.SLOW));
        reg(new MobDef("basalt_brute", Realm.EMBER, Model.ZOMBIE, 1.4f, 0x55555f, 3, Ability.KNOCKBACK));
        reg(new MobDef("lava_widow", Realm.EMBER, Model.SPIDER, 1.3f, 0xff4500, 3, Ability.FIRE, Ability.POISON));
        reg(new MobDef("soul_reaper", Realm.EMBER, Model.SKELETON, 1.2f, 0x4fd6e6, 3, Ability.LIFESTEAL));
        reg(new MobDef("ember_imp", Realm.EMBER, Model.ZOMBIE, 0.7f, 0xff7a2a, 1, Ability.FIRE));
        reg(new MobDef("hellhound", Realm.EMBER, Model.SPIDER, 0.9f, 0x702010, 2, Ability.FIRE));
        reg(new MobDef("charred_knight", Realm.EMBER, Model.SKELETON, 1.2f, 0x2b2420, 3, Ability.FIRE, Ability.WEAK));
        reg(new MobDef("abyss_piglord", Realm.EMBER, Model.ZOMBIE, 1.3f, 0xd08080, 3, Ability.KNOCKBACK));
        reg(new MobDef("wither_acolyte", Realm.EMBER, Model.SKELETON, 1.1f, 0x1a1a1a, 3, Ability.WITHER));
        reg(new MobDef("fungal_shambler", Realm.EMBER, Model.ZOMBIE, 1.1f, 0xb03050, 1, Ability.POISON));
        reg(new MobDef("magma_tyrant", Realm.EMBER, Model.ZOMBIE, 1.8f, 0xff3300, 4, Ability.FIRE, Ability.KNOCKBACK));
        reg(new MobDef("bone_inferno", Realm.EMBER, Model.SKELETON, 1.5f, 0xff9040, 4, Ability.FIRE, Ability.WITHER));
        reg(new MobDef("glowstone_phantom", Realm.EMBER, Model.SKELETON, 1.0f, 0xffe070, 2, Ability.BLIND));
        reg(new MobDef("smolder_crawler", Realm.EMBER, Model.SPIDER, 1.0f, 0x7a3a1a, 1, Ability.FIRE));
        reg(new MobDef("brimstone_fiend", Realm.EMBER, Model.ZOMBIE, 1.3f, 0xd8d820, 2, Ability.HUNGER));
        reg(new MobDef("infernal_juggernaut", Realm.EMBER, Model.ZOMBIE, 2.0f, 0x8a0a0a, 4, Ability.FIRE, Ability.KNOCKBACK));
        reg(new MobDef("ash_banshee", Realm.EMBER, Model.SKELETON, 0.95f, 0xbfbfbf, 2, Ability.BLIND));
        reg(new MobDef("quartz_sentinel", Realm.EMBER, Model.ZOMBIE, 1.2f, 0xf0e8e0, 2, Ability.KNOCKBACK));
        reg(new MobDef("lava_leaper", Realm.EMBER, Model.SPIDER, 1.2f, 0xff6000, 2, Ability.FIRE));
        reg(new MobDef("crimson_executioner", Realm.EMBER, Model.SKELETON, 1.4f, 0x7a0010, 3, Ability.LIFESTEAL, Ability.WEAK));
        reg(new MobDef("netherrack_golem", Realm.EMBER, Model.ZOMBIE, 1.5f, 0x8a3030, 3, Ability.KNOCKBACK));
        reg(new MobDef("hell_tick", Realm.EMBER, Model.SPIDER, 0.8f, 0x5a1a1a, 1, Ability.LIFESTEAL));
        reg(new MobDef("molten_warlock", Realm.EMBER, Model.SKELETON, 1.15f, 0xff5000, 3, Ability.FIRE, Ability.TELEPORT));
        reg(new MobDef("abyssal_overseer", Realm.EMBER, Model.ZOMBIE, 1.6f, 0x3a0a20, 4, Ability.WITHER, Ability.FIRE));
        reg(new MobDef("void_walker", Realm.VOID, Model.SKELETON, 1.2f, 0x5a2a8a, 2, Ability.TELEPORT));
        reg(new MobDef("ender_husk", Realm.VOID, Model.ZOMBIE, 1.1f, 0x3a1a5a, 2, Ability.TELEPORT));
        reg(new MobDef("chorus_ghoul", Realm.VOID, Model.ZOMBIE, 1.0f, 0xb080d0, 1, Ability.HUNGER));
        reg(new MobDef("end_stalker", Realm.VOID, Model.SKELETON, 1.1f, 0xd8d890, 2, Ability.BLIND));
        reg(new MobDef("shulker_mite", Realm.VOID, Model.SPIDER, 0.9f, 0xb070b0, 1, Ability.LEVITATE));
        reg(new MobDef("star_phantom", Realm.VOID, Model.SKELETON, 1.0f, 0xc0d0ff, 3, Ability.LEVITATE));
        reg(new MobDef("obsidian_wraith", Realm.VOID, Model.SKELETON, 1.3f, 0x1a1030, 3, Ability.WITHER));
        reg(new MobDef("rift_crawler", Realm.VOID, Model.SPIDER, 1.2f, 0x6a30c0, 2, Ability.TELEPORT));
        reg(new MobDef("astral_knight", Realm.VOID, Model.ZOMBIE, 1.3f, 0x9fa0ff, 3, Ability.WEAK));
        reg(new MobDef("dim_weaver", Realm.VOID, Model.SPIDER, 1.4f, 0x4a2a7a, 3, Ability.SLOW, Ability.TELEPORT));
        reg(new MobDef("cosmic_hulk", Realm.VOID, Model.ZOMBIE, 1.9f, 0x2a2a6a, 4, Ability.KNOCKBACK));
        reg(new MobDef("nebula_reaper", Realm.VOID, Model.SKELETON, 1.3f, 0xff60c0, 3, Ability.LIFESTEAL));
        reg(new MobDef("endstone_golem", Realm.VOID, Model.ZOMBIE, 1.6f, 0xe0e0a0, 3, Ability.KNOCKBACK));
        reg(new MobDef("void_leech", Realm.VOID, Model.SPIDER, 0.8f, 0x301050, 1, Ability.LIFESTEAL));
        reg(new MobDef("eclipse_stalker", Realm.VOID, Model.SKELETON, 1.2f, 0x101020, 3, Ability.BLIND, Ability.WITHER));
        reg(new MobDef("warp_cultist", Realm.VOID, Model.ZOMBIE, 1.0f, 0x7040a0, 2, Ability.TELEPORT));
        reg(new MobDef("meteor_brute", Realm.VOID, Model.ZOMBIE, 1.5f, 0x6a5040, 3, Ability.FIRE, Ability.KNOCKBACK));
        reg(new MobDef("ender_weeper", Realm.VOID, Model.SKELETON, 0.95f, 0xa0a0ff, 1, Ability.SLOW));
        reg(new MobDef("pulsar_spider", Realm.VOID, Model.SPIDER, 1.1f, 0x60ffd0, 2, Ability.WEAK));
        reg(new MobDef("quantum_ghoul", Realm.VOID, Model.ZOMBIE, 1.1f, 0x40e0ff, 2, Ability.TELEPORT));
        reg(new MobDef("abyss_herald", Realm.VOID, Model.SKELETON, 1.4f, 0x8000ff, 4, Ability.WITHER, Ability.TELEPORT));
        reg(new MobDef("gravity_warden", Realm.VOID, Model.ZOMBIE, 1.4f, 0x5050d0, 3, Ability.LEVITATE));
        reg(new MobDef("oblivion_shade", Realm.VOID, Model.SKELETON, 1.0f, 0x202030, 2, Ability.BLIND));
        reg(new MobDef("draconic_spawn", Realm.VOID, Model.ZOMBIE, 1.2f, 0xa040d0, 3, Ability.WITHER));
        reg(new MobDef("singularity_crawler", Realm.VOID, Model.SPIDER, 1.5f, 0x0a0020, 4, Ability.LEVITATE, Ability.WITHER));
        reg(new MobDef("crystal_seraph", Realm.VOID, Model.SKELETON, 1.5f, 0xffd0ff, 4, Ability.LIFESTEAL, Ability.LEVITATE));
        reg(new MobDef("ender_berserk", Realm.VOID, Model.ZOMBIE, 1.3f, 0xc04040, 3, Ability.KNOCKBACK, Ability.TELEPORT));
        reg(new MobDef("void_priest", Realm.VOID, Model.SKELETON, 1.15f, 0x6030a0, 3, Ability.BLIND, Ability.TELEPORT));
        reg(new MobDef("celestial_devourer", Realm.VOID, Model.SPIDER, 1.7f, 0xff90ff, 4, Ability.LIFESTEAL, Ability.WITHER));
        reg(new MobDef("cosmic_lurker", Realm.VOID, Model.ZOMBIE, 1.0f, 0x3050a0, 2, Ability.SLOW));
        reg(MobDef.boss("tomb_warden", Realm.SKY, Model.SKELETON, 2.0f, 0x8c8cff, 300, 12, 0.26, new Ability[]{Ability.WEAK, Ability.KNOCKBACK}, new MobDef.Reward(ChestTier.COMMON, 1.0f), new MobDef.Reward(ChestTier.RARE, 1.0f), new MobDef.Reward(ChestTier.EPIC, 0.3f)));
        reg(MobDef.boss("hargrot", Realm.SKY, Model.ZOMBIE, 2.4f, 0x2f5f2f, 600, 16, 0.25, new Ability[]{Ability.POISON, Ability.LIFESTEAL}, new MobDef.Reward(ChestTier.RARE, 1.0f), new MobDef.Reward(ChestTier.EPIC, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 1.0f)));
        reg(MobDef.boss("cinder_reaper", Realm.EMBER, Model.SKELETON, 2.2f, 0xff5a1f, 500, 18, 0.27, new Ability[]{Ability.FIRE, Ability.WITHER}, new MobDef.Reward(ChestTier.RARE, 1.0f), new MobDef.Reward(ChestTier.EPIC, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 0.3f)));
        reg(MobDef.boss("malzarat", Realm.EMBER, Model.ZOMBIE, 2.8f, 0xb01010, 900, 22, 0.25, new Ability[]{Ability.FIRE, Ability.LIFESTEAL, Ability.KNOCKBACK}, new MobDef.Reward(ChestTier.EPIC, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 1.0f)));
        reg(MobDef.boss("void_keeper", Realm.VOID, Model.SPIDER, 2.5f, 0x7a3cff, 700, 20, 0.32, new Ability[]{Ability.TELEPORT, Ability.BLIND}, new MobDef.Reward(ChestTier.EPIC, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 0.4f)));
        reg(MobDef.boss("nulhar", Realm.VOID, Model.SKELETON, 3.0f, 0xc050ff, 1000, 26, 0.27, new Ability[]{Ability.TELEPORT, Ability.LEVITATE, Ability.WITHER}, new MobDef.Reward(ChestTier.EPIC, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 1.0f), new MobDef.Reward(ChestTier.LEGENDARY, 0.5f)));
    }
}
