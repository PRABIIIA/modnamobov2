package dev.realmlegends.item;

import dev.realmlegends.Realm;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class RealmArmorItem extends ArmorItem {
    public final Realm realm;

    public RealmArmorItem(Realm realm, ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
        this.realm = realm;
    }
}
