package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import hellospire.SoundLibrary;

import static hellospire.SonicMod.makeID;

public class SpeedHurtPower extends BasePower {
    public static final String POWER_ID = makeID("SpeedHurtPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public static final int CARD_TICKER = 4;
    private static final int SELF_DAMAGE_AMOUNT = 3;
    private int hurtCounter;

    // amount is attack cards played counter

    public SpeedHurtPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, CARD_TICKER);
        if(!owner.hasPower(this.ID)) {
            this.amount = CARD_TICKER;
        }
        this.updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.SELF_DAMAGE_AMOUNT + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.SELF_DAMAGE_AMOUNT + DESCRIPTIONS[2];
        }

    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.amount;

        if (this.amount == 0) {
            this.flash();
            addToBot(SoundLibrary.PlayVoice(SoundLibrary.nice_01));
            this.amount = CARD_TICKER;
            addToBot(new DamageAction(
                    owner,
                    new DamageInfo(
                            owner,
                            SELF_DAMAGE_AMOUNT,
                            DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = CARD_TICKER;
        this.updateDescription();
    }
}