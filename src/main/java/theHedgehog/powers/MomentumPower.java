package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModXFastAction;

import static theHedgehog.SonicMod.makeID;

public class MomentumPower extends BasePower {
    public static final String POWER_ID = makeID("MomentumPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final int costTrigger = 2;

    public MomentumPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]
                .replace("{0}", String.valueOf(this.amount))
                .replace("{1}", this.amount == 1 ? "" : "s");
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        addToBot(new ModXFastAction(()->{
            boolean triggerPower = false;
            if (card.isCostModifiedForTurn && card.costForTurn >= costTrigger) {
                triggerPower = true;
            } else if (!card.isCostModifiedForTurn && card.cost >= costTrigger) {
                triggerPower = true;
            } else if (card.cost == -1 && card.energyOnUse >= costTrigger) {
                triggerPower = true;
            }
            if (triggerPower) {
                addToTop(new DrawCardAction(this.amount));
            }
        }));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}