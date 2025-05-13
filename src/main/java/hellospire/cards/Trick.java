package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.MyCharacter;
import hellospire.powers.GainedHeightPower;
import hellospire.powers.GainedTrickPower;
import hellospire.util.CardStats;

public class Trick extends BaseCard {
    public static final String ID = makeID("Trick");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;
    private static final int REFUND = 1;

    public Trick() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setCostUpgrade(0);

        this.isEthereal = true;
        this.exhaust = true;
        RefundVariable.setBaseValue(this, REFUND);
    }

        /// "DESCRIPTION": "Ethereal. NL Gain 2 Vigor. NL stslib:Refund 1. NL Exhaust.",
        /// "DESCRIPTION": "Ethereal. NL If you already have Vigor, double it. NL If not, gain 1 Vigor. NL stslib:Refund 1. NL Exhaust.",
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new TalkAction(p, "Yeah!", 1, 1));
//        addToBot(new ShoutAction(p, "Yeah!", 1, 1));

        addToBot(new SFXAction(SoundLibrary.ALLRIGHT));

//        switch (AbstractDungeon.cardRandomRng.random(1,5)){
//            case 1: {
//                addToBot(new SFXAction(SoundLibrary.ALLRIGHT));
//                break;
//            }
//            case 2: {
//                addToBot(new SFXAction(SoundLibrary.COOL));
//                break;
//            }
//            case 3: {
//                addToBot(new SFXAction(SoundLibrary.OK));
//                break;
//            }
//            case 4: {
//                addToBot(new SFXAction(SoundLibrary.YES));
//                break;
//            }
//        }
        addToBot(new ApplyPowerAction(p, p, new GainedTrickPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Trick();
    }
}
