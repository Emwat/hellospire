package theHedgehog.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.TextureLoader;

import static theHedgehog.SonicMod.makeID;

public class ChiliDogPatch {
    private static final String folder = "cards/colorless/";

    private static boolean playerIsSonic(){
        if (Loader.isModLoaded("GooglyMod")) {
            return true;
        }

        return !(AbstractDungeon.player instanceof Sonic);
        // return false;
    }

    private static void ModPatch(AbstractCard __instance, Texture cardImg, String cardName){
        if (playerIsSonic()) {
            return;
        }

        __instance.name = cardName;
        __instance.originalName = cardName;
        ModPatch(__instance, cardImg);
    }

    private static void ModPatch(AbstractCard __instance, Texture cardImg){
        if (playerIsSonic()) {
            return;
        }

        __instance.portrait = new TextureAtlas.AtlasRegion(cardImg, 0, 0, cardImg.getWidth(), cardImg.getHeight());
        // __instance.jokePortrait = __instance.portrait;
    }

    @SpirePatch(clz = Apotheosis.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Apo {
        @SpirePostfixPatch
        public static void postfix(Apotheosis __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Apo.png"))
            );
        }
    }

    @SpirePatch(clz = Apparition.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Apparition {
        @SpirePostfixPatch
        public static void postfix(Apparition __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Apparition.png"))
            );
        }
    }

    @SpirePatch(clz = BandageUp.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_BandageUp {
        @SpirePostfixPatch
        public static void postfix(BandageUp __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "ChiliDog.png")),
                    CardCrawlGame.languagePack.getCardStrings(makeID("ColorlessBandageUp")).NAME
            );
        }
    }

    @SpirePatch(clz = Blind.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Blind {
        @SpirePostfixPatch
        public static void postfix(Blind __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Blind.png")));
        }
    }

    @SpirePatch(clz = Chrysalis.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Chry {
        @SpirePostfixPatch
        public static void postfix(Chrysalis __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Chry.png")));
        }
    }

    @SpirePatch(clz = DarkShackles.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_DarkShackles {
        @SpirePostfixPatch
        public static void postfix(DarkShackles __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath("cards/skill/Enerbeam.png")),
                    CardCrawlGame.languagePack.getCardStrings(makeID("Enerbeam")).NAME
                    );
        }
    }

    @SpirePatch(clz = DeepBreath.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_DeepBreath {
        @SpirePostfixPatch
        public static void postfix(DeepBreath __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "DeepBreath.png")));
        }
    }

    @SpirePatch(clz = DramaticEntrance.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_DramaticEntrance {
        @SpirePostfixPatch
        public static void postfix(DramaticEntrance __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "DramaticEntrance.png"))
            );

        }
    }

    @SpirePatch(clz = FlashOfSteel.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_FlashOfSteel {
        @SpirePostfixPatch
        public static void postfix(FlashOfSteel __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "FlashOfSteel.png")));
        }
    }

    @SpirePatch(clz = GoodInstincts.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_GoodInstincts {
        @SpirePostfixPatch
        public static void postfix(GoodInstincts __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "GoodInstincts.png")));
        }
    }

    @SpirePatch(clz = HandOfGreed.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_HandOfGreed {
        @SpirePostfixPatch
        public static void postfix(HandOfGreed __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "HandOfGreed.png")));
        }
    }

    @SpirePatch(clz = Impatience.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Impatience {
        @SpirePostfixPatch
        public static void postfix(Impatience __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath("cards/skill/AssistRosy.png")),
                    CardCrawlGame.languagePack.getCardStrings(makeID("AssistRosy")).NAME
                    );
        }
    }

    @SpirePatch(clz = MasterOfStrategy.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_MasterOfStrategy {
        @SpirePostfixPatch
        public static void postfix(MasterOfStrategy __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "MasterOfStrategy.png"))
            );
        }
    }

    @SpirePatch(clz = Mayhem.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Mayhem {
        @SpirePostfixPatch
        public static void postfix(Mayhem __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Mayhem.png")));
        }
    }

    @SpirePatch(clz = Metamorphosis.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Metamorph {
        @SpirePostfixPatch
        public static void postfix(Metamorphosis __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Metamorph.png")));
        }
    }

    @SpirePatch(clz = MindBlast.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_MindBlast {
        @SpirePostfixPatch
        public static void postfix(MindBlast __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "MindBlast.png")));
        }
    }

    @SpirePatch(clz = SecretTechnique.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_SecretTechnique {
        @SpirePostfixPatch
        public static void postfix(SecretTechnique __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "SecretTechnique.png"))
            );
        }
    }


    @SpirePatch(clz = SecretWeapon.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_SecretWeapon {
        @SpirePostfixPatch
        public static void postfix(SecretWeapon __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "SecretWeapon.png"))
            );

        }
    }

    @SpirePatch(clz = Shiv.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Shiv {
        @SpirePostfixPatch
        public static void postfix(Shiv __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Shiv.png")),
                    CardCrawlGame.languagePack.getCardStrings(makeID("ColorlessShiv")).NAME
            );

        }
    }

    @SpirePatch(clz = TheBomb.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_TheBomb {
        @SpirePostfixPatch
        public static void postfix(TheBomb __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "TheBomb.png"))
            );

        }
    }

    @SpirePatch(clz = Transmutation.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Transmutation {
        @SpirePostfixPatch
        public static void postfix(Transmutation __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Transmutation.png")));
        }
    }

    @SpirePatch(clz = Trip.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Trip {
        @SpirePostfixPatch
        public static void postfix(Trip __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Trip.png"))
            );

        }
    }

    @SpirePatch(clz = Violence.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Violence {
        @SpirePostfixPatch
        public static void postfix(Violence __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Violence.png"))
            );
        }
    }



}

