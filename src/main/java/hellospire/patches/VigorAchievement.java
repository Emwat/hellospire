package hellospire.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hellospire.MyModConfig;

import static hellospire.SonicMod.makeID;

public class VigorAchievement {
    @SpirePatch(clz = AbstractPower.class, method = "stackPower")
    public static class UnlockAchievementPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPower __instance, int stackAmount) {
            if (!MyModConfig.enableCrossModIntegrations) {
                return;
            }

            if (Loader.isModLoaded("ModAchievement") &&
                    __instance instanceof VigorPower &&
                    __instance.amount >= 100) {
                UnlockTracker.unlockAchievement(makeID("VigorAbuse"));
            }
        }
    }
}
