#!/usr/bin/env python3
"""Генератор повторяющихся файлов мода: MobDefs.java, lang, loot tables, 3D-модели предметов, текстуры.
Запуск из корня проекта:  python3 tools/generate.py   (нужен Pillow: pip install pillow)
Чтобы добавить моба — допишите строку в MOBS_RAW и запустите скрипт снова.
"""
import json, os, random
from PIL import Image

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
RES = os.path.join(ROOT, "src/main/resources")
ASSETS = os.path.join(RES, "assets/realmlegends")
DATA = os.path.join(RES, "data/realmlegends")
JAVA = os.path.join(ROOT, "src/main/java/dev/realmlegends")
MOD = "realmlegends"
random.seed(1337)


def w(path, text):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(text)


def wj(path, obj):
    w(path, json.dumps(obj, ensure_ascii=False, indent=1))


# ------------------------------------------------------------------ МОБЫ
# id|рус|eng|модель(Z/S/P)|масштаб|цвет|тир(1-4)|способности
MOBS_RAW = """
SKY
moss_ghoul|Моховой упырь|Moss Ghoul|Z|1.0|4a7c3a|1|POISON
bog_stalker|Болотный охотник|Bog Stalker|S|1.0|3b6b57|1|SLOW
cave_crawler|Пещерный ползун|Cave Crawler|P|1.0|5c4a3a|1|
frost_wight|Ледяной вайт|Frost Wight|Z|1.1|9fd8ff|2|FROST
dune_husk|Барханный иссушитель|Dune Husk|Z|1.0|d9b86a|1|HUNGER
thorn_reaver|Терновый жнец|Thorn Reaver|S|1.1|2f6f2f|2|POISON
stone_sentinel|Каменный часовой|Stone Sentinel|Z|1.3|7f7f7f|2|KNOCKBACK
hollow_knight|Полый рыцарь|Hollow Knight|S|1.15|8a8fa3|2|WEAK
web_weaver|Ткач паутины|Web Weaver|P|1.3|3a2f4f|2|SLOW
swamp_hag|Болотная карга|Swamp Hag|Z|1.0|556b2f|2|BLIND
grave_watcher|Могильный сторож|Grave Watcher|S|1.2|4b4b5a|3|WEAK
magma_skitter|Магмовый ползун|Magma Skitter|P|0.9|ff6a1a|2|FIRE
crystal_spider|Кристальный паук|Crystal Spider|P|1.1|66e0ff|2|SLOW
rot_titan|Гнилой титан|Rot Titan|Z|1.7|3d5c2a|3|POISON,KNOCKBACK
shadow_stalker|Теневой преследователь|Shadow Stalker|S|1.0|2a2a3a|2|BLIND
bone_colossus|Костяной колосс|Bone Colossus|S|1.8|d8d2b8|3|KNOCKBACK
vine_strangler|Лозный душитель|Vine Strangler|Z|1.2|2e8b3d|2|SLOW
storm_wraith|Грозовой призрак|Storm Wraith|S|1.1|8fa8ff|3|LEVITATE
lava_brute|Лавовый громила|Lava Brute|Z|1.4|d84a10|3|FIRE
glow_recluse|Светящийся отшельник|Glow Recluse|P|1.0|b6ff5a|1|POISON
iron_revenant|Железный ревенант|Iron Revenant|Z|1.3|b0b4bc|3|KNOCKBACK,WEAK
mire_leech|Топяная пиявка|Mire Leech|Z|0.8|6b3f3f|1|LIFESTEAL
ashen_hunter|Пепельный следопыт|Ashen Hunter|S|1.05|6e6e6e|2|HUNGER
sand_scorpion|Песчаный скорпион|Sand Scorpion|P|1.2|e0b060|2|POISON
cursed_miner|Проклятый шахтёр|Cursed Miner|Z|1.1|8b6b3a|1|WEAK
deep_lurker|Глубинный затаившийся|Deep Lurker|Z|1.25|1f3a5f|3|BLIND,SLOW
amber_stinger|Янтарный жалоносец|Amber Stinger|P|0.8|ffb000|1|POISON
spectral_jester|Призрачный шут|Spectral Jester|S|0.9|d0a0ff|1|TELEPORT
elder_ghoul|Древний гуль|Elder Ghoul|Z|1.5|5a4a6a|4|WITHER,LIFESTEAL
gloom_matriarch|Мрачная матриарх|Gloom Matriarch|P|1.6|2a1a3a|4|POISON,BLIND
EMBER
ash_walker|Пепельный бродяга|Ash Walker|Z|1.0|8a8a8a|1|FIRE
magma_husk|Магмовая оболочка|Magma Husk|Z|1.1|c8480f|2|FIRE
cinder_skeleton|Угольный скелет|Cinder Skeleton|S|1.0|3a3a3a|1|FIRE
blaze_spider|Огненный паук|Blaze Spider|P|1.1|ff8a00|2|FIRE
obsidian_golem|Обсидиановый голем|Obsidian Golem|Z|1.7|2a1a4a|3|KNOCKBACK
crimson_stalker|Багровый охотник|Crimson Stalker|S|1.1|a01030|2|WITHER
warped_ghoul|Искажённый упырь|Warped Ghoul|Z|1.0|1f8f8f|2|SLOW
basalt_brute|Базальтовый громила|Basalt Brute|Z|1.4|55555f|3|KNOCKBACK
lava_widow|Лавовая вдова|Lava Widow|P|1.3|ff4500|3|FIRE,POISON
soul_reaper|Жнец душ|Soul Reaper|S|1.2|4fd6e6|3|LIFESTEAL
ember_imp|Огненный бес|Ember Imp|Z|0.7|ff7a2a|1|FIRE
hellhound|Адская гончая|Hellhound|P|0.9|702010|2|FIRE
charred_knight|Обугленный рыцарь|Charred Knight|S|1.2|2b2420|3|FIRE,WEAK
abyss_piglord|Свиной лорд бездны|Abyss Pig-Lord|Z|1.3|d08080|3|KNOCKBACK
wither_acolyte|Прислужник визера|Wither Acolyte|S|1.1|1a1a1a|3|WITHER
fungal_shambler|Грибной шаркун|Fungal Shambler|Z|1.1|b03050|1|POISON
magma_tyrant|Магмовый тиран|Magma Tyrant|Z|1.8|ff3300|4|FIRE,KNOCKBACK
bone_inferno|Костяной инферно|Bone Inferno|S|1.5|ff9040|4|FIRE,WITHER
glowstone_phantom|Светокаменный фантом|Glowstone Phantom|S|1.0|ffe070|2|BLIND
smolder_crawler|Тлеющий ползун|Smolder Crawler|P|1.0|7a3a1a|1|FIRE
brimstone_fiend|Серный изверг|Brimstone Fiend|Z|1.3|d8d820|2|HUNGER
infernal_juggernaut|Инфернальный джаггернаут|Infernal Juggernaut|Z|2.0|8a0a0a|4|FIRE,KNOCKBACK
ash_banshee|Пепельная банши|Ash Banshee|S|0.95|bfbfbf|2|BLIND
quartz_sentinel|Кварцевый страж|Quartz Sentinel|Z|1.2|f0e8e0|2|KNOCKBACK
lava_leaper|Лавовый прыгун|Lava Leaper|P|1.2|ff6000|2|FIRE
crimson_executioner|Багровый палач|Crimson Executioner|S|1.4|7a0010|3|LIFESTEAL,WEAK
netherrack_golem|Голем из незерака|Netherrack Golem|Z|1.5|8a3030|3|KNOCKBACK
hell_tick|Адский клещ|Hell Tick|P|0.8|5a1a1a|1|LIFESTEAL
molten_warlock|Расплавленный чернокнижник|Molten Warlock|S|1.15|ff5000|3|FIRE,TELEPORT
abyssal_overseer|Бездонный надзиратель|Abyssal Overseer|Z|1.6|3a0a20|4|WITHER,FIRE
VOID
void_walker|Бродяга Пустоты|Void Walker|S|1.2|5a2a8a|2|TELEPORT
ender_husk|Эндерская оболочка|Ender Husk|Z|1.1|3a1a5a|2|TELEPORT
chorus_ghoul|Хорусовый упырь|Chorus Ghoul|Z|1.0|b080d0|1|HUNGER
end_stalker|Охотник Края|End Stalker|S|1.1|d8d890|2|BLIND
shulker_mite|Шалкерный клещ|Shulker Mite|P|0.9|b070b0|1|LEVITATE
star_phantom|Звёздный фантом|Star Phantom|S|1.0|c0d0ff|3|LEVITATE
obsidian_wraith|Обсидиановый призрак|Obsidian Wraith|S|1.3|1a1030|3|WITHER
rift_crawler|Пространственный ползун|Rift Crawler|P|1.2|6a30c0|2|TELEPORT
astral_knight|Астральный рыцарь|Astral Knight|Z|1.3|9fa0ff|3|WEAK
dim_weaver|Ткач измерений|Dimension Weaver|P|1.4|4a2a7a|3|SLOW,TELEPORT
cosmic_hulk|Космический исполин|Cosmic Hulk|Z|1.9|2a2a6a|4|KNOCKBACK
nebula_reaper|Жнец туманностей|Nebula Reaper|S|1.3|ff60c0|3|LIFESTEAL
endstone_golem|Голем из эндерняка|End Stone Golem|Z|1.6|e0e0a0|3|KNOCKBACK
void_leech|Пустотная пиявка|Void Leech|P|0.8|301050|1|LIFESTEAL
eclipse_stalker|Страж затмения|Eclipse Stalker|S|1.2|101020|3|BLIND,WITHER
warp_cultist|Культист искажения|Warp Cultist|Z|1.0|7040a0|2|TELEPORT
meteor_brute|Метеоритный громила|Meteor Brute|Z|1.5|6a5040|3|FIRE,KNOCKBACK
ender_weeper|Плакальщик Эндера|Ender Weeper|S|0.95|a0a0ff|1|SLOW
pulsar_spider|Пульсарный паук|Pulsar Spider|P|1.1|60ffd0|2|WEAK
quantum_ghoul|Квантовый гуль|Quantum Ghoul|Z|1.1|40e0ff|2|TELEPORT
abyss_herald|Вестник бездны|Abyss Herald|S|1.4|8000ff|4|WITHER,TELEPORT
gravity_warden|Страж гравитации|Gravity Warden|Z|1.4|5050d0|3|LEVITATE
oblivion_shade|Тень забвения|Oblivion Shade|S|1.0|202030|2|BLIND
draconic_spawn|Драконье отродье|Draconic Spawn|Z|1.2|a040d0|3|WITHER
singularity_crawler|Ползун сингулярности|Singularity Crawler|P|1.5|0a0020|4|LEVITATE,WITHER
crystal_seraph|Кристальный серафим|Crystal Seraph|S|1.5|ffd0ff|4|LIFESTEAL,LEVITATE
ender_berserk|Эндерский берсерк|Ender Berserk|Z|1.3|c04040|3|KNOCKBACK,TELEPORT
void_priest|Жрец Пустоты|Void Priest|S|1.15|6030a0|3|BLIND,TELEPORT
celestial_devourer|Небесный пожиратель|Celestial Devourer|P|1.7|ff90ff|4|LIFESTEAL,WITHER
cosmic_lurker|Космический затаившийся|Cosmic Lurker|Z|1.0|3050a0|2|SLOW
"""

