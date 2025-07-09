package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.cards.BaseCard;

public class RandomizeCostAction extends AbstractGameAction {
    private BaseCard card;

    public RandomizeCostAction(BaseCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (card.cost < 0) {
            this.isDone = true;
            return;
        }

        int newCost = AbstractDungeon.cardRandomRng.random(0, 3);
        if (card.hasTag(SonicTags.SPIN_UP)) {
            if (newCost - 1 < 0) {
                card.setCostForCombat(0);
            } else {
                card.setCostForCombat(newCost - 1);
            }
        } else {
            card.setCostForTurn(newCost);
        }

        this.isDone = true;
    }

}
