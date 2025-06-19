package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        int currentCost = card.costForTurn;
        if (card.cost < 0) {
            this.isDone = true;
            return;
        }

        int newCost = AbstractDungeon.cardRandomRng.random(0, 3);
        SonicMod.logger.info(String.format("%s %s -> %s cost", card.name, card.cost, newCost));
        if (card.hasTag(SonicTags.HEAVY)) {
            card.setCostForCombat(newCost);
        } else {
            card.setCostForTurn(newCost);
        }

        this.isDone = true;
    }

}
