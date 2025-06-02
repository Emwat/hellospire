package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PanachePower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static hellospire.SonicMod.makeID;

public class SonicBoomPower extends BasePower {
    public static final String POWER_ID = makeID("SonicBoomPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static boolean isUpgraded;

    public SonicBoomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;

        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            this.addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }


        addToBot(new ReducePowerAction(owner, owner, ID, 1));
//        for (AbstractCreature mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
//            addToBot(new LoseHPAction(mo, p, owner));
//        }

        // I think it's just more fun to ALWAYS have damage modifiers.
//        if (this.isUpgraded) {
//            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,
//                    DamageInfo.createDamageMatrix(owner.currentBlock, false),
//                    DamageInfo.DamageType.THORNS,
//                    AbstractGameAction.AttackEffect.FIRE));
//        } else {
//            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,
//                    DamageInfo.createDamageMatrix(owner.currentBlock, true),
//                    DamageInfo.DamageType.THORNS,
//                    AbstractGameAction.AttackEffect.FIRE));
//        }

        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(owner.currentBlock, false),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.FIRE));

    }

}