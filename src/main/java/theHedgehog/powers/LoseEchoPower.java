package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.EchoPower;
// import hellospire.effects.DoubleSonicParticle;

import static theHedgehog.SonicMod.makeID;

public class LoseEchoPower extends BasePower {
    public static final String POWER_ID = makeID("LoseEchoPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    // The only thing TURN_BASED controls is the color of the number on the power icon.
    // Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    // For a power to actually decrease/go away on its own they do it themselves.
    // Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    // private DoubleSonicParticle VFX;
    // private int cardsDoubledThisTurn = 0;

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
        // addToBot(new ModXFastAction(() -> {
        //     VFX.finish();
        // }));
    }

    // public void onInitialApplication() {
    //     AbstractPlayer p = AbstractDungeon.player;
    //     if (!(p instanceof Sonic)) {
    //         return;
    //     }
    //     if (!Sonic.currentModSkin.getName().contains("Battle Sonic")) {
    //         return;
    //     }
    //
    //     AbstractDungeon.effectsQueue.add(new SmokePuffEffect(p.hb.cX, p.hb.cY));
    //     VFX = new DoubleSonicParticle(p);
    //     AbstractDungeon.actionManager.addToBottom(new VFXAction(VFX));
    // }

    // public void onUseCard(AbstractCard card, UseCardAction action) {
    //     if (
    //             (!card.purgeOnUse) &&
    //                     (this.amount > 0) &&
    //                     (card.target == ENEMY || card.target == ALL_ENEMY || card.target == SELF_AND_ENEMY) &&
    //                     this.cardsDoubledThisTurn < this.amount
    //     ) {
    //         this.cardsDoubledThisTurn += 1;
    //         VFX.sonic.useFastAttackAnimation();
    //     }
    // }
    //
    // public void onDeath() {
    //     VFX.finish();
    // }
    //
    // public void onVictory() {
    //     VFX.finish();
    // }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}