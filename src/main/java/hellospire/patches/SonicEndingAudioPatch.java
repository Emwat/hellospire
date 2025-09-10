// package hellospire.patches;
//
// import basemod.ReflectionHacks;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import hellospire.SonicMod;
// import hellospire.SoundLibrary;
// import hellospire.character.Sonic;
//
// public class SonicEndingAudioPatch {
//
//     @SpirePatch(clz = CutscenePanel.class, method = "activate")
//     public static class applySonicEndingPatch {
//         @SpirePrefixPatch
//         public static SpireReturn<Void> applyEndingSoundPatch(CutscenePanel __instance) {
//             if (!(AbstractDungeon.player instanceof Sonic)) {
//                 return SpireReturn.Continue();
//             }
//
//             String sfx = ReflectionHacks.getPrivate(__instance, String.class, "sfx");
//
//             SonicMod.logger.info("sfx is " + sfx + " | cond is " + SoundLibrary.TheHedgehog.equals(sfx));
//             if (SoundLibrary.TheHedgehog.equals(sfx)) {
//                 CardCrawlGame.sound.play(sfx);
//
//                 return SpireReturn.Return();
//             }
//             return SpireReturn.Continue();
//         }
//     }
// }
//
