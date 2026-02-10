package theHedgehog.character;

import theHedgehog.SonicMod;

import java.io.IOException;
import java.util.HashMap;

import static theHedgehog.SonicMod.makeID;

public class SonicTipTracker {
    public static final String Pg01_intro = makeID("Pg01_intro");
    public static final String Version092 = makeID("Version092");
    public static final String Version098 = makeID("Version098");
    public static final String HasUnlockedCaptainSonic = makeID("HasUnlockedCaptainSonic");

    public static HashMap<String, Boolean> tips = new HashMap<>();

    public static void initialize() {
        tips.put(Pg01_intro, SonicMod.sonicmodConfig.getBool(Pg01_intro));
        tips.put(Version092, SonicMod.sonicmodConfig.getBool(Version092));
        tips.put(Version098, SonicMod.sonicmodConfig.getBool(Version098));
        tips.put(HasUnlockedCaptainSonic, SonicMod.sonicmodConfig.getBool(HasUnlockedCaptainSonic));
    }

    public static void reset(){
        tips.put(Pg01_intro, false);
    }

    public static boolean hasShown(String tip) {
        return tips.getOrDefault(tip, false);
    }

    public static void neverShowAgain(String tip) {
        SonicMod.logger.info("Never showing again: " + tip);
        SonicMod.sonicmodConfig.setBool(tip, true);
        tips.put(tip, Boolean.valueOf(true));
        try {
            SonicMod.sonicmodConfig.save();
        } catch (IOException ex) {
            SonicMod.logger.catching(ex);
        }
    }
}
