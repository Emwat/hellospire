package hellospire.powers;

import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hellospire.SonicTags;
import hellospire.actions.ModFastAction;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class RingPower extends BasePower {
    public static final String POWER_ID = makeID("RingPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static boolean isLightSpeedDashing = false;
    public static boolean isPlayingBoost = false;

    public RingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    // When testing Ring, see methods to check if amount updated correctly.
    // 1) Play a Ring.
    // 2) Exhaust a Ring.
    // 3) Volcano Slider a Ring.
    // 4) Get Dazed.
    // 5) Draw a Ring.
    // 6) Discard a Ring.

    public static void setIsLightSpeedDashing(boolean newValue) {
        isLightSpeedDashing = newValue;
    }

    public void updateDescription() {
        int amountSpeed = ModGetPowerAmount(LevelUpSpeedPower.POWER_ID);
        String amountSpeedText = String.valueOf(this.amount + (amountSpeed * this.amount));

        if (amount == 1) {
            this.description = String.format("%s%s%s", DESCRIPTIONS[0], this.amount, DESCRIPTIONS[1]);
        } else {
            this.description = String.format("%s%s%s", DESCRIPTIONS[0], this.amount, DESCRIPTIONS[1]);
        }
    }

    // "Increase Block gained from cards by #b1",
    //         "."

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        int amountPower = ModGetPowerAmount(LevelUpPowerPower.POWER_ID) * this.amount;
        return type == DamageInfo.DamageType.NORMAL ? damage + (float) amountPower : damage;
    }

    public float modifyBlock(float blockAmount) {
        float outputBlock;
        float tempAmount = (float) this.amount;
        int amountSpeed = ModGetPowerAmount(LevelUpSpeedPower.POWER_ID) * this.amount;

        outputBlock = blockAmount + tempAmount + amountSpeed;

        if (outputBlock < 0.0F) {
            outputBlock = 0.0F;
        }
        return outputBlock;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        addToTop(new ModFastAction(()-> {
            isPlayingBoost = card.cardID.equals(hellospire.cardsPackExclusive.Boost.ID);
        }));
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        AbstractDungeon.actionManager.addToBottom(new ModFastAction(this::CalculateNumberOfRings));
        // ThousandCutsPower does not have this super method.
        // super.onAfterCardPlayed(usedCard);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(SonicTags.RING)) {
            CalculateNumberOfRings();
        }
    }

    public void CalculateNumberOfRings() {
        int numberOfRings = 0;

        for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
            if (cardInHand.hasTag(SonicTags.RING)) {
                numberOfRings++;
            }
        }

        amount = numberOfRings;
        updateDescription();
    }

//    @Override
//    public void atStartOfTurn() {
//        super.atStartOfTurn();
//        int amountFlight = GetPowerAmount("LevelUpFlightPower") * this.amount;
//
//        if (amountFlight > 0) {
//            addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, amountFlight)));
//            addToBot(new ApplyPowerAction(owner, owner, new LoseFocusPower(owner, amountFlight)));
//        }
//
//    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            setIsLightSpeedDashing(false);
        }
    }

    @Override
    public void onVictory() {
        setIsLightSpeedDashing(false);
        // addToBot(new VFXAction(new RunForwardEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        // AbstractDungeon.player.useJumpAnimation();
        super.onVictory();
    }

    @Override
    public int onHeal(int healAmount) {
        DiscardBoostsToHand();
        return super.onHeal(healAmount);
    }

    public void DiscardBoostsToHand() {
        addToBot(new ModFastAction(() -> {
            if (AbstractDungeon.player.discardPile.isEmpty()) {
                return;
            }
            // Prevents Boost from returning itself to hand
            if (!isPlayingBoost) {
                for (AbstractCard discardedCard : AbstractDungeon.player.discardPile.group) {
                    if (Objects.equals(discardedCard.cardID, hellospire.cardsPackExclusive.Boost.ID)) {
                        addToBot(new DiscardToHandAction(discardedCard));
                    }
                }
            }

        }));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}