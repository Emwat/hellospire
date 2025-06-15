package hellospire.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import hellospire.cards.*;

import java.util.ArrayList;

import static hellospire.SonicMod.makeID;

public class ExtenderPower extends BasePower {
    public static final String POWER_ID = makeID("ExtenderPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    AbstractCard c1 = new Extender1();
    AbstractCard c2 = new Extender2();
    AbstractCard c3 = new Extender3();

    public ExtenderPower(AbstractPlayer owner, int amount)
    {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        AbstractPower vigor = owner.getPower("Vigor");

        ArrayList<AbstractCard> tmp = new ArrayList<>();
        tmp.add(c1);
        tmp.add(c2);
        tmp.add(c3);

        if (vigor != null && vigor.amount > 0){
            this.flash();
            addToBot(new SelectCardsAction(tmp, 1, "Choose a Trick Finisher", cards -> {
                for (AbstractCard c : cards) {
                    TrickHelper(c);
                }
            }));

        }
    }

    private void TrickHelper(AbstractCard card) {
        AbstractCreature p = this.owner;
        if (card.cardID.equals(c1.cardID)) {
            addToBot(new IncreaseMaxOrbAction(2));
        } else if (card.cardID.equals(c2.cardID)) {
            addToBot(new DrawCardAction(2));
        } else if (card.cardID.equals(c3.cardID)) {
            addToBot(new GainEnergyAction(2));
        }
    }


}