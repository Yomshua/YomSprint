package yom.yomSprint.utils;

import yom.yomSprint.models.Track;

public class PlacheholderReplace {

    public static String apply(String line, Track track) {
        return line
                .replace("<track_name>", track.getName())
                .replace("<track_minSize>", String.valueOf(track.getMinPlayers()))
                .replace("<track_maxSize>", String.valueOf(track.getMaxPlayers()))
                .replace("<track_players>",String.valueOf(track.getWaitLobbySize()));
    }

}
