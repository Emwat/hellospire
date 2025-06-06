package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.EchoPower;

import static hellospire.SonicMod.makeID;

public class LoseEchoPower extends BasePower {
    public static final String POWER_ID = makeID("LoseEchoPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public LoseEchoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, amount));
        addToBot(new ReducePowerAction(this.owner, this.owner, EchoPower.POWER_ID, amount));
//        AbstractDungeon.actionManager.addToBottom(new HeightFinisherAction());
    }

}