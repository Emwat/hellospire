package theHedgehog.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PersistFields;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ReboundPower;
import theHedgehog.actions.ModXFastAction;

import static theHedgehog.SonicMod.makeID;

public class ModPersistPower extends BasePower {
    private boolean justEvoked = true;
    public static final String POWER_ID = makeID("ModPersistPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCard usedCard;

    public ModPersistPower(AbstractCreature owner){
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.justEvoked) {
            this.justEvoked = false;
        } else {
            if (card.type != AbstractCard.CardType.POWER) {
                this.flash();
                card.returnToHand = true;
                // action.reboundCard = true;
                addToBot(new ModXFastAction(()->{
                    // usedCard = card;
                    card.returnToHand = false;
                }));
            }

            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
