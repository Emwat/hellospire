package theHedgehog;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

public class SoundLibrary {

    static public final String LetsBlastThrough = makeID("ogg_LetsBlastThrough");
    static public final String LoseRings = makeID("ogg_LoseRings");

    static public final String Amazing1 = makeID("ogg_AMAZING");
    static public final String Amazing2 = makeID("ogg_OUTSTANDING");
    //    static public final String Amazing3 = makeID("ogg_GREAT");
//    static public final String Amazing4 = makeID("ogg_GOOD");
    static public final String ALLRIGHT = makeID("ogg_ALLRIGHT");
    static public final String COOL = makeID("ogg_COOL");
    static public final String OK = makeID("ogg_OK");
    static public final String OW = makeID("ogg_OW");
    static public final String YES = makeID("ogg_YES");
    static public final String FeelingGood = makeID("ogg_FeelingGood");
    static public final String SmallAllRight = makeID("ogg_SmallAllRight");
    static public final String SmallAllRightLetsGo = makeID("ogg_SmallAllRightLetsGo");
    static public final String SmallYahoo = makeID("ogg_SmallYahoo");
    static public final String SmallYes = makeID("ogg_SmallYes");
    static public final String Hehe = makeID("ogg_Hehe");
    static public final String ThatsIt = makeID("ogg_ThatsIt");
    static public final String BlastAway = makeID("ogg_BlastAway");

    static public final String Bingo = makeID("ogg_Bingo");
    static public final String PerfectBingo = makeID("ogg_PerfectBingo");

    static public final String QuickAir1 = makeID("ogg_QuickAir1");
    static public final String QuickAir2 = makeID("ogg_QuickAir2");
    static public final String QuickAir3 = makeID("ogg_QuickAir3");

    static public final String BlueTornado = makeID("ogg_BlueTornado");
    static public final String Booster = makeID("ogg_Booster");
    static public final String Spring = makeID("ogg_Spring");
    static public final String LevelUp = makeID("ogg_LevelUp");
    static public final String Ring = makeID("ogg_Ring");

    static public final String Attack1 = makeID("ogg_attack1");
    static public final String Attack2 = makeID("ogg_attack2");
    static public final String Attack3 = makeID("ogg_attack3");
    static public final String Attack4 = makeID("ogg_attack4"); // sharp_whistle
    static public final String Attack5Go = makeID("ogg_attack5");
    static public final String Attack6 = makeID("ogg_attack6");
    static public final String Attack7 = makeID("ogg_attack7");
    static public final String YESSS = makeID("ogg_YESSS");

    static public final String SonicStyle = makeID("ogg_SonicStyle");

    static public final String LightningShield = makeID("ogg_LightningShield");
    static public final String StarPost = makeID("ogg_StarPost");
    static public final String DropDash = makeID("ogg_DropDash");
    static public final String Roll = makeID("ogg_Roll");
    static public final String BlankSound = makeID("BlankSound");

    static public final String OmochaoPerfectLanding = makeID("ogg_OmochaoPerfectLanding");
    static public final String OmochaoIncorrectLanding = makeID("ogg_OmochaoIncorrectLanding");
    static public final String OmochaoTurbulence = makeID("ogg_OmochaoTurbulence");

    static public final String SonicsTheName = makeID("ogg_SonicsTheName");
    static public final String TooEasy = makeID("ogg_TooEasy");
    static public final String HeyWeShould = makeID("ogg_HeyWeShould");

    static public final String CatchMeIfYouCan = makeID("ogg_CatchMeIfYouCan");
    static public final String WhatsTheMatter = makeID("ogg_WhatsTheMatter");
    static public final String StepItUp = makeID("ogg_stepitup");
    static public final String TooSlow = makeID("ogg_tooslow");
    static public final String NeverUnderestimate = makeID("ogg_NeverUnderestimate");

    static public final String ImSonic = makeID("ogg_ImSonicTheHedgehog");

    static public final String Shoot = makeID("ogg_Shoot");
    static public final String Nooo = makeID("ogg_Nooo");
    static public final String Dead = makeID("ogg_Dead");
    static public final String LongLiveTheEggmanEmpire = makeID("ogg_HailEggmanEmpire");
    static public final String SpeedBreak = makeID("ogg_SpeedBreak");
    static public final String TimeBreak = makeID("ogg_TimeBreak");

