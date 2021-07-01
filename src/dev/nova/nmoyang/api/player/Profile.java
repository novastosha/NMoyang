package dev.nova.nmoyang.api.player;

import javax.annotation.Nullable;
import java.net.URL;

public class Profile {

    private final String profileID;

    @Nullable
    private final URL cape;
    private final Skin skin;

    public Profile(String profileID, Skin skin, @Nullable URL cape) {
        this.profileID = profileID;
        this.skin = skin;
        this.cape = cape;
    }


    public Skin getSkin() {
        return skin;
    }

    public String getProfileID() {
        return profileID;
    }

    @Nullable
    public URL getCape() {
        return cape;
    }
}
