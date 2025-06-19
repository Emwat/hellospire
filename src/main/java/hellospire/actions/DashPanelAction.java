package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.SonicMod;

public class DashPanelAction extends AbstractGameAction{

    private AbstractCard card;
    private UseCardAction action;
    private int energyOnUse;

    // Test things
    // X cost cards
    // unplayable
    // SelectCardFromHand

    public DashPanelAction(AbstractCreature target, AbstractCard card, int energyOnUse){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.card = card;
        this.action = new UseCardAction(card, target);
        this.energyOnUse = energyOnUse;
    }

    public void update(){
        AbstractMonster m = null;
        if (action.target != null) {
            m = (AbstractMonster)action.target;
        }

        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(
                tmp, m, this.energyOnUse, true, true), true);
        this.isDone = true;
    }
}
