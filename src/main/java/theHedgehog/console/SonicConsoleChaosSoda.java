package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.potions.ChaosSodaPotion;

import java.util.ArrayList;

// valid commands:
// chaossoda

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleChaosSoda extends ConsoleCommand {
    public SonicConsoleChaosSoda() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (tokens.length > 1 && tokens[1].equals("0")) {
            ReplacePotsWithChaosSoda();
        } else {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float) Settings.HEIGHT / 2.0F, new PotionBelt());
            // NotPerfect: spawnRelicAndObtain is too slow, so ReplacePotsWithSoda ends up only covering the initial potion slots
            ReplacePotsWithChaosSoda();
        }
    }

    private void ReplacePotsWithChaosSoda(){
        for (int j = 0; j < AbstractDungeon.player.potionSlots; j++) {
            boolean isEmptyPotionSlot = AbstractDungeon.player.potions.get(j) instanceof PotionSlot;
            if (isEmptyPotionSlot) {
                AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(PotionHelper.getPotion(ChaosSodaPotion.ID)));
            } else {
                AbstractDungeon.player.removePotion(AbstractDungeon.player.potions.get(j));
                AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(PotionHelper.getPotion(ChaosSodaPotion.ID)));
            }
        }
    }

}
