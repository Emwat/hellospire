package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class TrickFinisher extends BaseCard {
    public static final String ID = makeID("TrickFinisher");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public TrickFinisher() {
        super(ID, info);
        setExhaust(true);
        setSelfRetain(true);
//        RefundVariable.setBaseValue(this, REFUND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower vigor = p.getPower(VigorPower.POWER_ID);
        if (vigor != null && vigor.amount > 0) {
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, vigor.amount)));
            if (vigor.amount > 12){
                addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                        SoundLibrary.FeelingGood,
                        SoundLibrary.ThatsIt
                ))));
            }
        }
        addToBot(new GainEnergyAction(1));

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.setExhaust(false);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TrickFinisher();
    }
}
