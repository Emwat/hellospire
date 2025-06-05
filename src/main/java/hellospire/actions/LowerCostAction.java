package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class LowerCostAction extends AbstractGameAction {
    private AbstractCard card;

    public LowerCostAction(AbstractCard card) {
        this.card = card;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        LowerCost(card);
        this.isDone = true;
    }

    private void LowerCost(AbstractCard card) {
        if (card.costForTurn > 0) {
            card.setCostForTurn(card.costForTurn - 1);
            card.isCostModifiedForTurn = true;
        }
    }

}
