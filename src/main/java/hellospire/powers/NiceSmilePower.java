package hellospire.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class NiceSmilePower extends BasePower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = makeID("NiceSmilePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NiceSmilePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (Objects.equals(power.ID, VigorPower.POWER_ID)) {
            return power.amount + this.amount;
        }

        return BetterOnApplyPowerPower.super.betterOnApplyPowerStacks(power, target, source, stackAmount);
    }

    // Not working :(
//    @Override
//    public boolean betterOnApplyPower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
//        if (abstractPower.ID.equals(VigorPower.POWER_ID)) {
//            this.flash();
//            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount)));
//            return false;
//        }
//        return true;
//    }
}