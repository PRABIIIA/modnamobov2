package dev.realmlegends;

import dev.realmlegends.entity.CustomMob;
import dev.realmlegends.entity.MobDef;
import dev.realmlegends.entity.MobDefs;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class ModEntities {
    public static final Map<String, EntityType<CustomMob>> TYPES = new LinkedHashMap<>();

    private ModEntities() { }

    public static void register() {
        for (MobDef d : MobDefs.ALL) {
            FabricEntityTypeBuilder.Mob<CustomMob> b = FabricEntityTypeBuilder.createMob();
            b.spawnGroup(SpawnGroup.MONSTER);
            b.entityFactory((type, world) -> new CustomMob(type, world, d));
            b.dimensions(EntityDimensions.fixed(d.model.width * d.scale, d.model.height * d.scale));
            b.trackRangeBlocks(d.boss ? 128 : 80);
            if (d.realm == Realm.EMBER) b.fireImmune();
            b.defaultAttributes(() -> CustomMob.createAttributes(d));

            final boolean boss = d.boss;
            final boolean overworld = d.realm == Realm.SKY;
            SpawnRestriction.SpawnPredicate<CustomMob> pred = (type, world, reason, pos, random) -> {
                boolean ok = overworld
                        ? HostileEntity.canSpawnInDark(type, world, reason, pos, random)
                        : world.getDifficulty() != Difficulty.PEACEFUL && MobEntity.canMobSpawn(type, world, reason, pos, random);
                return ok && (!boss || random.nextInt(6) == 0); // боссы появляются редко
            };
            b.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pred);

            EntityType<CustomMob> type = Registry.register(Registries.ENTITY_TYPE, RealmLegends.id(d.id), b.build());
            TYPES.put(d.id, type);

            Predicate<BiomeSelectionContext> sel = switch (d.realm) {
                case SKY -> BiomeSelectors.foundInOverworld();
                case EMBER -> BiomeSelectors.foundInTheNether();
                case VOID -> BiomeSelectors.foundInTheEnd();
            };
            int max = d.boss ? 1 : (d.tier <= 2 ? 3 : 2);
            BiomeModifications.addSpawn(sel, SpawnGroup.MONSTER, type, d.spawnWeight(), 1, max);
        }
    }
}
