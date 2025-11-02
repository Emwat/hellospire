package theHedgehog.util;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHedgehog.cards.*;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.PowerBrakeRelic;

// valid commands:
// sss

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleDevCustom extends ConsoleCommand {
    public SonicConsoleDevCustom() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(4));

        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(mo, AbstractDungeon.player, 9999));
            }
        }

        TopKickTest();
        // DizzyTest();
        // FireTest();
    }

    private void TopKickTest(){
        AbstractCard topKick = new TopKick().makeStatEquivalentCopy();
        AbstractCard topKick2 = new TopKick().makeStatEquivalentCopy();
        topKick2.upgrade();
        AbstractCard espio = new AssistEspio().makeStatEquivalentCopy();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(topKick, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(topKick2, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(espio, 1));

        AbstractRelic r = new CDFutureRelic();
        if (!AbstractDungeon.player.hasRelic(CDFutureRelic.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                    Settings.WIDTH / 2.0F * Settings.scale,
                    Settings.HEIGHT / 2.0F * Settings.scale,
                    r);
        }
    }

    private void DizzyTest(){
        AbstractCard windmill = new Windmill().makeStatEquivalentCopy();
        AbstractCard needle = new SpinningNeedleAttack().makeStatEquivalentCopy();
        AbstractCard charmy = new AssistCharmy().makeStatEquivalentCopy();
        // AbstractCard barry = new AssistBarry().makeStatEquivalentCopy();
        AbstractCard sticks = new AssistSticks().makeStatEquivalentCopy();
        AbstractCard speedbreak = new SpeedBreak().makeStatEquivalentCopy();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(windmill, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(needle, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(charmy, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(sticks, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(speedbreak, 1));

        AbstractRelic r = new PowerBrakeRelic();
        if (!AbstractDungeon.player.hasRelic(PowerBrakeRelic.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                    Settings.WIDTH / 2.0F * Settings.scale,
                    Settings.HEIGHT / 2.0F * Settings.scale,
                    r);
        }
    }

    private void FireTest(){
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
        }
        AbstractCard crouch = new Crouch().makeStatEquivalentCopy();
        crouch.upgrade();

        AbstractCard somer = new FireSomersault().makeStatEquivalentCopy();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Crouch().makeStatEquivalentCopy(), 2));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(crouch, 1));

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FireTackle().makeStatEquivalentCopy(), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new FireSomersault().makeStatEquivalentCopy(), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(somer, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new VolcanoSlider().makeStatEquivalentCopy(), 1));
    }
}
