package hellospire;

import basemod.EasyConfigPanel;

public class MyModConfig extends EasyConfigPanel {
    public static boolean enableSound = true;
    public static boolean enableVoice = true;
    public static boolean enableKicksForStrikeDummy = true;
    public static int voiceFrequency = 5;
//    public enum Voice {
//        English,
//        Japanese,
//        None
//    }
//    public static Voice languageVoice = Voice.None;
//    public static String nameSomething = "foo";
//    public static int setSomething = 5;

    public MyModConfig() {
        super(SonicMod.modID, SonicMod.makeID("MyModConfig"));
        setNumberRange("voiceFrequency", 0, 10);
    }
}