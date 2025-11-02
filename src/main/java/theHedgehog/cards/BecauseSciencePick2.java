package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.stances.IceStance;
import theHedgehog.util.CardStats;

public class BecauseSciencePick2 extends BaseCard {
    public static final String ID = makeID("BecauseSciencePick2");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public BecauseSciencePick2() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        addToBot(new ChangeStanceAction(new IceStance()));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BecauseSciencePick2();
    }
}
