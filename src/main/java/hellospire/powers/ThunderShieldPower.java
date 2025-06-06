package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbPassiveAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.BerserkPower;
import hellospire.cards.Ring;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class ThunderShieldPower extends BasePower {
    public static final String POWER_ID = makeID("ThunderShieldPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ThunderShieldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + amount + DESCRIPTIONS[5];
        }
    }

//0    "At the end of your turn, Gain ",
//1            " Ring and Channel ",
//2            " Rings and Channel ",
//3            " Lightning. Your Rings trigger Lightning ",
//4            " time.",
//5            " times."

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        int rings = 0;
        addToTop(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), this.amount));
        for (int i = 0; i < amount; i++) {
            addToTop(new ChannelAction(new Lightning()));
        }
        for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
            if (Objects.equals(cardInHand.cardID, Ring.ID)) {
                rings++;
            }
        }
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (Objects.equals(orb.name, "Lightning")) {
                for (int i = 0; i < rings; i++) {
                    for (int j = 0; j < amount; j++) {
                        orb.onStartOfTurn();
                        orb.onEndOfTurn();
                    }
                }
            }
        }
    }

}
