package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Acceleration extends BaseCard {
    public static final String ID = makeID("Acceleration");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    /// Gain !M! Focus."
    public Acceleration() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToTop(new ApplyPowerAction(p, p, new ModDrawReductionPower(p, 1)));
        // addToBot(new ApplyPowerAction(p, p, new FrailPower(p, 2, false), 2));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Acceleration();
    }
}
