package hellospire.powers;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import hellospire.MyModConfig;
import hellospire.SoundLibrary;
import hellospire.effects.AMAZINGEffect;
import hellospire.actions.ModTextInCenterAction;

import static hellospire.SonicMod.makeID;

public class AMAZINGPower extends BasePower {
    public static final String POWER_ID = makeID("AMAZINGPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static final int CARD_AMT = 3;
    private int damage;
    // amount is attack cards played counter

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public AMAZINGPower(AbstractCreature owner, int damage) {
        super(POWER_ID, TYPE, TURN_BASED, owner, damage);
        if (!owner.hasPower(this.ID)) {
            this.amount = CARD_AMT;
        }
        this.damage = damage;
        this.updateDescription();

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.damage + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.damage + DESCRIPTIONS[2];
        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.damage += stackAmount;
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK) {
            return;
        }
        --this.amount;

        if (this.amount == 2) {
            addToTop(new ModTextInCenterAction("Great!", Color.WHITE.cpy()));
        }

        if (this.amount == 1) {
            addToTop(new ModTextInCenterAction("OUTSTANDING!", Color.PINK.cpy()));
            if (MyModConfig.enableVoice && MyModConfig.voiceFrequency == 10) {
                addToBot(SoundLibrary.VoiceAction(SoundLibrary.Amazing2));
            }
        }

        if (this.amount == 0) {
            this.flash();
            if (MyModConfig.enableTextPopUps && MyModConfig.enableVoice && MyModConfig.voiceFrequency == 10) {
                addToTop(new VFXAction(new AMAZINGEffect("amazing")));
            } else {
                addToTop(new ModTextInCenterAction("AMAZING!!", Color.GOLD.cpy()));
            }
            // addToTop(new TextAboveCreatureAction(owner, "AMAZING!"));
            if (MyModConfig.enableVoice && MyModConfig.voiceFrequency == 10) {
                addToBot(SoundLibrary.VoiceAction(SoundLibrary.Amazing1));
            }
            this.amount = CARD_AMT;
            // this.addToBot(new VFXAction());
            this.addToBot(new DamageAllEnemiesAction(
                    AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(this.damage, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = CARD_AMT;
        this.updateDescription();
        addToTop(new ModTextInCenterAction("Good!", Color.WHITE.cpy()));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}