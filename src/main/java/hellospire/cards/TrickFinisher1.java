package hellospire.cards;

import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import hellospire.powers.GainedTrickPower;
import hellospire.util.CardStats;

public class TrickFinisher1 extends BaseCard {
    public static final String ID = makeID("TrickFinisher1");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            -3
    );

    private static final int MAGIC = 2;

    public TrickFinisher1() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, MAGIC)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TrickFinisher1();
    }
}
