package theHedgehog.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.character.SonicTipTracker;
import theHedgehog.util.ModEggmanAnnounceFtue;
import theHedgehog.util.ModMultiPageFtue;
import theHedgehog.util.TextureLoader;

import java.util.ArrayList;
import java.util.Arrays;


@SpirePatch(clz = EmptyDeckShuffleAction.class, method = SpirePatch.CONSTRUCTOR)
public class AnnouncementPatch {
    @SpirePostfixPatch
    public static void applyAnnouncementPatch(EmptyDeckShuffleAction __instance) {
        if (!(AbstractDungeon.player instanceof Sonic)){
            return;
        }

        ArrayList<String> announcements = new ArrayList<>();
        if (!(Boolean) SonicTipTracker.tips.get(SonicTipTracker.Version092)) {
            announcements.add(SonicTipTracker.Version092);
            SonicTipTracker.neverShowAgain(SonicTipTracker.Version092);
        }

        if (Loader.isModLoaded("spireTogether") && !(Boolean) SonicTipTracker.tips.get(SonicTipTracker.Version098)) {
            announcements.add(SonicTipTracker.Version098);
            SonicTipTracker.neverShowAgain(SonicTipTracker.Version098);
        }

        if (!announcements.isEmpty()) {
            AbstractDungeon.ftue = new ModEggmanAnnounceFtue(announcements);
        }

    }



}
