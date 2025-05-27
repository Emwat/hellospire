package hellospire;

import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class SoundLibrary {

    static public String nice_01 = makeID("audio_AMAZING");
    static public String nice_02 = makeID("audio_OUTSTANDING");
    static public String nice_03 = makeID("audio_GREAT");
    static public String nice_04 = makeID("audio_GOOD");
    static public String ALLRIGHT = makeID("ALLRIGHT");
    static public String COOL = makeID("COOL");
    static public String OK = makeID("OK");
    static public String OW = makeID("OW");
    static public String YEAH = makeID("YEAH");
    static public String YES = makeID("YES");
    static public String FeelingGood = makeID("FeelingGood");

    static public String QuickAir1 = makeID("ogg_QuickAir1");
    static public String QuickAir2 = makeID("ogg_QuickAir2");
    static public String QuickAir3 = makeID("ogg_QuickAir3");

    static public String BlueTornado = makeID("ogg_BlueTornado");
    static public String Booster = makeID("ogg_Booster");
    static public String Spring = makeID("ogg_Spring");
    static public String LevelUp = makeID("voice_LevelUp");
    static public String Ring = makeID("ogg_Ring");

    static public String Feeling_good = makeID("audio_FeelingGood");

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

    static public int randomNumber = -1;

    static public SFXAction PlaySound(String key){
        if (MyModConfig.enableSound) {
            return new SFXAction(key);
        }
        return new SFXAction(BlankSound);
    }

    static public SFXAction PlayVoice(String key){
        if (MyModConfig.enableVoice) {
            return new SFXAction(key);
        }
        return new SFXAction(BlankSound);
    }

    static public SFXAction PlayRandomSound(ArrayList<String> sounds) {
        if (!MyModConfig.enableSound) {
            return new SFXAction(BlankSound);
        }

        int generatedRandomNumber = AbstractDungeon.cardRandomRng.random(0, sounds.size() - 1);

        if (sounds.size() < 2) {
            randomNumber = generatedRandomNumber;
        } else {
            while (generatedRandomNumber == randomNumber) {
                generatedRandomNumber = AbstractDungeon.cardRandomRng.random(0, sounds.size() - 1);
            }
            randomNumber = generatedRandomNumber;
        }

        return new SFXAction(sounds.get(randomNumber));
    }

    static public SFXAction PlayRandomVoice(ArrayList<String> sounds) {
        if (!MyModConfig.enableVoice) {
            return new SFXAction(BlankSound);
        }

        int generatedRandomNumber = AbstractDungeon.cardRandomRng.random(0, sounds.size() - 1);

        if (sounds.size() < 2) {
            randomNumber = generatedRandomNumber;
        } else {
            while (generatedRandomNumber == randomNumber) {
                generatedRandomNumber = AbstractDungeon.cardRandomRng.random(0, sounds.size() - 1);
            }
            randomNumber = generatedRandomNumber;
        }

        return new SFXAction(sounds.get(randomNumber));
    }

}

//        CardCrawlGame.sound.playA(hellospire.SonicMod.makeID("AOUTSTANDING"), MathUtils.random(-0.2F, 0.2F));
