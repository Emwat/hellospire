package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import hellospire.cards.Height;

import java.util.Objects;

import static hellospire.BasicMod.makeID;

public class GainedHeightPower extends BasePower {
    public static final String POWER_ID = makeID("GainedHeight");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public GainedHeightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public float modifyBlock(float blockAmount) {
        float var2;
        return (var2 = blockAmount + (float)this.amount) < 0.0F ? 0.0F : var2;
    }

    @Override
    public void onPlayCard(AbstractCard playedCard, AbstractMonster m) {
        super.onPlayCard(playedCard, m);

        AbstractCard heightCard = new Height();
        int numberOfHeights = 0;
        boolean hasExhaustedHeightCard = false;

        if (Objects.equals(playedCard.cardID, heightCard.cardID)) {
            hasExhaustedHeightCard = true;
        }

        for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
            if(Objects.equals(cardInHand.cardID, heightCard.cardID)){
                if (!hasExhaustedHeightCard) {
                    addToBot(new ExhaustSpecificCardAction(cardInHand, AbstractDungeon.player.hand));
//                    addToBot(new NewQueueCardAction(cardInHand, AbstractDungeon.player));
                    hasExhaustedHeightCard = true;
                } else {
                    numberOfHeights++;
                }

            }
        }
        amount = numberOfHeights;

        if (amount == 0){
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        if (amount == 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
//        AbstractDungeon.actionManager.addToBottom(new HeightFinisherAction());
    }
}