package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.stances.FireStance;
import theHedgehog.util.CardStats;

public class BecauseSciencePick1 extends BaseCard {
    public static final String ID = makeID("BecauseSciencePick1");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public BecauseSciencePick1() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        addToBot(new ChangeStanceAction(new FireStance()));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BecauseSciencePick1();
    }
}
