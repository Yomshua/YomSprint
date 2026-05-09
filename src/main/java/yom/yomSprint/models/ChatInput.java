package yom.yomSprint.models;

import org.bukkit.Location;

import java.util.UUID;

public class ChatInput {

    private UUID uuid;
    private Track track;
    private Location location;

    public ChatInput(UUID uuid, Track track,Location location) {
        this.uuid = uuid;
        this.track = track;
        this.location = location;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Track getTrack() {
        return track;
    }

    public Location getLocation() {
        return location;
    }
}
