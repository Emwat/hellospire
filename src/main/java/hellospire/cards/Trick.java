package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.actions.ModTextInCenterAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class Trick extends BaseCard {
    public static final String ID = makeID("Trick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int REFUND = 1;
    private static final String[] TrickNames1 = new String[]{"indy", "melon"};
    private static final String[] TrickNames2 = new String[]{"blue scorpion", "blue sky", "double mouse", "method", "mute", "nose grab", "tweak", "twister", "japan", "jessy"};
    public static int TricksPlayed = 0;
    public static int firstTrickNumber = 0;

    public Trick() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setEthereal(true);
        setExhaust(true);

        if (MyModConfig.enableCrossModIntegrations && (Loader.isModLoaded("PrideMod") || isTheRainbow())) {
            loadCardImage(SonicMod.imagePath("cards/skill/TrickAlexDivato.png"));
        }

        if (IsConfusedEgg()){
            loadCardImage(SonicMod.imagePath("cards/skill/Trick_b.png"));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new ModAnimateHopAction(p));
        addToBot(TrickNameAction(p));
        addToBot(new ModFastAction(() -> TricksPlayed++ ));
        addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.ALLRIGHT,
                SoundLibrary.COOL,
                SoundLibrary.OK,
                SoundLibrary.YES
        ))));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
        addToBot(new GainEnergyAction(REFUND));
    }

    private AbstractGameAction TrickNameAction(AbstractPlayer p) {
        int randomNumber1 = AbstractDungeon.miscRng.random(0, TrickNames1.length - 1);
        int randomNumber2 = AbstractDungeon.miscRng.random(0, 2);
        int randomNumber3 = AbstractDungeon.miscRng.random(0, TrickNames2.length - 1);
        Color textColor = new Color(0f / 255f, 255f, 228f / 255f, 1f);
        if (TricksPlayed == 0) {
            firstTrickNumber = randomNumber1;
            return new ModTextInCenterAction(TrickNames1[randomNumber1], textColor);
        } else if (TricksPlayed == 1) {
            if (randomNumber2 == 2) {
                return new ModTextInCenterAction(TrickNames2[randomNumber3], textColor);
            } else {
                int otherNumber = firstTrickNumber == 0 ? 1 : 0;
                return new ModTextInCenterAction(TrickNames1[otherNumber], textColor);
            }
        }
        return new ModTextInCenterAction(TrickNames2[randomNumber3], textColor);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Trick();
    }
}
