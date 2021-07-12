package dev.nova.nmoyang.api.player;

import javax.annotation.Nullable;
import java.net.URL;

public class Profile {

    private final String profileID;

    @Nullable
    private final URL cape;
    private final Skin skin;

    /**
     *
     * Creates a profile instance that contains all surface information about a user.
     *
     * @param profileID The ID of the profile. (Because Mojang Studios is planning on adding multiple profiles)
     * @param skin The skin of the user.
     * @param cape A URL to the cape image. (Null when the user has no cape)
     *
     * @see Skin
     */
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