# id, рус, eng, мир, модель, масштаб, цвет, hp, урон, скорость, способности, награды[(редкость, шанс)]
BOSSES = [
    ("tomb_warden", "Страж Забытых Курганов", "Warden of the Forgotten Barrows", "SKY", "SKELETON", 2.0, "8c8cff",
     300, 12, 0.26, ["WEAK", "KNOCKBACK"], [("COMMON", 1.0), ("RARE", 1.0), ("EPIC", 0.3)]),
    ("hargrot", "Король Подземелья Харгрот", "Hargrot, King of the Depths", "SKY", "ZOMBIE", 2.4, "2f5f2f",
     600, 16, 0.25, ["POISON", "LIFESTEAL"], [("RARE", 1.0), ("EPIC", 1.0), ("LEGENDARY", 1.0)]),
    ("cinder_reaper", "Пепельный Жнец", "Cinder Reaper", "EMBER", "SKELETON", 2.2, "ff5a1f",
     500, 18, 0.27, ["FIRE", "WITHER"], [("RARE", 1.0), ("EPIC", 1.0), ("LEGENDARY", 0.3)]),
    ("malzarat", "Владыка Бездны Малзарат", "Malzarat, Lord of the Abyss", "EMBER", "ZOMBIE", 2.8, "b01010",
     900, 22, 0.25, ["FIRE", "LIFESTEAL", "KNOCKBACK"], [("EPIC", 1.0), ("LEGENDARY", 1.0)]),
    ("void_keeper", "Хранитель Пустоты", "Keeper of the Void", "VOID", "SPIDER", 2.5, "7a3cff",
     700, 20, 0.32, ["TELEPORT", "BLIND"], [("EPIC", 1.0), ("LEGENDARY", 0.4)]),
    ("nulhar", "Предвечный Архонт Нулхар", "Nulhar, the Primordial Archon", "VOID", "SKELETON", 3.0, "c050ff",
     1000, 26, 0.27, ["TELEPORT", "LEVITATE", "WITHER"], [("EPIC", 1.0), ("LEGENDARY", 1.0), ("LEGENDARY", 0.5)]),
]

