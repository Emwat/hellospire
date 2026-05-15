// package theHedgehog.modachievements;
//
//
// import com.evacipated.cardcrawl.modthespire.Loader;
// import com.evacipated.cardcrawl.modthespire.lib.*;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.screens.VictoryScreen;
// import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
// import javassist.CtBehavior;
// import theHedgehog.packsA.SonicStylePack;
// import theHedgehog.packsB.SonicDizzySpinPack;
// import theHedgehog.relics.CDPastRelic;
// import theHedgehog.relics.ClassicModeRelic;
// import thePackmaster.SpireAnniversary5Mod;
// import thePackmaster.ThePackmaster;
// import thePackmaster.packs.AbstractCardPack;
//
// import static theHedgehog.util.UnlockUtil.unlockModAchievement;
//
// @SpirePatch(
//         clz = VictoryScreen.class,
//         method = "update",
//         requiredModId = "ModAchievement"
//         )
// public class SonicKingOfRingsPatch {
//     @SpireInsertPatch(
//             locator = Locator.class
//     )
//     public static void Insert(VictoryScreen __instance) {
//         if (AbstractDungeon.player.hasRelic(CDPastRelic.ID) || AbstractDungeon.player.hasRelic(ClassicModeRelic.ID)) {
//             unlockModAchievement(achievements.Achievement.KingOfRings.name());
//         }
//     }
//
//
//     private static class Locator extends SpireInsertLocator {
//         @Override
//         public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
//             Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ReturnToMenuButton.class, "hide");
//             int[] lines = LineFinder.findAllInOrder(ctMethodToPatch, methodCallMatcher);
//             return lines;
//         }
//     }
// }