package theHedgehog.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

public class DeathScreenTextPatch {
    private static final String[] DeathScreenSonic = CardCrawlGame.languagePack.getUIString(makeID("DeathScreenSonic")).TEXT;
    private static final String[] DeathScreenTails = CardCrawlGame.languagePack.getUIString(makeID("DeathScreenTails")).TEXT;
    private static final String[] DeathScreenKnuckles = CardCrawlGame.languagePack.getUIString(makeID("DeathScreenKnuckles")).TEXT;
    private static final String[] DeathScreenShadow = CardCrawlGame.languagePack.getUIString(makeID("DeathScreenShadow")).TEXT;

    @SpirePatch(clz = DeathScreen.class, method = "getDeathText")
    public static class changeDeathTextMessagePatch {
        @SpirePrefixPatch
        public static SpireReturn<String> changeDeathTextMessagePatch(DeathScreen __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return SpireReturn.Continue();
            }


            if (Sonic.isTails()) {
                return SpireReturn.Return(DeathScreenTails[MathUtils.random(DeathScreenTails.length - 1)]);
            } else if (Sonic.isKnuckles()) {
                return SpireReturn.Return(DeathScreenKnuckles[MathUtils.random(DeathScreenKnuckles.length - 1)]);
            } else if (Sonic.isShadow()) {
                return SpireReturn.Return(DeathScreenShadow[MathUtils.random(DeathScreenShadow.length - 1)]);
            } else {
                return SpireReturn.Return(DeathScreenSonic[MathUtils.random(DeathScreenSonic.length - 1)]);
            }
        }
    }
}
