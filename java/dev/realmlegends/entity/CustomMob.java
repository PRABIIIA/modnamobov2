package dev.realmlegends.entity;

import dev.realmlegends.ChestTier;
import dev.realmlegends.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomMob extends HostileEntity {
    private static final int[][] OFFSETS = {{0, 0}, {2, 0}, {-2, 0}, {0, 2}, {0, -2}, {2, 2}, {-2, -2}, {2, -2}, {-2, 2}};

    private final MobDef def;
    private ServerBossBar bossBar;
    private boolean enraged;
    private int summonStage;

    public CustomMob(EntityType<? extends CustomMob> type, World world, MobDef def) {
        super(type, world);
        this.def = def;
        this.experiencePoints = def.boss ? 500 : def.tier * 8;
        if (def.boss) {
            setPersistent();
            bossBar = new ServerBossBar(getDisplayName(), BossBar.Color.PURPLE, BossBar.Style.NOTCHED_10);
        }
    }

    public MobDef getDef() { return def; }

    public static DefaultAttributeContainer.Builder createAttributes(MobDef d) {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, d.health)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, d.damage)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, d.speed)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, d.boss ? 48 : 32)
                .add(EntityAttributes.GENERIC_ARMOR, d.tier * 2)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, d.boss ? 0.8 : Math.max(0, Math.min(0.6, (d.scale - 1f) * 0.5)));
    }

    // NB: initGoals вызывается из конструктора родителя, поле def здесь ещё null — не использовать его.
    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        goalSelector.add(7, new WanderAroundFarGoal(this, 0.8));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        goalSelector.add(8, new LookAroundGoal(this));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean hit = super.tryAttack(target);
        if (hit && target instanceof LivingEntity le) {
            for (Ability a : def.abilities) a.apply(this, le);
        }
        return hit;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean r = super.damage(source, amount);
        if (r && !getWorld().isClient && getHealth() > 0) {
            for (Ability a : def.abilities) {
                if (a == Ability.TELEPORT && random.nextFloat() < 0.25f) teleportRandomly();
            }
        }
        return r;
    }

    private void teleportRandomly() {
        for (int i = 0; i < 8; i++) {
            double dx = (random.nextDouble() - 0.5) * 12, dz = (random.nextDouble() - 0.5) * 12;
            if (getWorld().isSpaceEmpty(this, getBoundingBox().offset(dx, 0, dz))
                    && !getWorld().containsFluid(getBoundingBox().offset(dx, 0, dz))) {
                requestTeleport(getX() + dx, getY(), getZ() + dz);
                playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
                return;
            }
        }
    }

    // ---------- Босс ----------
    @Override
    protected void mobTick() {
        super.mobTick();
        if (bossBar == null) return;
        float p = getHealth() / getMaxHealth();
        bossBar.setPercent(p);
        if (!enraged && p < 0.5f) {
            enraged = true;
            addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 1_000_000, 1, false, false));
            addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1_000_000, 0, false, false));
            playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, 2f, 0.8f);
        }
        if (summonStage == 0 && p < 0.66f) { summonMinions(); summonStage = 1; }
        if (summonStage == 1 && p < 0.33f) { summonMinions(); summonStage = 2; }
    }

    private void summonMinions() {
        List<MobDef> pool = new ArrayList<>();
        for (MobDef d : MobDefs.ALL) if (!d.boss && d.realm == def.realm) pool.add(d);
        for (int i = 0; i < 3; i++) {
            MobDef d = pool.get(random.nextInt(pool.size()));
            CustomMob m = ModEntities.TYPES.get(d.id).create(getWorld());
            if (m == null) continue;
            m.refreshPositionAndAngles(getX() + random.nextInt(5) - 2, getY(), getZ() + random.nextInt(5) - 2, random.nextFloat() * 360f, 0f);
            m.setTarget(getTarget());
            getWorld().spawnEntity(m);
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        if (bossBar != null) bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        if (bossBar != null) bossBar.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        if (bossBar != null) bossBar.setName(getDisplayName());
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (bossBar != null) bossBar.clearPlayers();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) { return !def.boss; }

    // ---------- Сундуки с добычей ----------
    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (def.boss && getWorld() instanceof ServerWorld sw) dropChests(sw);
    }

    private void dropChests(ServerWorld sw) {
        int slot = 0;
        for (MobDef.Reward r : def.rewards) {
            if (random.nextFloat() > r.chance()) continue;
            if (slot >= OFFSETS.length) break;
            BlockPos base = getBlockPos().add(OFFSETS[slot][0], 0, OFFSETS[slot][1]);
            slot++;
            placeChest(sw, findSpot(sw, base), r.chest());
        }
        sw.spawnParticles(ParticleTypes.TOTEM_OF_UNDYING, getX(), getY() + 1, getZ(), 80, 1, 1, 1, 0.3);
    }

    private BlockPos findSpot(ServerWorld sw, BlockPos start) {
        for (int dy = 2; dy >= -4; dy--) {
            BlockPos p = start.up(dy);
            BlockState s = sw.getBlockState(p);
            if ((s.isAir() || s.isReplaceable()) && sw.getBlockState(p.down()).isSolidBlock(sw, p.down())) return p;
        }
        return getBlockPos();
    }

    private void placeChest(ServerWorld sw, BlockPos p, ChestTier tier) {
        sw.setBlockState(p, Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.Type.HORIZONTAL.random(random)));
        BlockEntity be = sw.getBlockEntity(p);
        if (be instanceof ChestBlockEntity chest) {
            chest.setLootTable(tier.lootTable(def.realm), random.nextLong());
            chest.setCustomName(Text.translatable(tier.nameKey()).formatted(tier.color));
        }
        sw.spawnParticles(ParticleTypes.END_ROD, p.getX() + 0.5, p.getY() + 1.2, p.getZ() + 0.5, 20, 0.3, 0.5, 0.3, 0.05);
        sw.playSound(null, p, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.HOSTILE, 1f, 1f);
    }

    // ---------- Звуки ----------
    @Override
    protected SoundEvent getAmbientSound() {
        return switch (def.model) {
            case ZOMBIE -> SoundEvents.ENTITY_ZOMBIE_AMBIENT;
            case SKELETON -> SoundEvents.ENTITY_SKELETON_AMBIENT;
            case SPIDER -> SoundEvents.ENTITY_SPIDER_AMBIENT;
        };
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return switch (def.model) {
            case ZOMBIE -> SoundEvents.ENTITY_ZOMBIE_HURT;
            case SKELETON -> SoundEvents.ENTITY_SKELETON_HURT;
            case SPIDER -> SoundEvents.ENTITY_SPIDER_HURT;
        };
    }

    @Override
    protected SoundEvent getDeathSound() {
        return switch (def.model) {
            case ZOMBIE -> SoundEvents.ENTITY_ZOMBIE_DEATH;
            case SKELETON -> SoundEvents.ENTITY_SKELETON_DEATH;
            case SPIDER -> SoundEvents.ENTITY_SPIDER_DEATH;
        };
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent s = switch (def.model) {
            case ZOMBIE -> SoundEvents.ENTITY_ZOMBIE_STEP;
            case SKELETON -> SoundEvents.ENTITY_SKELETON_STEP;
            case SPIDER -> SoundEvents.ENTITY_SPIDER_STEP;
        };
        playSound(s, 0.15f, 1f);
    }
}
