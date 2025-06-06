package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.AMAZINGPower;
import hellospire.util.CardStats;

public class AMAZING extends BaseCard {
    public static final String ID = makeID("AMAZING");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;

    public AMAZING() {
        super(ID, info);
        setMagic(DAMAGE, UPG_DAMAGE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AMAZINGPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AMAZING();
    }
}
