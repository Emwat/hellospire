package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.actions.RandomizeCostAction;

import static theHedgehog.SonicMod.makeID;

public class DizzyPlayerPower extends BasePower {
    public static final String POWER_ID = makeID("DizzyPlayerPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public int highestCost = 0;
    public String highestCostCardName = "(no card)";

    public DizzyPlayerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = String.format("%s NL NL Card: Cost %s %s", DESCRIPTIONS[0], highestCost, highestCostCardName);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        // atbSetCostAndNameAndClearIfZero();

        AbstractDungeon.actionManager.addToTurnStart(new ModXFastAction(() -> {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                int randomizedCost = AbstractDungeon.cardRandomRng.random(3);
                addToBot(new RandomizeCostAction(card, randomizedCost));
            }
        }));
    }

    @Override
    public void atEndOfRound() {
        atbSetCostAndNameAndClearIfZero();
    }

    private void atbSetCostAndNameAndClearIfZero(){
        if (justApplied) {
            justApplied = false;
        } else {
            if (amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

}