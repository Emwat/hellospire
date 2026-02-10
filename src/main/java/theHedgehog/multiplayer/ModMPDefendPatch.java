package theHedgehog.multiplayer;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import spireTogether.other.MPSkillsPatches;
import theHedgehog.cards.Defend;

public class ModMPDefendPatch {
    @SpirePatch2(clz = MPSkillsPatches.class, method = "IsUpgradedMPDefend", requiredModId = "spireTogether", optional = true)
    public static class Inserter {
        public static boolean Postfix(AbstractCard o, boolean __result) {
            if (!__result && o.upgraded) {
                if (o instanceof Defend)
                    return true;
            }
            return __result;
        }
    }
}
