package hellospire.packsPatches;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.powers.RingPower;

@SpirePatch(clz = AddTemporaryHPAction.class, method = "update")
public class OnTempHPPatch {

    // TODO: Make this faster.
    @SpirePostfixPatch
    public static void PostPatchTempHPForBoost(AddTemporaryHPAction __instance) {
        if (__instance.isDone && Loader.isModLoaded("anniv5") && AbstractDungeon.player != null && AbstractDungeon.player.hasPower(RingPower.POWER_ID)) {
            RingPower ringPower = (RingPower) AbstractDungeon.player.getPower(RingPower.POWER_ID);
            ringPower.DiscardBoostsToHand();
        }

    }

}