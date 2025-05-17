package hellospire.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static hellospire.BasicMod.makeID;

public class LevelUpSpeedPower extends BasePower {
    public static final String POWER_ID = makeID("LevelUpSpeed");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LevelUpSpeedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


}