package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.DizzyPower;
import hellospire.powers.ExtenderPower;
import hellospire.util.CardStats;

public class Extender extends BaseCard {
    public static final String ID = makeID("Extender");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public Extender() {
        super(ID, info);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ExtenderPower(p, 1)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Extender();
    }
}