MODEL = {"Z": "ZOMBIE", "S": "SKELETON", "P": "SPIDER"}
mobs = []  # (id, ru, en, realm, model, scale, color, tier, abilities)
realm = None
for line in MOBS_RAW.strip().splitlines():
    if "|" not in line:
        realm = line.strip()
        continue
    i, ru, en, m, sc, col, tier, ab = line.split("|")
    mobs.append((i, ru, en, realm, MODEL[m], sc, col, int(tier), [a for a in ab.split(",") if a]))
assert len(mobs) == 90, len(mobs)

# ---- MobDefs.java
jl = []
for i, ru, en, r, m, sc, col, tier, ab in mobs:
    abil = "".join(f", Ability.{a}" for a in ab)
    jl.append(f'        reg(new MobDef("{i}", Realm.{r}, Model.{m}, {sc}f, 0x{col}, {tier}{abil}));')
for i, ru, en, r, m, sc, col, hp, dmg, spd, ab, rew in BOSSES:
    abil = ", ".join(f"Ability.{a}" for a in ab)
    rw = ", ".join(f"new MobDef.Reward(ChestTier.{c}, {p}f)" for c, p in rew)
    jl.append(f'        reg(MobDef.boss("{i}", Realm.{r}, Model.{m}, {sc}f, 0x{col}, {hp}, {dmg}, {spd}, '
              f'new Ability[]{{{abil}}}, {rw}));')
