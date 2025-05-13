package hellospire.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.cards.TrickFinisher1;
import hellospire.cards.TrickFinisher2;
import hellospire.cards.TrickFinisher3;

public class SwapCostsAction extends AbstractGameAction{
    private AbstractCard card1;
    private AbstractCard card2;

    public SwapCostsAction(AbstractCard card1, AbstractCard card2){
        this.card1 = card1;
        this.card2 = card2;

        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update(){
        SwapCosts(card1, card2);
        this.isDone = true;
    }

    private void SwapCosts(AbstractCard card1, AbstractCard card2){
        int savedCardCost1 = card1.costForTurn;
        int savedCardCost2 = card2.costForTurn;

        card1.costForTurn = savedCardCost2;
        card1.isCostModifiedForTurn = true;

        card2.costForTurn = savedCardCost1;
        card2.isCostModifiedForTurn = true;
    }

}
