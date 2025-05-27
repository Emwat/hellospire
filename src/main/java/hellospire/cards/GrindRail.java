package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.SwapCostsAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class GrindRail extends BaseCard {
    public static final String ID = makeID("GrindRail");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public GrindRail() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        if(!this.upgraded) {
            ArrayList<AbstractCard> adjacentCards = getNeighbors(p.hand);

            AbstractCard card1;
            AbstractCard card2;

            if (adjacentCards.size() < 2){
                return;
            }

            card1 = adjacentCards.get(0);
            card2 = adjacentCards.get(1);

            addToBot(new SwapCostsAction(card1, card2));
        } else {
            addToBot(new SelectCardsInHandAction(
                    2,
                    "Select 2 cards to swap costs.",
                    true,
                    true,
                    filter -> { return true; },
                    cards -> {

                        if(cards.size() < 2){
                            return;
                        }

                        AbstractCard pickedCard1 = cards.get(0);
                        AbstractCard pickedCard2 = cards.get(1);
                        addToBot(new SwapCostsAction(pickedCard1, pickedCard2));
            }));
        }
    }



    public ArrayList<AbstractCard> getNeighbors(CardGroup hand) {
        ArrayList<AbstractCard> neighbors = new ArrayList<>();

        if (hand.contains(this)) {
            int index = hand.group.indexOf(this);
            if (index > 0) {
                neighbors.add(hand.group.get(index -1));
            }
            if (index < hand.size() - 1) {
                neighbors.add(hand.group.get(index + 1));
            }
        }
        return neighbors;
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GrindRail();
    }
}
