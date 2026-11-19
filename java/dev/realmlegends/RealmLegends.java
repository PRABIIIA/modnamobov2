package dev.realmlegends;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class RealmLegends implements ModInitializer {
    public static final String MOD_ID = "realmlegends";

    public static Identifier id(String path) { return new Identifier(MOD_ID, path); }

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
        RealmEffects.register();
    }
}
