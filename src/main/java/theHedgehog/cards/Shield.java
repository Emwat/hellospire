package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Shield extends BaseCard {
    public static final String ID = makeID("Shield");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public Shield() {
        super(ID, info);
        setCostUpgrade(1);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new Shield();
    }
}
