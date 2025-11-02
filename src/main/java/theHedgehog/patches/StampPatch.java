package theHedgehog.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class StampPatch {
    public static SpireField<Boolean> inBottledStamp = new SpireField<>(() -> false);

    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )

    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            if (isInCombat()) {
                inBottledStamp.set(__result, inBottledStamp.get(__instance));
            }

            if (!isInCombat()) {
                inBottledStamp.set(__result, Boolean.FALSE);
            }

            return __result;
        }

        static private boolean isInCombat() {
            return CardCrawlGame.isInARun() &&
                    AbstractDungeon.currMapNode != null &&
                    AbstractDungeon.getCurrRoom() != null &&
                    AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
        }
    }
}