    static public final String Amy = makeID("ogg_amy");
    static public final String Big = makeID("ogg_big");
    static public final String Blaze = makeID("ogg_blaze");
    public static final String Chao = makeID("ogg_chao");

    static public final String Charmy = makeID("ogg_charmy");
    public static final String Chip = makeID("ogg_chip");
    static public final String CuteCouple = makeID("ogg_cutecouple");
    static public final String Cream = makeID("ogg_cream");
    static public final String Espio = makeID("ogg_espio");
    static public final String Jet = makeID("ogg_jet");
    static public final String JetSneeze = makeID("ogg_jet_sneeze");
    static public final String JetWhat = makeID("ogg_jet_what");
    static public final String Knuckles = makeID("ogg_knux");
    public static final String Sticks = makeID("ogg_sticks");

    public static final String Shadow = makeID("ogg_shadow");

    public static final String Silver = makeID("ogg_silver");
    public static final String Rouge = makeID("ogg_rouge");

    static public final String Tails = makeID("ogg_tails");
    static public final String Vector = makeID("ogg_vector");

    public static String MetalHaha = makeID("ogg_metal_haha");
    public static String MetalData = makeID("ogg_metal_data");
    public static String MetalAppropriate = makeID("ogg_metal_appropriate");

    static public final String Boost = makeID("ogg_boost");
    static public final String Jump = makeID("ogg_jump");
    static public final String Rail = makeID("ogg_rail");
    static public final String SonicBoom = makeID("ogg_sonic_boom");
    static public final String Trick = makeID("ogg_trick_press");
    static public final String TrickOK = makeID("ogg_trick_ok");

    static public int randomNumber = -1;


    static public SFXAction SoundAction(String key) {
        if (MyModConfig.enableSound) {
            return new SFXAction(key);
        }
        return new SFXAction(BlankSound);
    }

    static public SFXAction VoiceAction(String key) {
        if (!(AbstractDungeon.player instanceof Sonic)) {
            return new SFXAction(BlankSound);
        }

        if (!Sonic.currentModSkin.getName().contains("Sonic")) {
            return new SFXAction(BlankSound);
        }

        if (MyModConfig.enableVoice) {
            if (isRandomlyTrue()) {
                return new SFXAction(key);
            }
        }

        return new SFXAction(BlankSound);
    }

    static public SFXAction RandomSoundAction(ArrayList<String> sounds) {
        if (!MyModConfig.enableSound) {
            return new SFXAction(BlankSound);
        }

        return new SFXAction(GetRandomClip(sounds));
    }

    static public SFXAction RandomVoiceAction(ArrayList<String> voices) {
        if (!MyModConfig.enableVoice) {
            return new SFXAction(BlankSound);
        }

        if (!(AbstractDungeon.player instanceof Sonic)) {
            return new SFXAction(BlankSound);
        }

        if (isRandomlyTrue()) {
            return new SFXAction(GetRandomClip(voices));
        }

        return new SFXAction(BlankSound);
    }

    /// Used for CardCrawlGame.sound.play()
    /// Which I'm guessing is for outside combat (for example: com.megacrit.cardcrawl.events.exordium.ScrapOoze)
    static public String GetRandomVoice(ArrayList<String> sounds) {
        if (!(AbstractDungeon.player instanceof Sonic)) {
            return BlankSound;
        }

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
            int tries = 0;
            int maxTries = 99;
            while (generatedRandomNumber == randomNumber) {
                tries++;
                if (tries >= maxTries) {
                    break;
                }
                generatedRandomNumber = AbstractDungeon.miscRng.random(0, sounds.size() - 1);
            }
            randomNumber = generatedRandomNumber;
        }

        return sounds.get(randomNumber);
    }

    public static AbstractGameAction AlwaysPlayVoiceAction(String key) {
        return new SFXAction(key);
    }

    public static boolean isRandomlyTrue() {
        try {
            return AbstractDungeon.miscRng.random(0, 10) - MyModConfig.voiceFrequency <= 0;
        } catch (NullPointerException nullPointerException) {
            return (int) (Math.random() * 11) - MyModConfig.voiceFrequency <= 0;
        }
    }
}

//        CardCrawlGame.sound.playA(hellospire.SonicMod.makeID("AOUTSTANDING"), MathUtils.random(-0.2F, 0.2F));
