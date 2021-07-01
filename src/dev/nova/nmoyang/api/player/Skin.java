package dev.nova.nmoyang.api.player;

import java.net.URL;

public class Skin {

    private final URL skinURL;
    private final boolean isSlim;

    public Skin(URL skinURL, boolean isSlim) {
        this.skinURL = skinURL;
        this.isSlim = isSlim;
    }

    public boolean isSlim() {
        return isSlim;
    }

    public URL getSkinURL() {
        return skinURL;
    }
}
