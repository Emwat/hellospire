package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class TrickFinisher1 extends BaseCard {
    public static final String ID = makeID("TrickFinisher1");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
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