w(os.path.join(JAVA, "entity/MobDefs.java"), f"""package dev.realmlegends.entity;

import dev.realmlegends.ChestTier;
import dev.realmlegends.Realm;

import java.util.ArrayList;
import java.util.List;

/** АВТОГЕНЕРАЦИЯ: tools/generate.py. Не редактируйте вручную. */
public final class MobDefs {{
    public static final List<MobDef> ALL = new ArrayList<>();

    private MobDefs() {{ }}

    private static void reg(MobDef d) {{ ALL.add(d); }}

    static {{
{chr(10).join(jl)}
    }}
}}
""")

# ------------------------------------------------------------------ ЭКИПИРОВКА
TYPES = ["helmet", "chestplate", "leggings", "boots", "sword", "axe", "pickaxe", "shovel", "hoe"]
GEAR = {
    "sky": dict(
        ru=["Шлем Небесного Стража", "Нагрудник Небесного Стража", "Поножи Небесного Стража", "Ботинки Небесного Стража",
            "Клинок Рассвета", "Секира Грозового Фронта", "Кирка Небесного Свода", "Лопата Облачного Странника", "Мотыга Золотых Колосьев"],
        en=["Celestial Warden Helm", "Celestial Warden Chestplate", "Celestial Warden Leggings", "Celestial Warden Boots",
            "Blade of Dawn", "Stormfront Cleaver", "Pickaxe of the Heavens", "Cloudwalker's Shovel", "Hoe of Golden Ears"],
        dru=["Ночное зрение и подводное дыхание", "ПОЛЁТ и +2 сердца", "Прыгучесть II", "Плавное падение и скорость I",
             "Похищает здоровье, подсвечивает врага, шанс «небесной кары»", "Рассекает всех врагов вокруг цели",
             "Спешка II в руке", "Спешка II в руке", "Спешка II в руке"],
        den=["Night vision and water breathing", "FLIGHT and +2 hearts", "Jump Boost II", "Slow falling and Speed I",
             "Steals health, marks the foe, chance of heavenly smite", "Cleaves every enemy around the target",
             "Haste II in hand", "Haste II in hand", "Haste II in hand"],
        set_ru="Полный комплект: Регенерация I, Сопротивление I", set_en="Full set: Regeneration I, Resistance I"),
    "ember": dict(
        ru=["Шлем Преисподней", "Нагрудник Преисподней", "Поножи Преисподней", "Ботинки Преисподней",
            "Клинок Адского Пламени", "Секира Магмы", "Кирка Вулкана", "Лопата Пепла", "Мотыга Углей"],
        en=["Infernal Helm", "Infernal Chestplate", "Infernal Leggings", "Infernal Boots",
            "Hellfire Blade", "Magma Cleaver", "Volcano Pickaxe", "Ashen Shovel", "Hoe of Embers"],
        dru=["Ночное зрение и огнестойкость", "ПОЛЁТ и Сила I", "Огнестойкость и скорость I", "Скорость II",
             "Поджигает на 8 с и ослабляет врага", "Рассекает всех врагов вокруг цели",
             "Спешка II в руке", "Спешка II в руке", "Спешка II в руке"],
        den=["Night vision and fire resistance", "FLIGHT and Strength I", "Fire resistance and Speed I", "Speed II",
             "Ignites for 8s and weakens the foe", "Cleaves every enemy around the target",
             "Haste II in hand", "Haste II in hand", "Haste II in hand"],
        set_ru="Полный комплект: Сопротивление I, Регенерация I", set_en="Full set: Resistance I, Regeneration I"),
    "void": dict(
        ru=["Шлем Звёздного Странника", "Нагрудник Звёздного Странника", "Поножи Звёздного Странника", "Ботинки Звёздного Странника",
            "Меч Пустоты", "Секира Забвения", "Кирка Бесконечности", "Лопата Тёмной Материи", "Мотыга Края Мира"],
        en=["Starbound Helm", "Starbound Chestplate", "Starbound Leggings", "Starbound Boots",
            "Blade of the Void", "Cleaver of Oblivion", "Pickaxe of Infinity", "Dark Matter Shovel", "Hoe of the World's Edge"],
        dru=["Ночное зрение и подводное дыхание", "ПОЛЁТ и Сопротивление I", "Спешка II и Прыгучесть II", "Плавное падение и скорость II",
             "Левитация, иссушение и замедление врага", "Рассекает всех врагов вокруг цели",
             "Спешка II в руке", "Спешка II в руке", "Спешка II в руке"],
        den=["Night vision and water breathing", "FLIGHT and Resistance I", "Haste II and Jump Boost II", "Slow falling and Speed II",
             "Levitation, wither and slowness on the foe", "Cleaves every enemy around the target",
             "Haste II in hand", "Haste II in hand", "Haste II in hand"],
        set_ru="Полный комплект: Регенерация II, Прилив здоровья II", set_en="Full set: Regeneration II, Health Boost II"),
}

