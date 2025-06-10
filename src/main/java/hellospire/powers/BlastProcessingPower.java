package hellospire.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.unique.ApplyBulletTimeAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.actions.watcher.MasterRealityAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.CarveReality;
import com.megacrit.cardcrawl.cards.purple.MasterReality;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.deprecated.DEPRECATEDMasterRealityPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import hellospire.cards.Ring;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class BlastProcessingPower extends BasePower implements OnCreateCardInterface {
    public static final String POWER_ID = makeID("BlastProcessingPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static int numberOfFreeCards = 0;

    public BlastProcessingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        numberOfFreeCards = amount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        numberOfFreeCards += stackAmount;
        amount += stackAmount;
//        super.stackPower(stackAmount);
    }

    @Override
    public void onCreateCard(AbstractCard abstractCard) {
        boolean possible = abstractCard.cost > 0;

        if (!possible) {
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
    public void atEndOfTurn(boolean isPlayer) {
        amount = numberOfFreeCards;
        super.atEndOfTurn(isPlayer);
    }
}
