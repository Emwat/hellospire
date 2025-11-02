package theHedgehog.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.character.SonicTipTracker;
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

        if (!(Boolean) SonicTipTracker.tips.get(SonicTipTracker.Version092)) {
            AbstractDungeon.ftue = new ModMultiPageFtue(SonicTipTracker.Version092, new ArrayList<>(Arrays.asList(new Texture[]{
                    TextureLoader.getTexture(SonicMod.imagePath("events/EggmanAnnounce.png"))
            })));
            SonicTipTracker.neverShowAgain(SonicTipTracker.Version092);
        }

    }



}