# ---- Палитры (b основа, e кромка, g отделка, h рукоять, G самоцвет, a акцент)
KEYS = "beghGa"
PAL = {
    "sky": ["e8f4ff", "8fb8e8", "f2c94c", "5a4632", "4fd8ff", "ffffff"],
    "ember": ["3a2a2a", "ff6a1a", "8a1c10", "2a1a14", "ffd02a", "ff3a00"],
    "void": ["2a1a4a", "a050ff", "5a3a8a", "1a1030", "ff60ff", "60ffe0"],
}
for r, cols in PAL.items():
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    for idx, c in enumerate(cols):
        img.putpixel((idx, 0), tuple(int(c[k:k + 2], 16) for k in (0, 2, 4)) + (255,))
    os.makedirs(os.path.join(ASSETS, "textures/item"), exist_ok=True)
    img.save(os.path.join(ASSETS, f"textures/item/palette_{r}.png"))

# ---- Воксельные 3D-формы (x вправо, y вверх, как у спрайтов 16x16)
def diag(c, key, a, b):
    for i in range(a, b + 1):
        c[(i, i)] = key


def shape_sword():
    c = {}
    c[(0, 0)] = "g"
    diag(c, "h", 1, 2)
    for k in range(-2, 3):
        c[(3 + k, 3 - k)] = "g"
    c[(3, 3)] = "G"
    for i in range(4, 15):
        c[(i, i)] = "b"
        if i < 14:
            c[(i + 1, i)] = "e"
            c[(i, i + 1)] = "e"
    c[(15, 15)] = "e"
    return c


def shape_pick():
    c = {}
    diag(c, "h", 0, 11)
    for k in range(-3, 4):
        c[(12 + k, 12 - k)] = "b"
    c[(8, 14)] = "e"; c[(14, 8)] = "e"; c[(7, 13)] = "e"; c[(13, 7)] = "e"
    c[(12, 12)] = "G"; c[(11, 11)] = "g"
    return c


def shape_axe():
    c = {}
    diag(c, "h", 0, 12)
    for p in [(11, 13), (12, 13), (13, 13), (12, 14), (13, 14), (14, 14), (13, 15), (14, 15), (14, 13), (15, 14), (15, 15), (11, 14), (10, 12)]:
        c[p] = "b"
    for p in [(15, 13), (15, 12), (14, 12)]:
        c[p] = "e"
    c[(12, 12)] = "G"
    return c


def shape_shovel():
    c = {}
    diag(c, "h", 0, 11)
    c[(12, 12)] = "g"
    for p in [(12, 13), (13, 12), (13, 13), (12, 14), (14, 12), (13, 14), (14, 13), (14, 14), (15, 14), (14, 15), (13, 15), (15, 13)]:
        c[p] = "b"
    for p in [(15, 15), (15, 12), (12, 15)]:
        c[p] = "e"
    c[(13, 13)] = "G"
    return c


def shape_hoe():
    c = {}
    diag(c, "h", 0, 11)
    c[(12, 12)] = "g"
    for p in [(9, 13), (10, 13), (11, 13), (12, 13), (13, 13), (14, 13)]:
        c[p] = "b"
    for p in [(9, 12), (9, 11), (14, 12)]:
        c[p] = "e"
    c[(12, 13)] = "G"
    return c


def ascii_shape(rows):
    c = {}
    for ry, row in enumerate(rows):
        for x, ch in enumerate(row.ljust(16, ".")):
            if ch != ".":
                c[(x, 15 - ry)] = ch
    return c


