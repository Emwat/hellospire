package hellospire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static hellospire.BasicMod.makeID;

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

    static public String QuickAir1 = makeID("QuickAir1");
    static public String QuickAir2 = makeID("QuickAir2");
    static public String QuickAir3 = makeID("QuickAir3");

    static public String BlueTornado = makeID("BlueTornado");
    static public String Booster = makeID("Booster");
    static public String Spring = makeID("Spring");
    static public String LevelUp = makeID("voiceLevelUp");

    static public String feeling_good = makeID("audio_FeelingGood");

    static public SFXAction PlayRandomSound(ArrayList<String> sounds){
        return new SFXAction(sounds.get(AbstractDungeon.cardRandomRng.random(0, sounds.size() - 1)));
    }

}

//        CardCrawlGame.sound.playA(hellospire.BasicMod.makeID("AOUTSTANDING"), MathUtils.random(-0.2F, 0.2F));
