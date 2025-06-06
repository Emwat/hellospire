package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.IncreaseCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class BlastOff extends BaseCard {
    public static final String ID = makeID("BlastOff");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );


    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public BlastOff() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new IncreaseCostAction(this.uuid, 1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlastOff();
    }
}
