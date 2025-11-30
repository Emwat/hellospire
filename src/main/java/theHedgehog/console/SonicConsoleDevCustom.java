package theHedgehog.console;

import basemod.devcommands.ConsoleCommand;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHedgehog.cards.*;
import theHedgehog.cardsTails.IQ200Attack;
import theHedgehog.cardsTails.IQ300Attack;
import theHedgehog.cardsTails.IQ400Attack;
import theHedgehog.cardsTails.MagicHook;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.PowerBrakeRelic;

import java.util.ArrayList;

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

        TailsTest();
        // HomingAttackBranchTest();
        // LoopDeLoopTest();
        // LightSpeedAttackTest();
        // TopKickTest();
        // DizzyTest();
        // FireTest();
    }

    private void TailsTest() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new TopKick().makeStatEquivalentCopy());
        cards.add(new IQ200Attack().makeStatEquivalentCopy());
        cards.add(new IQ300Attack().makeStatEquivalentCopy());
        cards.add(new IQ400Attack().makeStatEquivalentCopy());
        cards.add(new MagicHook().makeStatEquivalentCopy());
        for (AbstractCard c : cards) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
        }
    }

    private void HomingAttackBranchTest() {
        AbstractCard homing = new HomingAttack().makeStatEquivalentCopy();
        ((BranchingUpgradesCard) homing).doBranchUpgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(homing, 1));
    }

    private void LoopDeLoopTest() {
        AbstractCard loop1 = new LoopDeLoop().makeStatEquivalentCopy();
        AbstractCard loop2 = new LoopDeLoop().makeStatEquivalentCopy();
        loop2.upgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(loop1, 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(loop2, 1));
    }

    private void LightSpeedAttackTest() {
        AbstractCard lsa = new LightSpeedAttack().makeStatEquivalentCopy();
        AbstractCard ring = new Ring();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, 2, false)));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(ring.makeStatEquivalentCopy(), 4, false, true));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(ring.makeStatEquivalentCopy(), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(ring.makeStatEquivalentCopy(), 4));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(lsa, 1));
    }

    private void TopKickTest() {
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

    private void DizzyTest() {
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

    private void FireTest() {
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
