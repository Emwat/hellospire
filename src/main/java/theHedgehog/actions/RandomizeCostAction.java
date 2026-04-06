package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicTags;
import theHedgehog.cards.BaseCard;

public class RandomizeCostAction extends AbstractGameAction {
    private AbstractCard card;
    private int newCost = -1;

    public RandomizeCostAction(AbstractCard card, int newCost) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.newCost = newCost;
    }

    public void update() {
        if (card.cost < 0) {
            this.isDone = true;
            return;
        }

        if (card.hasTag(SonicTags.SPIN_UP)) {
            if (newCost > 0) {
                newCost -= 1;
            }
            BaseCard.setCostForCombat(card, Math.max(newCost, 0));
            card.flash();
        } else {
            card.setCostForTurn(newCost);
            card.flash();
        }

        this.isDone = true;
    }

}
