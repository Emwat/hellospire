package theHedgehog.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.TextureLoader;

import static theHedgehog.SonicMod.makeID;

public class ChiliDogPatch {
    private static final String folder = "cards/colorless/";

    private static boolean playerIsSonic(){
        if (Loader.isModLoaded("GooglyMod")) {
            return false;
        }

        // return true;
        return AbstractDungeon.player instanceof Sonic;
    }

    private static void ModPatch(AbstractCard __instance, Texture cardImg, String cardName){
        if (!playerIsSonic()) {
            return;
        }

        __instance.name = cardName;
        __instance.originalName = cardName;
        ModPatch(__instance, cardImg);
    }

    private static void ModPatch(AbstractCard __instance, Texture cardImg){
        if (!playerIsSonic()) {
            return;
        }

        if (!MyModConfig.enableStatusCardArt) {
            if (__instance instanceof Burn ||
                    __instance instanceof Dazed ||
                    __instance instanceof Slimed ||
                    __instance instanceof Wound ||
                    __instance instanceof VoidCard
            ) {
                return;
            }
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

    @SpirePatch(clz = Bite.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Bite {
        @SpirePostfixPatch
        public static void postfix(Bite __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Bite.png"))
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

    @SpirePatch(clz = Burn.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Burn {
        @SpirePostfixPatch
        public static void postfix(Burn __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Burn.png")));
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

    @SpirePatch(clz = Dazed.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Dazed {
        @SpirePostfixPatch
        public static void postfix(Dazed __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder +"Dazed.png")));
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

    @SpirePatch(clz = Discovery.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Discovery {
        @SpirePostfixPatch
        public static void postfix(Discovery __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Discovery.png"))
            );

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

    @SpirePatch(clz = Enlightenment.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Enlightenment {
        @SpirePostfixPatch
        public static void postfix(Enlightenment __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Enlightenment.png"))
            );

        }
    }

    @SpirePatch(clz = Finesse.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Finesse {
        @SpirePostfixPatch
        public static void postfix(Finesse __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Finesse.png"))
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

    @SpirePatch(clz = Forethought.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Forethought {
        @SpirePostfixPatch
        public static void postfix(Forethought __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Forethought.png"))
            );

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

    @SpirePatch(clz = JackOfAllTrades.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_JackOfAllTrades {
        @SpirePostfixPatch
        public static void postfix(JackOfAllTrades __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "JackOfAllTrades.png")));
        }
    }

    @SpirePatch(clz = Madness.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Madness {
        @SpirePostfixPatch
        public static void postfix(Madness __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Madness.png")));
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

    @SpirePatch(clz = Magnetism.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Magnetism {
        @SpirePostfixPatch
        public static void postfix(Magnetism __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Magnetism.png"))
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

    @SpirePatch(clz = Panacea.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Panacea {
        @SpirePostfixPatch
        public static void postfix(Panacea __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Panacea.png")));
        }
    }

    @SpirePatch(clz = Panache.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Panache {
        @SpirePostfixPatch
        public static void postfix(Panache __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Panache.png")));
        }
    }

    @SpirePatch(clz = PanicButton.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_PanicButton {
        @SpirePostfixPatch
        public static void postfix(PanicButton __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "PanicButton.png")));
        }
    }

    @SpirePatch(clz = Purity.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Purity {
        @SpirePostfixPatch
        public static void postfix(Purity __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Purity.png")));
        }
    }

    @SpirePatch(clz = SadisticNature.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_SadisticNature {
        @SpirePostfixPatch
        public static void postfix(SadisticNature __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "SadisticNature.png")));
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
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Kunai.png")),
                    CardCrawlGame.languagePack.getCardStrings(makeID("ColorlessShiv")).NAME
            );

        }
    }

    @SpirePatch(clz = Slimed.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Slimed {
        @SpirePostfixPatch
        public static void postfix(Slimed __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Slimed.png"))
            );

        }
    }

    @SpirePatch(clz = SwiftStrike.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_SwiftStrike {
        @SpirePostfixPatch
        public static void postfix(SwiftStrike __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "SwiftStrike.png"))
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

    @SpirePatch(clz = ThinkingAhead.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_ThinkingAhead {
        @SpirePostfixPatch
        public static void postfix(ThinkingAhead __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "ThinkingAhead.png")));
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

    @SpirePatch(clz = Wound.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_Wound {
        @SpirePostfixPatch
        public static void postfix(Wound __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Wound.png"))
            );
        }
    }

    @SpirePatch(clz = VoidCard.class, method = SpirePatch.CONSTRUCTOR)
    public static class applySonicFlavorPatch_VoidCard {
        @SpirePostfixPatch
        public static void postfix(VoidCard __instance) {
            ModPatch(__instance,
                    TextureLoader.getTexture(SonicMod.imagePath(folder + "Void.png")));
        }
    }


}

