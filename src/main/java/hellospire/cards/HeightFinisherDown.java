package hellospire.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.util.CardStats;

public class HeightFinisherDown extends BaseCard {
    public static final String ID = makeID("HeavyBounceSlam");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    public HeightFinisherDown() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HeightFinisherDown();
    }
}
