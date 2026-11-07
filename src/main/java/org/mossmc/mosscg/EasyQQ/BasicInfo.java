package org.mossmc.mosscg.EasyQQ;

import org.mossmc.mosscg.MossLib.Object.ObjectConfig;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;

public class BasicInfo {
    public static String version = "V0.1.0.0.1727";
    public static String author = "MossCG";

    public static ObjectLogger logger;
    public static ObjectConfig config;

    public static boolean debug = false;
    public static void sendDebug(String message) {
        if (debug) logger.sendAPI(message);
    }
}