SHAPES = {
    "sword": shape_sword(), "pickaxe": shape_pick(), "axe": shape_axe(), "shovel": shape_shovel(), "hoe": shape_hoe(),
    "helmet": ascii_shape([
        "................", "....eeeeeeee....", "...ebbbbbbbbe...", "..ebbbbGGbbbbe..", "..ebbbbbbbbbbe..",
        "..ebbbbbbbbbbe..", "..egggggggggge..", "..eg........ge..", "..eg........ge..", "..eb........be..", "..ee........ee.."]),
    "chestplate": ascii_shape([
        "................", "..eee......eee..", ".ebbbe....ebbbe.", ".ebbbbeeeebbbbe.", ".ebbbbbbbbbbbbe.",
        ".ebbbbbGGbbbbbe.", "..ebbbbGGbbbbe..", "..ebbbbbbbbbbe..", "..egggggggggge..", "..ebbbbbbbbbbe..",
        "..ebbbbbbbbbbe..", "...eeeeeeeeee..."]),
    "leggings": ascii_shape([
        "..eeeeeeeeeeee..", "..egggggggggge..", "..ebbbbbbbbbbe..", "..ebbbbGGbbbbe..", "..ebbbbbbbbbbe..",
        "..ebbbe..ebbbe..", "..ebbbe..ebbbe..", "..ebbbe..ebbbe..", "..ebbbe..ebbbe..", "..eggge..eggge..",
        "..ebbbe..ebbbe..", "..ebbbe..ebbbe..", "..eeee...eeee..."]),
    "boots": ascii_shape([
        "................", "................", "..eeee....eeee..", "..ebbe....ebbe..", "..ebGe....eGbe..",
        "..ebbe....ebbe..", "..ebbe....ebbe..", "..ebbbbe..ebbbbe", "..egggge..egggge", "..eeeeee..eeeeee"]),
}
DEPTH = {"b": 1.5, "e": 1.0, "g": 1.5, "h": 1.0, "G": 2.6, "a": 1.2}
FACES = ("north", "south", "east", "west", "up", "down")

HANDHELD = {
    "thirdperson_righthand": {"rotation": [0, -90, 55], "translation": [0, 4.0, 0.5], "scale": [0.85, 0.85, 0.85]},
    "thirdperson_lefthand": {"rotation": [0, 90, -55], "translation": [0, 4.0, 0.5], "scale": [0.85, 0.85, 0.85]},
    "firstperson_righthand": {"rotation": [0, -90, 25], "translation": [1.13, 3.2, 1.13], "scale": [0.68, 0.68, 0.68]},
    "firstperson_lefthand": {"rotation": [0, 90, -25], "translation": [1.13, 3.2, 1.13], "scale": [0.68, 0.68, 0.68]},
    "gui": {"rotation": [20, -30, 0], "translation": [0, 0, 0], "scale": [0.95, 0.95, 0.95]},
    "ground": {"rotation": [0, 0, 0], "translation": [0, 2, 0], "scale": [0.5, 0.5, 0.5]},
    "fixed": {"rotation": [0, 180, 0], "translation": [0, 0, 0], "scale": [1, 1, 1]},
}
GENERATED = {
    "thirdperson_righthand": {"rotation": [0, 0, 0], "translation": [0, 3, 1], "scale": [0.55, 0.55, 0.55]},
    "thirdperson_lefthand": {"rotation": [0, 0, 0], "translation": [0, 3, 1], "scale": [0.55, 0.55, 0.55]},
    "firstperson_righthand": {"rotation": [0, -90, 25], "translation": [1.13, 3.2, 1.13], "scale": [0.68, 0.68, 0.68]},
    "firstperson_lefthand": {"rotation": [0, 90, -25], "translation": [1.13, 3.2, 1.13], "scale": [0.68, 0.68, 0.68]},
    "gui": {"rotation": [15, -25, 0], "translation": [0, 0, 0], "scale": [0.95, 0.95, 0.95]},
    "head": {"rotation": [0, 180, 0], "translation": [0, 13, 7], "scale": [1, 1, 1]},
    "ground": {"rotation": [0, 0, 0], "translation": [0, 2, 0], "scale": [0.5, 0.5, 0.5]},
    "fixed": {"rotation": [0, 180, 0], "translation": [0, 0, 0], "scale": [1, 1, 1]},
}


def elements(cells):
    els = []
    for y in range(16):
        x = 0
        while x < 16:
            k = cells.get((x, y))
            if not k:
                x += 1
                continue
            x2 = x
            while cells.get((x2 + 1, y)) == k and x2 < 15:
                x2 += 1
            d = DEPTH[k]
            i = KEYS.index(k)
            uv = [i, 0, i + 1, 1]
            els.append({"from": [x, y, round(8 - d, 2)], "to": [x2 + 1, y + 1, round(8 + d, 2)],
                        "faces": {f: {"uv": uv, "texture": "#0"} for f in FACES}})
            x = x2 + 1
    return els


for r in GEAR:
    for t in TYPES:
        tool = t in ("sword", "axe", "pickaxe", "shovel", "hoe")
        wj(os.path.join(ASSETS, f"models/item/{r}_{t}.json"), {
            "textures": {"0": f"{MOD}:item/palette_{r}", "particle": f"{MOD}:item/palette_{r}"},
            "elements": elements(SHAPES[t]),
            "display": HANDHELD if tool else GENERATED,
            "gui_light": "front",
        })

