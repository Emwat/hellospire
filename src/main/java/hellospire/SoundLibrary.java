package hellospire;

import com.megacrit.cardcrawl.actions.utility.SFXAction;

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

    static public String feeling_good = makeID("audio_FeelingGood");

}

//        CardCrawlGame.sound.playA(hellospire.BasicMod.makeID("AOUTSTANDING"), MathUtils.random(-0.2F, 0.2F));
