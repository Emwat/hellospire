package hellospire.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static hellospire.SonicMod.makeID;

public class LevelUpFlightPower extends BasePower {
    public static final String POWER_ID = makeID("LevelUpFlightPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LevelUpFlightPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


}