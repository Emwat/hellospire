package theHedgehog.powers;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cardmodifiers.RocketAccelModifier;

import static theHedgehog.SonicMod.makeID;

public class RocketAccelPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = makeID("RocketAccelPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;


    public RocketAccelPower(AbstractCreature owner, int amount, int magicNumber) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amount2 = magicNumber;
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && this.amount > 0) {
            this.flash();

            addToTop(new ModXFastAction(() -> {
                CardModifierManager.addModifier(card, new RocketAccelModifier(this.amount2));
                card.flash();
            }));

            --this.amount;
            updateDescription();
            if (this.amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage + (float) this.amount2 : damage;
    }

    // @Override
    // public void stackPower(int stackAmount) {
    //     super.stackPower(stackAmount);
    //     amount += stackAmount;
    // }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
