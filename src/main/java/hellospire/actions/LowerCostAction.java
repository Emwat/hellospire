package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import hellospire.SonicTags;
import hellospire.cards.BaseCard;

public class LowerCostAction extends AbstractGameAction {
    private final AbstractCard card;
    private final int amount;

    public LowerCostAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        LowerCost(card);
        this.isDone = true;
    }

    private void LowerCost(AbstractCard card) {
        if (card.hasTag(SonicTags.SPIN_UP)) {
            if (card.costForTurn - amount < 0) {
                BaseCard.setCostForCombat(card, 0);
            } else {
                BaseCard.setCostForCombat(card, card.costForTurn - amount);
            }
        } else {
            if (card.costForTurn - amount < 0) {
                card.setCostForTurn(0);
            } else {
                card.setCostForTurn(card.costForTurn - amount);
            }
        }

    }

}
