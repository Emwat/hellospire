package hellospire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.relics.Lantern;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.TextureLoader;

public class RelicImagePatch {
    @SpirePatch(clz = IceCream.class, method = SpirePatch.CONSTRUCTOR)
    public static class iceCreamImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(IceCream __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.img = TextureLoader.getTexture(SonicMod.imagePath("relics/IceCreamSonic.png"));
        }
    }

    @SpirePatch(clz = Lantern.class, method = SpirePatch.CONSTRUCTOR)
    public static class lanternImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(Lantern __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.flavorText = "Blast Off: Press X/Square when \"GO!\" appears for a free Boost.";
            __instance.img = TextureLoader.getTexture(SonicMod.imagePath("relics/BlastOffRelic.png"));
        }
    }
}