for i, *_ in mobs:
    wj(os.path.join(ASSETS, f"models/item/{i}_spawn_egg.json"), {"parent": "minecraft:item/template_spawn_egg"})
for b in BOSSES:
    wj(os.path.join(ASSETS, f"models/item/{b[0]}_spawn_egg.json"), {"parent": "minecraft:item/template_spawn_egg"})

# ---- Текстуры брони (64x64, на них же UV для 3D-надстроек)
ARMOR_PAL = {
    "sky": ((225, 238, 250), (242, 201, 76), (79, 216, 255)),
    "ember": ((52, 36, 36), (255, 106, 26), (255, 208, 42)),
    "void": ((38, 24, 70), (160, 80, 255), (255, 96, 255)),
}
for r, (base, accent, glow) in ARMOR_PAL.items():
    img = Image.new("RGBA", (64, 64))
    px = img.load()
    for y in range(64):
        for x in range(64):
            n = random.randint(-14, 14)
            c = [max(0, min(255, v + n)) for v in base]
            if x % 8 == 0 or y % 8 == 0:
                c = [int(v * 0.65) for v in c]            # «швы» пластин
            elif (x * 3 + y * 2) % 13 == 0:
                c = list(accent)                           # золотые/огненные прожилки
            elif (x * 7 + y * 5) % 47 == 0:
                c = list(glow)                             # светящиеся точки
            px[x, y] = (c[0], c[1], c[2], 255)
    img.save(os.path.join(ASSETS, f"textures/models/armor/{r}.png"))

# ------------------------------------------------------------------ ЯЗЫКИ
ru = {"itemGroup.realmlegends.main": "Realm Legends",
      "chest.realmlegends.common": "Обычный сундук", "chest.realmlegends.rare": "Редкий сундук",
      "chest.realmlegends.epic": "Эпический сундук", "chest.realmlegends.legendary": "Легендарный сундук"}
en = {"itemGroup.realmlegends.main": "Realm Legends",
      "chest.realmlegends.common": "Common Chest", "chest.realmlegends.rare": "Rare Chest",
      "chest.realmlegends.epic": "Epic Chest", "chest.realmlegends.legendary": "Legendary Chest"}
for i, r_, e_, *_ in mobs:
    ru[f"entity.{MOD}.{i}"] = r_; en[f"entity.{MOD}.{i}"] = e_
    ru[f"item.{MOD}.{i}_spawn_egg"] = f"Яйцо призыва: {r_}"; en[f"item.{MOD}.{i}_spawn_egg"] = f"{e_} Spawn Egg"
for b in BOSSES:
    ru[f"entity.{MOD}.{b[0]}"] = b[1]; en[f"entity.{MOD}.{b[0]}"] = b[2]
    ru[f"item.{MOD}.{b[0]}_spawn_egg"] = f"Яйцо призыва: {b[1]}"; en[f"item.{MOD}.{b[0]}_spawn_egg"] = f"{b[2]} Spawn Egg"
for r, g in GEAR.items():
    for idx, t in enumerate(TYPES):
        k = f"item.{MOD}.{r}_{t}"
        ru[k] = g["ru"][idx]; en[k] = g["en"][idx]
        ru[k + ".desc"] = g["dru"][idx]; en[k + ".desc"] = g["den"][idx]
        if idx < 4:
            ru[k + ".desc2"] = g["set_ru"]; en[k + ".desc2"] = g["set_en"]
wj(os.path.join(ASSETS, "lang/ru_ru.json"), ru)
wj(os.path.join(ASSETS, "lang/en_us.json"), en)

# ------------------------------------------------------------------ ЛУТ
def item(name, weight, lo=None, hi=None, ench=None):
    e = {"type": "minecraft:item", "name": name, "weight": weight, "functions": []}
    if lo is not None:
        e["functions"].append({"function": "minecraft:set_count", "count": {"min": lo, "max": hi}})
    if ench:
        e["functions"].append({"function": "minecraft:enchant_with_levels", "levels": {"min": ench, "max": ench + 8}, "treasure": True})
    if not e["functions"]:
        del e["functions"]
    return e


def pool(rolls_lo, rolls_hi, entries, cond=None):
    p = {"rolls": {"min": rolls_lo, "max": rolls_hi}, "entries": entries}
    if cond:
        p["conditions"] = cond
    return p


def chest(pools):
    return {"type": "minecraft:chest", "pools": pools}


