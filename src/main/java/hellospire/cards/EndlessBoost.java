package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class EndlessBoost extends BaseCard {
    public static final String ID = makeID("EndlessBoost");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public EndlessBoost() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    /// "Lose 2 Dexterity. NL Every turn, gain 1 [E]."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new BerserkPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EndlessBoost();
    }
}
