package dev.realmlegends;

import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ChestTier {
    COMMON(Formatting.GRAY), RARE(Formatting.BLUE), EPIC(Formatting.DARK_PURPLE), LEGENDARY(Formatting.GOLD);

    public final Formatting color;

    ChestTier(Formatting color) { this.color = color; }

    public Identifier lootTable(Realm realm) {
        String n = this == LEGENDARY ? "legendary_" + realm.key : name().toLowerCase(Locale.ROOT);
        return RealmLegends.id("chests/" + n);
    }

    public String nameKey() {
        return "chest." + RealmLegends.MOD_ID + "." + name().toLowerCase(Locale.ROOT);
    }
}
