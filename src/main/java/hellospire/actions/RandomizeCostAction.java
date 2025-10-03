package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.cards.BaseCard;

public class RandomizeCostAction extends AbstractGameAction {
    private AbstractCard card;

    public RandomizeCostAction(AbstractCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (card.cost < 0) {
            this.isDone = true;
            return;
        }

        int newCost = AbstractDungeon.cardRandomRng.random(3);
        if (card.hasTag(SonicTags.SPIN_UP)) {
            newCost -= 1;
            BaseCard.setCostForCombat(card, Math.max(newCost, 0));
            card.flash();
        } else {
            card.setCostForTurn(newCost);
            card.flash();
        }

        this.isDone = true;
    }

}
