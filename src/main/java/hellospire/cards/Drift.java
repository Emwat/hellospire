package hellospire.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.DriftAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Drift extends BaseCard {
    public static final String ID = makeID("Drift");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public Drift() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    ///"DESCRIPTION": "Activate the passive effects of your orbs X times."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DriftAction(p, this.freeToPlayOnce, this.energyOnUse, this.upgraded));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Drift();
    }
}
