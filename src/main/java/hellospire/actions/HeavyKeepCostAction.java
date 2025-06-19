package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import hellospire.SonicMod;
import hellospire.cards.BaseCard;

public class HeavyKeepCostAction extends AbstractGameAction {
    private BaseCard card;

    // Testing methods:
    // Playing the card and then check discard pile
    // Playing Speed Break and then playing the card
    // Playing Speed Break and not playing the card. Let the turn end, check the discard pile

    public HeavyKeepCostAction(BaseCard card) {
        this.card = card;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        card.setCostForCombat(card.costForTurn);
        this.isDone = true;
    }
}