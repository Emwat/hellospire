package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static hellospire.SonicMod.makeID;

public class SonicBoomPower extends BasePower {
    public static final String POWER_ID = makeID("SonicBoomPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public SonicBoomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();

        addToBot(new ReducePowerAction(owner, owner, ID, amount));
        addToBot(new DamageAllEnemiesAction(
                AbstractDungeon.player,
                owner.currentBlock,
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.SMASH));
    }

}