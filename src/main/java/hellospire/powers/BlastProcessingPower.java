package hellospire.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import hellospire.SonicMod;

import static hellospire.SonicMod.makeID;

public class BlastProcessingPower extends BasePower implements OnCreateCardInterface {
    public static final String POWER_ID = makeID("BlastProcessingPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static int maxAmount = 0;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public BlastProcessingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        maxAmount = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + maxAmount + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        maxAmount += stackAmount;
        amount += stackAmount;
//        super.stackPower(stackAmount);
    }

    @Override
    public void onCreateCard(AbstractCard abstractCard) {
        boolean possible = abstractCard.cost > 0;

        if (!possible || abstractCard.freeToPlay()) {
            return;
        }

        if (amount > 0) {
            amount--;
            abstractCard.costForTurn = abstractCard.cost - 1;
            abstractCard.cost = abstractCard.cost - 1;
            abstractCard.isCostModified = true;
            abstractCard.superFlash(Color.GOLD.cpy());
        }
    }

    @Override
    public void atStartOfTurn() {
        // SonicMod.logger.info("Blast Processing amount : " + amount + " | maxAmount : " + maxAmount);

        // 09/08/2025 04:10 PM TogetherInSpire (v6.4.20) seems to overwrite my maxAmount.
        if (maxAmount < 2) {
            amount = 2;
        } else {
            amount = maxAmount;
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
