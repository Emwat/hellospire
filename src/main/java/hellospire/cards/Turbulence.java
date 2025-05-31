package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.TurbulencePower;
import hellospire.util.CardStats;

public class Turbulence extends BaseCard {
    public static final String ID = makeID("Turbulence");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 1;

    public Turbulence() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new TurbulencePower(p, magicNumber)));
        if (this.upgradeBlock) {
            addToBot(new ExhaustAction(1, false, true, true));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(2);
//            this.setInnate(true);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Turbulence();
    }
}
