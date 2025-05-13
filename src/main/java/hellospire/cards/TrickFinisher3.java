package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import hellospire.util.CardStats;

public class TrickFinisher3 extends BaseCard {
    public static final String ID = makeID("TrickFinisher3");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    private static final int MAGIC = 1;

    public TrickFinisher3() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMaxOrbAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TrickFinisher3();
    }
}
