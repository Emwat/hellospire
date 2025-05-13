package hellospire.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.cards.TrickFinisher1;
import hellospire.cards.TrickFinisher2;
import hellospire.cards.TrickFinisher3;

public class SecretRouteAction extends AbstractGameAction{
    private AbstractPlayer p;

    public SecretRouteAction(AbstractPlayer p, int amount){
        this.p = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.amount = amount;
    }

    public void update() {
        CardGroup tmp = null;
        addToBot(new DrawCardAction(amount));
    }
        
}
