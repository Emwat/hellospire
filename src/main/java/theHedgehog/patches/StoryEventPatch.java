// package hellospire.patches;
//
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.TextureAtlas;
// import com.evacipated.cardcrawl.modthespire.Loader;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
// import com.megacrit.cardcrawl.cards.colorless.BandageUp;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.localization.TutorialStrings;
// import com.megacrit.cardcrawl.rewards.RewardItem;
// import com.megacrit.cardcrawl.ui.FtueTip;
// import com.megacrit.cardcrawl.unlock.UnlockTracker;
// import hellospire.SonicMod;
// import hellospire.character.Sonic;
// import hellospire.character.SonicTipTracker;
// import hellospire.util.TextureLoader;
//
// public class StoryEventPatch {
//     @SpirePatch(clz = RewardItem.class, method = "claimReward")
//     public static class applyStoryEventPatch {
//         @SpirePostfixPatch
//         public static void applyRubyEventPatch(RewardItem __instance) {
//             if (Loader.isModLoaded("togetherSpire") || !(AbstractDungeon.player instanceof Sonic)){
//                 return;
//             }
//
//             if (__instance.type == RewardItem.RewardType.SAPPHIRE_KEY) {
//                 String eventName = "Tails";
//                 TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Tails 00");
//                 String[] MSG = tutorialStrings.TEXT;
//                 String[] LABEL = tutorialStrings.LABEL;
//
//                 if (!SonicTipTracker.tips.get("TAILS_00")) {
//                     AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, FtueTip.TipType.SHUFFLE);
//                     SonicTipTracker.neverShowAgain("TAILS_00");
//                 } else if (!SonicTipTracker.tips.get("TAILS_01")) {
//                     AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, FtueTip.TipType.SHUFFLE);
//                     SonicTipTracker.neverShowAgain("TAILS_01");
//                 }
//             }
//         }
//     }
// }
//
