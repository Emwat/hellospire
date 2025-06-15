package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class NiceSmilePower extends BasePower {
    public static final String POWER_ID = makeID("NiceSmilePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static boolean debounce = false;

    public NiceSmilePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // TODO: A user has reported that this infinitely loops. :( 06/05/2025 01:52 PM
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (isOver99(owner)) {
            return;
        }

        if (Objects.equals(power.ID, "Vigor") && debounce == false) {
            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount)));
            debounce = true;
            return;
        }
        debounce = false;

    }

    private boolean isOver99(AbstractCreature creature) {
        AbstractPower vigor = owner.getPower("Vigor");
        return vigor != null && vigor.amount > 99;
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