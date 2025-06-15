package hellospire.powers;

import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.cards.Ring;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class RingPower extends BasePower {
    public static final String POWER_ID = makeID("RingPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    // When changing maxAmountHealed, please also change it in Keywords.json
    private static int amountHealed = 0;
    private static final int maxAmountHealed = 12;

    public RingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public static int getAmountHealed() {
        return amountHealed;
    }

    public static void incrementAmountHealed(int amountHealed) {
        RingPower.amountHealed += amountHealed;
    }

    public static void resetAmountHealed() {
        RingPower.amountHealed = 0;
    }

    public static int getMaxAmountHealed() {
        return maxAmountHealed;
    }

    public static int calculateAmountToHeal(int amountToHeal){
        if (amountHealed + amountToHeal <= maxAmountHealed){
            return amountToHeal;
        } else {
            return maxAmountHealed - amountHealed;
        }
    }

    public void updateDescription() {
        final String youHaveThisMuch = DESCRIPTIONS[2] + amount + (amount == 1 ? DESCRIPTIONS[3] : DESCRIPTIONS[4]);
        final String HPTell = String.format("%s/%s", amountHealed, maxAmountHealed);
        this.description = String.format("%s %s %s %s %s", DESCRIPTIONS[0], maxAmountHealed, DESCRIPTIONS[1], HPTell, youHaveThisMuch);
    }

//      "For each Ring in your hand, increase Block gained from cards by 1. You can only heal a max of ",
//              " HP via Rings in a single combat. You have healed ",
//              ". NL You have ",
//              " Ring.",
//              " Rings."

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        int amountPower = GetPowerAmount("LevelUpPowerPower") * this.amount;
        return type == DamageInfo.DamageType.NORMAL ? damage + (float) amountPower : damage;
    }

    public float modifyBlock(float blockAmount) {
        float outputBlock;
        float tempAmount = (float) this.amount;
        int amountSpeed = GetPowerAmount("LevelUpSpeedPower") * this.amount;
//        SonicMod.logger.info("amountSpeed: " + amountSpeed);

        outputBlock = blockAmount + tempAmount + amountSpeed;

        if (outputBlock < 0.0F) {
            outputBlock = 0.0F;
        }
//        BaseMod.logger.info(String.format("blockAmount %s | amount %s | var2 %s", blockAmount, amount, outputBlock));
        return outputBlock;
    }

    @Override
    public void onPlayCard(AbstractCard playedCard, AbstractMonster m) {
        super.onPlayCard(playedCard, m);

        CalculateNumberOfRings(playedCard);
    }

    private void CalculateNumberOfRings(AbstractCard playedCard){
        int numberOfRings = 0;

        for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
            if (Objects.equals(cardInHand.cardID, Ring.ID)) {
                numberOfRings++;
            }
        }

        if (playedCard != null && Objects.equals(playedCard.cardID, Ring.ID)) {
            numberOfRings--;
        }

        amount = numberOfRings;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        CalculateNumberOfRings(null);
        super.onExhaust(card);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        int amountFlight = GetPowerAmount("LevelUpFlightPower") * this.amount;

        if (amountFlight > 0) {
            addToBot(new ApplyPowerAction(owner, owner, new FocusPower(owner, amountFlight)));
            addToBot(new ApplyPowerAction(owner, owner, new LoseFocusPower(owner, amountFlight)));
        }

    }

    private int GetPowerAmount(String targetID) {
        if (owner.getPower(makeID(targetID)) != null) {
            return owner.getPower(makeID(targetID)).amount;
        }
        return 0;
    }
}