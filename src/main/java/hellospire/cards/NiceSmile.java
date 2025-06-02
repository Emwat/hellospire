package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.AMAZINGPower;
import hellospire.powers.NiceSmilePower;
import hellospire.util.CardStats;

public class NiceSmile extends BaseCard {
    public static final String ID = makeID("NiceSmile");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public NiceSmile() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NiceSmilePower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NiceSmile();
    }
}
