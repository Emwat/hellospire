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

public class TrickFinisherAction extends AbstractGameAction{
    private AbstractPlayer p;

    public TrickFinisherAction(){
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, 1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update(){
        CardGroup tmp = null;
        if (this.duration == Settings.ACTION_DUR_MED){
            tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            AbstractCard c1 = new TrickFinisher1();
            AbstractCard c2 = new TrickFinisher2();
            AbstractCard c3 = new TrickFinisher3();
            tmp.addToTop(c1);
            tmp.addToTop(c2);
            tmp.addToTop(c3);
            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose", false);
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0){
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards){
                c.unhover();
                addToBot(new AutoplayCardAction(c, tmp));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
