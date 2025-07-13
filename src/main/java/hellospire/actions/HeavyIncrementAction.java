package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import hellospire.SonicMod;
import hellospire.cards.BaseCard;

public class HeavyIncrementAction extends AbstractGameAction {
    private AbstractCard card;

    public HeavyIncrementAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (card.cost == -1 || card.cost == -2){
            return;
        }

        for (AbstractCard c : GetAllInBattleInstances.get(this.card.uuid)) {
            SonicMod.logger.info(c.cardID + String.format(" %s %s %s %s %s %s",
                    "cost: ", c.cost,
                    "|costForTurn: ", c.costForTurn,
                    "|chargeCost", c.chargeCost));
//            c.cost = c.costForTurn + 1;
            if (c.freeToPlay()) {
                BaseCard.setCostForCombat(c, 1);
            } else {
                BaseCard.setCostForCombat(c, c.costForTurn + 1);
            }

            // modifyCostForCombat ADDS the amount to the current cost.
            // c.modifyCostForCombat(amtToAdd);
        }

        this.isDone = true;
    }
}