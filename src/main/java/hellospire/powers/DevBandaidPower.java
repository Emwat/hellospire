package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.watcher.ForesightPower;
import com.megacrit.cardcrawl.relics.BagOfPreparation;
import hellospire.SonicMod;
import hellospire.actions.ModFastAction;

import static hellospire.SonicMod.makeID;

public class DevBandaidPower extends BasePower {
    public static final String POWER_ID = makeID("DevBandaidPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public DevBandaidPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        DoTheThing();
    }

    private void DoTheThing(){
        if (AbstractDungeon.player != null && AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            addToBot(new ModFastAction(() -> {
                SonicMod.logger.info("Activating DevBandaidPower!!");
                AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);
            }));
            addToBot(new DrawCardAction(AbstractDungeon.player.hasRelic(BagOfPreparation.ID) ? 7 : 5));
        }
        addToBot(new ReducePowerAction(owner, owner, ID, amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
