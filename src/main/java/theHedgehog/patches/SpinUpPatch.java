package theHedgehog.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import theHedgehog.SonicTags;

@SpirePatch(clz = ConfusionPower.class, method = "onCardDraw")
public class SpinUpPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractPower __instance, AbstractCard card) {
        int oldCost = card.cost;
        if (card.hasTag(SonicTags.SPIN_UP) && card.cost >= 0) {
            int newCost = Math.max(0, card.cost - 1);
            card.cost = newCost;
            card.costForTurn = newCost;
            card.isCostModified = false;
        }
    }
}