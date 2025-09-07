package hellospire.cardsPackExclusive;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.cards.BaseCard;
import hellospire.character.Sonic;
import hellospire.util.CardStats;
import thePackmaster.ThePackmaster;

public class Trick extends BaseCard {
    public static final String ID = makeID("PackTrick");
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

    public Trick() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        setEthereal(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
        addToBot(new GainEnergyAction(REFUND));
    }


    @Override
    public AbstractCard makeCopy() { // Optional
        return new Trick();
    }
}
