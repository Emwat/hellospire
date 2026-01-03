package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class RelaxPick2 extends BaseCard {
    public static final String ID = makeID("RelaxPick2");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public RelaxPick2() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        AbstractCard copy = new Relax().makeStatEquivalentCopy();
        if (this.upgraded) {
            copy.upgrade();
        }

        // addToBot(new ApplyPowerAction(p, p, new RelaxPower(p, 1), 1));
        addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new RelaxPick2();
    }
}
