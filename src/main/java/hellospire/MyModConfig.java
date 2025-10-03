package hellospire;

import basemod.EasyConfigPanel;
import com.evacipated.cardcrawl.modthespire.Loader;

public class MyModConfig extends EasyConfigPanel {
    public static boolean enableBoss2Music = true;
    public static boolean enableBoss3Music = true;
    public static boolean enableBossSpearShieldMusic = true;
    public static boolean enableBossHeartMusic = true;
    public static boolean enableClassicMode = false;
    public static boolean enableEventsForAllCharacters = false;
    public static boolean enableEventsForOnlySonic = true;
    public static boolean enableTextPopUps = true;
    public static boolean enableThreeOrbs = false;
    public static boolean enableSkinCaptain = true;
    public static boolean enableSound = true;
    public static boolean enableVoice = true;
    public static int voiceFrequency = Loader.isModLoaded("spireTogether") ? 5 : 10;
    public static int optionStarterRelic = 0;

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
        setNumberRange("optionStarterRelic", 0, 6);
    }
}