package dev.nova.nmoyang.api.player;

import java.net.URL;

public class Skin {

    private final URL skinURL;
    private final boolean isSlim;

    /**
     *
     * A class that has URL to the skin image and if the skin is slim.
     *
     * @param skinURL The URL to the skin image.
     * @param isSlim The skin is slim or not.
     *
     * @see Profile
     */
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