M = "minecraft:"
wj(os.path.join(DATA, "loot_tables/chests/common.json"), chest([pool(3, 5, [
    item(M + "bread", 10, 2, 5), item(M + "cooked_beef", 8, 2, 4), item(M + "coal", 10, 4, 10),
    item(M + "iron_ingot", 10, 2, 5), item(M + "gold_ingot", 6, 1, 3), item(M + "torch", 8, 8, 16),
    item(M + "arrow", 6, 8, 16), item(M + "emerald", 4, 1, 2), item(M + "experience_bottle", 3, 1, 3)])]))
wj(os.path.join(DATA, "loot_tables/chests/rare.json"), chest([pool(4, 6, [
    item(M + "iron_ingot", 8, 4, 8), item(M + "gold_ingot", 8, 3, 6), item(M + "diamond", 6, 1, 3),
    item(M + "emerald", 6, 3, 6), item(M + "lapis_lazuli", 6, 6, 12), item(M + "golden_apple", 4, 1, 1),
    item(M + "experience_bottle", 6, 3, 6), item(M + "ender_pearl", 4, 1, 3), item(M + "blaze_rod", 3, 1, 2),
    item(M + "book", 5, ench=20)])]))
wj(os.path.join(DATA, "loot_tables/chests/epic.json"), chest([pool(5, 7, [
    item(M + "diamond", 8, 3, 6), item(M + "netherite_scrap", 5, 1, 3), item(M + "enchanted_golden_apple", 3, 1, 1),
    item(M + "experience_bottle", 8, 8, 16), item(M + "book", 8, ench=30), item(M + "emerald", 6, 8, 16),
    item(M + "totem_of_undying", 2, 1, 1), item(M + "ender_pearl", 5, 3, 6), item(M + "diamond_block", 2, 1, 1),
    item(M + "netherite_ingot", 2, 1, 1), item(M + "name_tag", 3, 1, 1)])]))
THEME = {
    "sky": [item(M + "golden_carrot", 6, 8, 16), item(M + "amethyst_shard", 6, 8, 16), item(M + "heart_of_the_sea", 2, 1, 1)],
    "ember": [item(M + "blaze_rod", 6, 4, 8), item(M + "ancient_debris", 6, 2, 4), item(M + "magma_cream", 5, 6, 12)],
    "void": [item(M + "shulker_shell", 6, 2, 4), item(M + "ender_eye", 6, 4, 8), item(M + "end_crystal", 2, 1, 2)],
}
for r in GEAR:
    gear = [item(f"{MOD}:{r}_{t}", 10, 1, 1) for t in TYPES]
    common = [item(M + "netherite_ingot", 6, 1, 3), item(M + "diamond", 8, 6, 12),
              item(M + "enchanted_golden_apple", 4, 1, 2), item(M + "totem_of_undying", 3, 1, 1),
              item(M + "experience_bottle", 8, 16, 32), item(M + "book", 8, ench=30),
              item(M + "emerald_block", 3, 1, 3), item(M + "nether_star", 2, 1, 1)]
    wj(os.path.join(DATA, f"loot_tables/chests/legendary_{r}.json"),
       chest([pool(2, 3, gear), pool(4, 6, common + THEME[r])]))

# лут с мобов
MOB_DROPS = {
    "SKY": ([M + "bone", M + "rotten_flesh", M + "string"], [M + "iron_ingot", M + "gold_ingot", M + "emerald", M + "diamond"]),
    "EMBER": ([M + "blaze_powder", M + "gold_nugget", M + "nether_wart"], [M + "gold_ingot", M + "quartz", M + "blaze_rod", M + "netherite_scrap"]),
    "VOID": ([M + "ender_pearl", M + "chorus_fruit", M + "string"], [M + "diamond", M + "ender_eye", M + "shulker_shell", M + "emerald"]),
}
for i, ru_, en_, r_, m_, sc, col, tier, ab in mobs:
    common, rare = MOB_DROPS[r_]
    loot_fn = [{"function": "minecraft:set_count", "count": {"min": 0, "max": 2}},
               {"function": "minecraft:looting_enchant", "count": {"min": 0, "max": 1}}]
    p1 = {"rolls": 2, "entries": [{"type": "minecraft:item", "name": n, "weight": 1, "functions": loot_fn} for n in common]}
    p2 = {"rolls": 1, "conditions": [
        {"condition": "minecraft:killed_by_player"},
        {"condition": "minecraft:random_chance_with_looting", "chance": 0.08 + 0.05 * tier, "looting_multiplier": 0.03}],
        "entries": [{"type": "minecraft:item", "name": n, "weight": 1,
                     "functions": [{"function": "minecraft:set_count", "count": {"min": 1, "max": tier}}]} for n in rare]}
    wj(os.path.join(DATA, f"loot_tables/entities/{i}.json"), {"type": "minecraft:entity", "pools": [p1, p2]})

print("Мобов:", len(mobs), "| боссов:", len(BOSSES), "| предметов экипировки:", len(GEAR) * len(TYPES))
