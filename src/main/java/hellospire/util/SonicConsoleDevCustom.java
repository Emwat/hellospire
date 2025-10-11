package hellospire.util;

import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.cards.Crouch;
import hellospire.cards.FireSomersault;
import hellospire.cards.FireTackle;
import hellospire.cards.VolcanoSlider;

// valid commands:
// s25

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
