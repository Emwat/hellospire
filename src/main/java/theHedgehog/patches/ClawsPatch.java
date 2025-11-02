package theHedgehog.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.defect.GashAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicTags;

@SpirePatch(clz = GashAction.class, method = "update")
public class ClawsPatch {
    @SpirePrefixPatch
    public static void Prefix(GashAction __instance) {
        int instanceAmount = __instance.amount;

        IncreaseClawDamageForSonicClaws(instanceAmount, AbstractDungeon.player.discardPile);
        IncreaseClawDamageForSonicClaws(instanceAmount, AbstractDungeon.player.drawPile);
        IncreaseClawDamageForSonicClaws(instanceAmount, AbstractDungeon.player.exhaustPile);
        IncreaseClawDamageForSonicClaws(instanceAmount, AbstractDungeon.player.hand);
    }

    private static void IncreaseClawDamageForSonicClaws(int instanceAmount, CardGroup group) {
        if (!group.isEmpty()) {
            for (AbstractCard c : group.group) {
                if (c.hasTag(SonicTags.CLAW)) {
                    c.baseDamage += instanceAmount;
                    c.applyPowers();
                    if (group.type == CardGroup.CardGroupType.HAND) {
                        c.superFlash();
                    }
                }
            }
        }
    }
}