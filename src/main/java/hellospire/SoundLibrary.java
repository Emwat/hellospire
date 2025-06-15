package hellospire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class SoundLibrary {

    static public String Amazing1 = makeID("ogg_AMAZING");
    static public String Amazing2 = makeID("ogg_OUTSTANDING");
//    static public String Amazing3 = makeID("ogg_GREAT");
//    static public String Amazing4 = makeID("ogg_GOOD");
    static public String ALLRIGHT = makeID("ALLRIGHT");
    static public String COOL = makeID("ogg_COOL");
    static public String OK = makeID("ogg_OK");
    static public String OW = makeID("ogg_OW");
    static public String YES = makeID("ogg_YES");
    static public String FeelingGood = makeID("ogg_FeelingGood");

    static public String QuickAir1 = makeID("ogg_QuickAir1");
    static public String QuickAir2 = makeID("ogg_QuickAir2");
    static public String QuickAir3 = makeID("ogg_QuickAir3");

    static public String BlueTornado = makeID("ogg_BlueTornado");
    static public String Booster = makeID("ogg_Booster");
    static public String Spring = makeID("ogg_Spring");
    static public String LevelUp = makeID("ogg_LevelUp");
    static public String Ring = makeID("ogg_Ring");

    static public String Attack1 = makeID("ogg_attack1");
    static public String Attack2 = makeID("ogg_attack2");
    static public String Attack3 = makeID("ogg_attack3");
    static public String Attack4 = makeID("ogg_attack4");
    static public String Attack5 = makeID("ogg_attack5");
    static public String Attack6 = makeID("ogg_attack6");
    static public String SonicStyle = makeID("ogg_SonicStyle");

    static public String LightningShield = makeID("ogg_LightningShield");
    static public String Jump = makeID("ogg_Jump");
    static public String StarPost = makeID("ogg_StarPost");
    static public String DropDash = makeID("ogg_DropDash");
    static public String BlankSound = makeID("BlankSound");

    public static String OmochaoPerfectLanding = makeID("ogg_OmochaoPerfectLanding");
    public static String OmochaoIncorrectLanding = makeID("ogg_OmochaoIncorrectLanding");
    public static String OmochaoTurbulence = makeID("ogg_OmochaoTurbulence");

    public static String SonicsTheName = makeID("ogg_SonicsTheName");
    public static String TooEasy = makeID("ogg_TooEasy");
    public static String HeyWeShould = makeID("ogg_HeyWeShould");
    public static String WindUpPunchGo = makeID("ogg_WindUpPunchGo");

    public static String Nooo = makeID("ogg_Nooo");
    public static String Dead = makeID("ogg_Dead");
    public static String LongLiveTheEggmanEmpire = makeID("ogg_HailEggmanEmpire");
    public static String SpeedBreak = makeID("ogg_SpeedBreak");
    public static String TimeBreak = makeID("ogg_TimeBreak");

    public static String BossMusic = makeID("mp3_WindUpPunchGo");

    public static String Tails = makeID("ogg_tails");
    public static String Knuckles = makeID("ogg_knux");
    public static String Cream = makeID("ogg_cream");
    public static String Amy = makeID("ogg_amy");
    public static String CuteCouple = makeID("ogg_cutecouple");
    public static String Big = makeID("ogg_big");
    public static String Blaze  = makeID("ogg_blaze");;

    static public int randomNumber = -1;

    static public SFXAction PlaySound(String key){
        if (MyModConfig.enableSound) {
            return new SFXAction(key);
        }
        return new SFXAction(BlankSound);
    }

    static public SFXAction PlayVoice(String key){
        int maxVoiceFrequency = 10;
        if (MyModConfig.enableVoice) {
            if (AbstractDungeon.miscRng.random(0, maxVoiceFrequency) - MyModConfig.voiceFrequency <= 0){
                return new SFXAction(key);
            }
        }

        return new SFXAction(BlankSound);
    }

    static public SFXAction PlayRandomSound(ArrayList<String> sounds) {
        if (!MyModConfig.enableSound) {
            return new SFXAction(BlankSound);
        }

        return new SFXAction(GetRandomClip(sounds));
    }

    static public SFXAction PlayRandomVoice(ArrayList<String> sounds) {
        if (!MyModConfig.enableVoice) {
            return new SFXAction(BlankSound);
        }

        return new SFXAction(GetRandomClip(sounds));
    }

    /// Used for CardCrawlGame.sound.play()
    /// Which I'm guessing is for outside combat (for example: com.megacrit.cardcrawl.events.exordium.ScrapOoze)
    static public String GetRandomVoice(ArrayList<String> sounds) {
        if (!MyModConfig.enableVoice) {
            return BlankSound;
        }

        return GetRandomClip(sounds);
    }

    private static String GetRandomClip(ArrayList<String> sounds) {
        int generatedRandomNumber = AbstractDungeon.miscRng.random(0, sounds.size() - 1);

        if (sounds.size() < 2) {
            randomNumber = generatedRandomNumber;
        } else {
            while (generatedRandomNumber == randomNumber) {
                generatedRandomNumber = AbstractDungeon.miscRng.random(0, sounds.size() - 1);
            }
            randomNumber = generatedRandomNumber;
        }

        return sounds.get(randomNumber);
    }

    public static AbstractGameAction AlwaysPlayVoice(String key) {
        return new SFXAction(key);
    }
}

//        CardCrawlGame.sound.playA(hellospire.SonicMod.makeID("AOUTSTANDING"), MathUtils.random(-0.2F, 0.2F));
