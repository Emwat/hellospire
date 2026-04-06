package theHedgehog.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicTags;
import theHedgehog.cards.BaseCard;

public class ModTransformWorkaroundAction extends AbstractGameAction {
    private AbstractCard target;
    private AbstractCard replacement;

    public ModTransformWorkaroundAction(AbstractCard target, AbstractCard replacement) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;

        this.target = target;
        this.replacement = replacement;
        if (Settings.FAST_MODE) {
            this.startDuration = 0.05F;
        } else {
            this.startDuration = 0.15F;
        }

        this.duration = this.startDuration;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            int index = -1;
            for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
                AbstractCard thisCard = AbstractDungeon.player.hand.group.get(i);
                if (thisCard.equals(target)) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                addToTop(new MakeTempCardInHandAction(replacement.makeCopy()));
                addToTop(new ExhaustSpecificCardAction(target, AbstractDungeon.player.hand));
            } else {
                addToTop(new MakeTempCardInHandAction(replacement.makeCopy()));
                addToTop(new ExhaustSpecificCardAction(target, AbstractDungeon.player.discardPile));
            }
        }

        this.tickDuration();
    }


}
