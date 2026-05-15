package theHedgehog.modachievements;


import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import javassist.CtBehavior;
import theHedgehog.packsA.SonicStylePack;
import theHedgehog.packsA.SonicDizzySpinPack;
import thePackmaster.SpireAnniversary5Mod;
import thePackmaster.ThePackmaster;
import thePackmaster.packs.AbstractCardPack;

import static theHedgehog.util.UnlockUtil.unlockModAchievement;

@SpirePatch(
        clz = VictoryScreen.class,
        method = "update",
        requiredModId = "anniv5"
)
public class SonicAllStarsPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(VictoryScreen __instance) {
        if (Loader.isModLoaded("ModAchievement")) {
            if (AbstractDungeon.player.chosenClass.equals(ThePackmaster.Enums.THE_PACKMASTER) && !SpireAnniversary5Mod.allPacksMode) {
                for (AbstractCardPack p : SpireAnniversary5Mod.currentPoolPacks) {
                    if (p.packID.equals(SonicStylePack.ID) || p.packID.equals(SonicDizzySpinPack.ID)) {
                        unlockModAchievement(achievements.Achievement.SonicAllStars.name());
                    }
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ReturnToMenuButton.class, "hide");
            int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
            return lines;
        }
    }
}