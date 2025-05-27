package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.EchoPower;

import static hellospire.SonicMod.makeID;

public class NextTurnEchoPower extends BasePower {
    public static final String POWER_ID = makeID("NextTurnEchoPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public NextTurnEchoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new ReducePowerAction(owner, owner, ID, amount));
        addToBot(new ApplyPowerAction(owner, owner, new EchoPower(owner, amount)));
        addToBot(new ApplyPowerAction(owner, owner, new LoseEchoPower(owner, amount)));
//        AbstractDungeon.actionManager.addToBottom(new HeightFinisherAction());
    }

}