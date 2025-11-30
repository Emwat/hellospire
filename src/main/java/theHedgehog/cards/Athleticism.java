package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.actions.ReduceDebuffsAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Athleticism extends BaseCard {
    public static final String ID = makeID("Athleticism");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int EXHAUSTED = 3;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Athleticism() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    ///     "DESCRIPTION": "Exhaust up to two cards. NL Your debuffs decrease by one."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(EXHAUSTED, false, true, true));
        // addToBot(new ChangeStanceAction("Neutral"));
        addToBot(new ReduceDebuffsAction(p, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Athleticism();
    }
}
