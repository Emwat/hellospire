package hellospire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.ReflectionHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import hellospire.SonicMod;
import hellospire.powers.LevelUpFlightPower;
import hellospire.powers.RingPower;

public class FlightFocusPatch {
    @SpirePatch(clz = AbstractOrb.class, method = "applyFocus", paramtypez = {})
    public static class applyFlightPowerPatch {
        @SpirePostfixPatch
        public static void applyFlightPowerPatchThing(AbstractOrb __instance) {
            AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
            AbstractPower flight = AbstractDungeon.player.getPower(LevelUpFlightPower.POWER_ID);
            AbstractPower ring = AbstractDungeon.player.getPower(RingPower.POWER_ID);

            int basePassiveAmount = ReflectionHacks.getPrivate(__instance, AbstractOrb.class, "basePassiveAmount");
            int baseEvokeAmount = ReflectionHacks.getPrivate(__instance, AbstractOrb.class, "baseEvokeAmount");

            if (!__instance.ID.equals("Plasma")) {
                int focusAmount = focus != null ? focus.amount : 0;
                int flightAmount = flight != null ? flight.amount : 0;
                flightAmount = ring == null ? 0 : flightAmount * ring.amount;

                __instance.passiveAmount = Math.max(0, basePassiveAmount + focusAmount + flightAmount);
                __instance.evokeAmount = Math.max(0, baseEvokeAmount + focusAmount + flightAmount);
            } else {
                __instance.passiveAmount = basePassiveAmount;
                __instance.evokeAmount = baseEvokeAmount;
            }
        }
    }
}

