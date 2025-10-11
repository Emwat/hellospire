package hellospire.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.character.SonicTipTracker;
import hellospire.util.ModMultiPageFtue;
import hellospire.util.TextureLoader;

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
