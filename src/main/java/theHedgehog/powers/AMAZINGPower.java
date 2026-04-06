package theHedgehog.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Panache;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHedgehog.MyModConfig;
import theHedgehog.SoundLibrary;
import theHedgehog.effects.AMAZINGEffect;
import theHedgehog.actions.ModTextInCenterAction;

import static theHedgehog.SonicMod.makeID;

public class AMAZINGPower extends BasePower {
    public static final String POWER_ID = makeID("AMAZINGPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public static final int CARD_AMT = 3;
    // private int baseDamage;
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
        // this.baseDamage = damage;
        this.damage = damage;
        this.updateDescription();
    }

          // "Every time you play ",
          //         " Attack card in a single turn, deal #b",
          //         " Attack cards in a single turn, deal #b",
          //         " damage to ALL enemies and gain ",
          //         " Strength."
    public void updateDescription() {
        int s = this.amount == 1 ? 1 : 2;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[s] + this.damage + DESCRIPTIONS[3];
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
            addToBot(new DamageAllEnemiesAction(
                    AbstractDungeon.player,
                    DamageInfo.createDamageMatrix(this.damage, true),
                    DamageInfo.DamageType.THORNS,
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            // addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, getStrengthGain())));
        }

        this.updateDescription();
    }

    // private int getStrengthGain(){
    //     if (this.baseDamage == 0) {
    //         return 1;
    //     }
    //     return this.damage/this.baseDamage;
    // }

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