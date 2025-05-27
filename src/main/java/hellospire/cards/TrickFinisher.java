package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class TrickFinisher extends BaseCard {
    public static final String ID = makeID("Trick");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int REFUND = 1;

    public TrickFinisher() {
        super(ID, info);
        setMagic(MAGIC);

        setEthereal(true);
        setExhaust(true);
//        RefundVariable.setBaseValue(this, REFUND);
    }

    /// "DESCRIPTION": "Ethereal. NL Gain 2 Vigor. NL stslib:Refund 1. NL Exhaust.",
    /// "DESCRIPTION": "Ethereal. NL If you already have Vigor, double it. NL If not, gain 1 Vigor. NL stslib:Refund 1. NL Exhaust.",
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayRandomSound(new ArrayList<>(Arrays.asList(
                SoundLibrary.ALLRIGHT,
                SoundLibrary.COOL,
                SoundLibrary.OK,
                SoundLibrary.YES
        ))));
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));

        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(new TrickFinisher1()));
        }
        addToBot(new GainEnergyAction(REFUND));
//        addToBot(new ApplyPowerAction(p, p, new TrickPower(p, magicNumber)));
    }

    private int CalculateVigorToGain(AbstractPlayer p) {
        int amount = p.getPower("Vigor").amount;
        int CAP = 50;
        if (amount >= CAP) {
            amount = 0;
        } else if (amount + amount > CAP) {
            amount = CAP - amount;
        }
        return amount;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TrickFinisher();
    }
}
