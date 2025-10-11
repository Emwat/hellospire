package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.character.Sonic;
import hellospire.powers.FireIcePower;
import hellospire.powers.SuperSonicPower;
import hellospire.util.CardStats;

public class BecauseScience extends BaseCard {
    public static final String ID = makeID("BecauseScience");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public BecauseScience() {
        super(ID, info);

        setDisplayRarity(CardRarity.RARE);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FireIcePower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BecauseScience();
    }
}
