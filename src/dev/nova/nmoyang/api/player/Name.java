package dev.nova.nmoyang.api.player;

import com.sun.istack.internal.Nullable;

public class Name {

    private final long changedAt;
    private final String name;

    /**
     *
     * Creates a name for name history purposes.
     *
     * @param name The name string.
     * @param changedAt The time it got changed. (When null it means that is the inital name, Mojang won't record this because it would be the same time the account got bought)
     */
    public Name(String name, @Nullable long changedAt){
        this.name = name;
        this.changedAt = changedAt;
    }

    public String getName() {
        return name;
    }

    public long getChangedAt() {
        return changedAt;
    }
}
