package dev.realmlegends;

/** Три мира: SKY = Обычный мир, EMBER = Незер, VOID = Энд. */
public enum Realm {
    SKY("sky"), EMBER("ember"), VOID("void");

    public final String key;

    Realm(String key) { this.key = key; }
}
