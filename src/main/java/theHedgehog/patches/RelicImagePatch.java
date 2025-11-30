package theHedgehog.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.relics.Lantern;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.TextureLoader;

public class RelicImagePatch {
    @SpirePatch(clz = BottledTornado.class, method = SpirePatch.CONSTRUCTOR)
    public static class bottledTornadoImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(BottledTornado __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.flavorText = CardCrawlGame.languagePack.getRelicStrings(SonicMod.makeID("ModBottledTornado")).FLAVOR;
            __instance.img = TextureLoader.getTexture(SonicMod.relicPath("BottledTornadoRelic.png"));
        }
    }

    @SpirePatch(clz = IceCream.class, method = SpirePatch.CONSTRUCTOR)
    public static class iceCreamImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(IceCream __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.flavorText = CardCrawlGame.languagePack.getRelicStrings(SonicMod.makeID("ModIceCreamRelic")).FLAVOR;
            __instance.img = TextureLoader.getTexture(SonicMod.relicPath("IceCreamSonic.png"));
        }
    }

    @SpirePatch(clz = Lantern.class, method = SpirePatch.CONSTRUCTOR)
    public static class lanternImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(Lantern __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.flavorText = CardCrawlGame.languagePack.getRelicStrings(SonicMod.makeID("ModLanternRelic")).FLAVOR;
            __instance.img = TextureLoader.getTexture(SonicMod.relicPath("BlastOffRelic.png"));
        }
    }

    @SpirePatch(clz = DuVuDoll.class, method = SpirePatch.CONSTRUCTOR)
    public static class duvuImagePatch {
        @SpirePostfixPatch
        public static void applyImagePatch(DuVuDoll __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }
            __instance.flavorText = CardCrawlGame.languagePack.getRelicStrings(SonicMod.makeID("ModTailsDoll")).FLAVOR;
            __instance.img = TextureLoader.getTexture(SonicMod.relicPath("TailsDollRelic.png"));
        }
    }
}

