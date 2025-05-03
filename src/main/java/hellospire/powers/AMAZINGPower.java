package hellospire.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PanachePower;
import hellospire.BasicMod;
import hellospire.character.MyCharacter;

import static hellospire.BasicMod.makeID;

public class AMAZINGPower extends BasePower {
    public static final String POWER_ID = makeID("AMAZING");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static final int CARD_AMT = 3;
    public static final String NAME = "AMAZING";
    private int damage;
    // amount is attack cards played counter

    public AMAZINGPower(AbstractCreature owner, int damage) {
        super(POWER_ID, TYPE, TURN_BASED, owner, damage);
//        BaseMod.logger.info("1 AMAZINGPower : this.damage : " + this.damage + " : damage " + damage + " : amount " + amount);
        this.amount = CARD_AMT;
        this.damage = damage;
//        BaseMod.logger.info("2 AMAZINGPower : this.damage : " + this.damage + " : damage " + damage + " : amount " + amount);
        this.updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.damage + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.damage + DESCRIPTIONS[2];
        }

    }

    /// TODO: stackAmount is giving 3 increase when it should reflect the power card's magic number.
    public void stackPower(int stackAmount) {
//        BaseMod.logger.info("AMAZINGPower : stackAmount : " + stackAmount);
        this.fontScale = 8.0F;
        this.damage += stackAmount;
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK){
            return;
        }
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            addToBot(new SFXAction("AAMAZING"));
            this.amount = CARD_AMT;
            this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = CARD_AMT;
        this.updateDescription();
    }
}