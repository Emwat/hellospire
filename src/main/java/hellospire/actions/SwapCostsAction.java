package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class SwapCostsAction extends AbstractGameAction {
    private AbstractCard card1;
    private AbstractCard card2;

    public SwapCostsAction(AbstractCard card1, AbstractCard card2) {
        this.card1 = card1;
        this.card2 = card2;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        SwapCosts(card1, card2);
        this.isDone = true;
    }

    private void SwapCosts(AbstractCard card1, AbstractCard card2) {
        int savedCardCost1 = Math.max(card1.costForTurn, 0);
        int savedCardCost2 = Math.max(card2.costForTurn, 0);

        card1.costForTurn = savedCardCost2;
        card1.isCostModifiedForTurn = true;

        card2.costForTurn = savedCardCost1;
        card2.isCostModifiedForTurn = true;
    }

}
