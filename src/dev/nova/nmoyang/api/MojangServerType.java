package dev.nova.nmoyang.api;

public enum MojangServerType {

    MINECRAFT_DOT_NET(0),
    SESSION(1),
    ACCOUNT(2),
    AUTHENTICATION_SERVER(3),
    SESSION_SERVER(4),
    API(5),
    TEXTURES(6),
    MOJANG_DOT_COM(7);


    private final int index;

    MojangServerType(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }
}
