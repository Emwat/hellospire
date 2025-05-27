package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class ChangeFormation extends BaseCard {
    public static final String ID = makeID("ChangeFormation");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private ArrayList<AbstractCard> choices = new ArrayList<>();

    public ChangeFormation() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        choices.add(new ChangeFormationSpeed());
        choices.add(new ChangeFormationFlight());
        choices.add(new ChangeFormationPower());
    }

    //    "DESCRIPTION": "Retain. NL Convert your Levels to Speed, Flight, or Power."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        addToBot(new SelectCardsAction(
                choices,
                "Change Formation",
                false,
                card -> true,
                cards -> {
                    for (AbstractCard choice : cards) {
                        addToBot(new NewQueueCardAction(choice, AbstractDungeon.getRandomMonster(), true, true));
                    }
                }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChangeFormation();
    }

}
