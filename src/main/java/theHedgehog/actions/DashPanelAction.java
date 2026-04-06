package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SharpHidePower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import theHedgehog.SonicTags;
import theHedgehog.cards.BaseCard;
import theHedgehog.util.GeneralUtils;

public class DashPanelAction extends AbstractGameAction {

    private AbstractCard card;
    private UseCardAction action;
    private int energyOnUse;
    private int vigorWorkaround = 0;
    private int blockWorkaround = 0;

    // Test things
    // X cost cards
    // unplayable
    // SelectCardFromHand

    public DashPanelAction(AbstractCreature target, AbstractCard card, int energyOnUse) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.card = card;
        this.action = new UseCardAction(card, target);
        this.energyOnUse = energyOnUse;

        vigorWorkaround = (int)GeneralUtils.getVigorAndMoreAmount(card.baseDamage);
        blockWorkaround = (int)GeneralUtils.getBlockAndMoreAmount(card.baseBlock);
    }

    private AbstractMonster modGetRandomMonster() {
        return AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
    }

    public void update() {
        if (!card.purgeOnUse) {
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            } else {
                m = modGetRandomMonster();
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;

            if (tmp.type == AbstractCard.CardType.ATTACK && vigorWorkaround > 0) {
                tmp.baseDamage += vigorWorkaround;
            }
            if (tmp.baseBlock > 0 && blockWorkaround > 0) {
                tmp.baseBlock += blockWorkaround;
            }

            if (card instanceof BaseCard && card.hasTag(SonicTags.RIGHTMOST)) {
                BaseCard thisHorseKick = ((BaseCard) tmp);
                if (thisHorseKick.CheckIfRightCard(card, AbstractDungeon.player.hand)) {
                    thisHorseKick.forceConditionEffect = true;
                }
            }

            // For some reason, if DashPanel has an attack, it counts as two attacks.
            // So if you're Dash Paneling two attacks, Pen Nib registers four attacks
            if (m != null) {
                if (m.hasPower(SharpHidePower.POWER_ID)) {
                    AbstractDungeon.player.addBlock(m.getPower(SharpHidePower.POWER_ID).amount);
                }
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(
                    tmp, m, this.energyOnUse, true, true), true);
            this.isDone = true;
        }

    }
}
