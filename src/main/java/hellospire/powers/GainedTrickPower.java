package hellospire.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import hellospire.actions.TrickFinisherAction;
import hellospire.cards.TrickFinisher1;
import hellospire.cards.TrickFinisher2;
import hellospire.cards.TrickFinisher3;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

import static hellospire.BasicMod.makeID;

public class GainedTrickPower extends BasePower {
    public static final String POWER_ID = makeID("GainedTrick");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public GainedTrickPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, amount));

        ArrayList<AbstractCard> tmp = new ArrayList<>();
        AbstractCard c1 = new TrickFinisher1();
        AbstractCard c2 = new TrickFinisher2();
        AbstractCard c3 = new TrickFinisher3();
        tmp.add(c1);
        tmp.add(c2);
        tmp.add(c3);
        addToBot(new SelectCardsAction(tmp, 1, "Choose a Trick Finisher", cards -> {
            for (AbstractCard c : cards) {
//                addToBot(new NewQueueCardAction(c, null, true, true));
                TrickHelper(c);
            }
        }));
//        AbstractDungeon.actionManager.addToBottom(new TrickFinisherAction());
    }

    private void TrickHelper(AbstractCard card) {
        AbstractCard c1 = new TrickFinisher1();
        AbstractCard c2 = new TrickFinisher2();
        AbstractCard c3 = new TrickFinisher3();
        AbstractCreature p = this.owner;
        if (card.cardID.equals(c1.cardID)) {
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, amount)));
        } else if (card.cardID.equals(c2.cardID)) {
            addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, amount)));
        } else if (card.cardID.equals(c3.cardID)) {
            addToBot(new IncreaseMaxOrbAction(amount));
        }

    }


}