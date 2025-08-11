package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EchoPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import hellospire.SoundLibrary;

import static hellospire.SonicMod.makeID;

public class SilverPower extends BasePower {
    public static final String POWER_ID = makeID("SilverPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    // The only thing TURN_BASED controls is the color of the number on the power icon.
    // Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    // For a power to actually decrease/go away on its own they do it themselves.
    // Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public SilverPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Silver));
        addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(owner, new ShockWaveEffect(owner.hb.cX, owner.hb.cY, Settings.HALF_TRANSPARENT_WHITE_COLOR.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        } else {
            addToBot(new VFXAction(owner, new ShockWaveEffect(owner.hb.cX, owner.hb.cY, Settings.HALF_TRANSPARENT_WHITE_COLOR.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, owner, new StrengthPower(mo, -this.amount), -this.amount, true, AbstractGameAction.AttackEffect.NONE));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.hasPower("Artifact")) {
                addToBot(new ApplyPowerAction(mo, owner, new GainStrengthPower(mo, this.amount), this.amount, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        addToBot(new ReducePowerAction(owner, owner, ID, amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

}