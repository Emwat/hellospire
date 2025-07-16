package hellospire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import hellospire.relics.*;
import hellospire.ui.EvolveChaoOption;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;


import java.util.ArrayList;

// https://github.com/erasels/PackmasterCharacter/blob/main/src/main/java/thePackmaster/patches/gemspack/AddEnhanceButtonPatch.java
public class AddEvolveChaoButtonPatch {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class AddKeys {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(CampfireUI __instance, ArrayList<AbstractCampfireOption> ___buttons) {
            if (!AbstractDungeon.player.hasRelic(ChaoRelic.ID) ||
                    AbstractDungeon.player.hasRelic(ChaoIroncladRelic.ID) ||
                    AbstractDungeon.player.hasRelic(ChaoSilentRelic.ID) ||
                    AbstractDungeon.player.hasRelic(ChaoDefectRelic.ID) ||
                    AbstractDungeon.player.hasRelic(ChaoWatcherRelic.ID)
            ) {
                return;
            }

            ___buttons.add(new EvolveChaoOption());
        }

        @SpireInstrumentPatch
        public static ExprEditor fixProceedLogicPatch() {
            return new FixProceedLogicExprEditor();
        }

        // We want the Enhance option to be ignored by the proceed logic since it's optional and you can still use other
        // options after it. To achieve this, we have the Enhance option always treated as unusable by this logic.
        public static class FixProceedLogicExprEditor extends ExprEditor {
            @Override
            public void edit(FieldAccess fieldAccess) throws CannotCompileException {
                if (fieldAccess.getClassName().equals(AbstractCampfireOption.class.getName()) && fieldAccess.getFieldName().equals("usable") && fieldAccess.isReader()) {
                    fieldAccess.replace(String.format("{ $_ = $proceed($$) && !($0 instanceof %1$s); }", EvolveChaoOption.class.getName()));
                }
            }
        }

    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "relics");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }
}