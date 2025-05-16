package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class ChangeFormation extends BaseCard {
    public static final String ID = makeID("ChangeFormation");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public ChangeFormation() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new ChangeFormationSpeed());
        choices.add(new ChangeFormationFlight());
        choices.add(new ChangeFormationPower());

        addToBot(new SelectCardsAction(choices, "Change Formation", false, null, cards -> {
            for(AbstractCard choice : cards){
                addToBot(new NewQueueCardAction(choice, p, true, true));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChangeFormation();
    }
}
