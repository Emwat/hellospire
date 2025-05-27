package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import hellospire.cards.Ring;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class RingPower extends BasePower {
    public static final String POWER_ID = makeID("RingPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public RingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        int amountPower =  GetPowerAmount("LevelUpPowerPower") * this.amount;
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

        AbstractCard heightCard = new Ring();
        int numberOfHeights = 0;

        for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
            if (Objects.equals(cardInHand.cardID, heightCard.cardID)) {
                numberOfHeights++;
            }
        }
        amount = numberOfHeights;
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

    public void atEndOfTurn(boolean isPlayer) {
//        this.flash();
        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
//        AbstractDungeon.actionManager.addToBottom(new HeightFinisherAction());
    }

    private int GetPowerAmount(String targetID){
        if (owner.getPower(makeID(targetID)) != null) {
            return owner.getPower(makeID(targetID)).amount;
        }
        return 0;
    }
}