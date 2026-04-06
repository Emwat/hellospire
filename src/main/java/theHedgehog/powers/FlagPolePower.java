package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cards.Ring;

import static theHedgehog.SonicMod.makeID;

public class FlagPolePower extends BasePower {
    public static final String POWER_ID = makeID("FlagPolePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public FlagPolePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
//        super.onPlayCard(card, m);

        if (card.hasTag(SonicTags.RING)) {
            addToBot(new ChannelAction(new Frost()));
        }
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        AbstractDungeon.actionManager.addToBottom(new ModXFastAction(() ->{
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    card.setCostForTurn(-9);
                }
            }
        }));
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}