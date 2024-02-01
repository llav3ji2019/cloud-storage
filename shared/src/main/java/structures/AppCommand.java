package structures;

import java.util.Arrays;

public enum AppCommand {
    LOGIN,
    DOWNLOAD,
    SAVE,
    MOVE,
    RESPONSE;

    public static boolean contains(AppCommand cmd) {
        return Arrays.asList(AppCommand.values()).contains(cmd);
    }
}
