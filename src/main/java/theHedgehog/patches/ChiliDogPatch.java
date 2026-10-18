package theHedgehog.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.TextureLoader;

import java.util.HashMap;
import java.util.Map;

import static theHedgehog.SonicMod.makeID;

/**
 * Gives Sonic his own art (and, in English, names) for vanilla colorless and status cards.
 *
 * This used to be 43 near-identical constructor patches that each called
 * TextureLoader.getTexture(...) as an argument, i.e. BEFORE checking the player. The game
 * constructs every card at startup with no player, so ~43 textures were loaded at launch for
 * everyone. Now there is one patch and a table, and a texture is only loaded once we know the
 * player is Sonic.
 */
public class ChiliDogPatch {
    private static final String folder = "cards/colorless/";

    private static class Entry {
        final String image;
        /** ID (unprefixed) of the mod's own CardStrings whose NAME replaces the vanilla name, or null. */
        final String nameId;

        Entry(String image, String nameId) {
            this.image = image;
            this.nameId = nameId;
        }
    }

    private static final Map<Class<?>, Entry> ENTRIES = new HashMap<>();

    private static void add(Class<? extends AbstractCard> cls, String image, String nameId) {
        ENTRIES.put(cls, new Entry(image, nameId));
    }

    static {
        add(Apotheosis.class, folder + "Apo.png", null);
        add(Apparition.class, folder + "Apparition.png", null);
        add(Bite.class, folder + "Bite.png", null);
        add(BandageUp.class, folder + "ChiliDog.png", "ColorlessBandageUp");
        add(Blind.class, folder + "Blind.png", null);
        add(Burn.class, folder + "Burn.png", null);
        add(Chrysalis.class, folder + "Chry.png", null);
        add(DarkShackles.class, "cards/skill/Enerbeam.png", "Enerbeam");
        add(Dazed.class, folder + "Dazed.png", null);
        add(DeepBreath.class, folder + "DeepBreath.png", null);
        add(Discovery.class, folder + "Discovery.png", null);
        add(DramaticEntrance.class, folder + "DramaticEntrance.png", null);
        add(Enlightenment.class, folder + "Enlightenment.png", null);
        add(Finesse.class, folder + "Finesse.png", null);
        add(FlashOfSteel.class, folder + "FlashOfSteel.png", null);
        add(Forethought.class, folder + "Forethought.png", null);
        add(GoodInstincts.class, folder + "GoodInstincts.png", null);
        add(HandOfGreed.class, folder + "HandOfGreed.png", null);
        add(JackOfAllTrades.class, folder + "JackOfAllTrades.png", null);
        add(Madness.class, folder + "Madness.png", null);
        add(Impatience.class, "cards/skill/AssistRosy.png", "AssistRosy");
        add(Magnetism.class, folder + "Magnetism.png", null);
        add(MasterOfStrategy.class, folder + "MasterOfStrategy.png", null);
        add(Mayhem.class, folder + "Mayhem.png", null);
        add(Metamorphosis.class, folder + "Metamorph.png", null);
        add(MindBlast.class, folder + "MindBlast.png", null);
        add(Panacea.class, folder + "Panacea.png", null);
        add(Panache.class, folder + "Panache.png", null);
        add(PanicButton.class, folder + "PanicButton.png", null);
        add(Purity.class, folder + "Purity.png", null);
        add(SadisticNature.class, folder + "SadisticNature.png", null);
        add(SecretTechnique.class, folder + "SecretTechnique.png", null);
        add(SecretWeapon.class, folder + "SecretWeapon.png", null);
        add(Shiv.class, folder + "Kunai.png", "ColorlessShiv");
        add(Slimed.class, folder + "Slimed.png", null);
        add(SwiftStrike.class, folder + "SwiftStrike.png", null);
        add(TheBomb.class, folder + "TheBomb.png", null);
        add(ThinkingAhead.class, folder + "ThinkingAhead.png", null);
        add(Transmutation.class, folder + "Transmutation.png", null);
        add(Trip.class, folder + "Trip.png", null);
        add(Violence.class, folder + "Violence.png", null);
        add(Wound.class, folder + "Wound.png", null);
        add(VoidCard.class, folder + "Void.png", null);
    }

    private static boolean playerIsSonic() {
        if (Loader.isModLoaded("GooglyMod")) {
            return false;
        }

        return AbstractDungeon.player instanceof Sonic;
    }

    private static boolean isStatusCardArtDisabledFor(AbstractCard card) {
        return !MyModConfig.enableStatusCardArt && (card instanceof Burn ||
                card instanceof Dazed ||
                card instanceof Slimed ||
                card instanceof Wound ||
                card instanceof VoidCard);
    }

    @SpirePatches({
            @SpirePatch(clz = Apotheosis.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Apparition.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Bite.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = BandageUp.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Blind.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Burn.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Chrysalis.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = DarkShackles.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Dazed.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = DeepBreath.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Discovery.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = DramaticEntrance.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Enlightenment.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Finesse.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = FlashOfSteel.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Forethought.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = GoodInstincts.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = HandOfGreed.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = JackOfAllTrades.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Madness.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Impatience.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Magnetism.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = MasterOfStrategy.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Mayhem.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Metamorphosis.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = MindBlast.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Panacea.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Panache.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = PanicButton.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Purity.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = SadisticNature.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = SecretTechnique.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = SecretWeapon.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Shiv.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Slimed.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = SwiftStrike.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = TheBomb.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = ThinkingAhead.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Transmutation.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Trip.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Violence.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = Wound.class, method = SpirePatch.CONSTRUCTOR),
            @SpirePatch(clz = VoidCard.class, method = SpirePatch.CONSTRUCTOR)
    })
    public static class ApplySonicFlavorPatch {
        @SpirePostfixPatch
        public static void postfix(AbstractCard __instance) {
            // Cheapest checks first, and nothing is loaded unless this is Sonic.
            Entry entry = ENTRIES.get(__instance.getClass());
            if (entry == null || !playerIsSonic()) {
                return;
            }

            if (entry.nameId != null && SonicMod.hasFullLocalization()) {
                String cardName = CardCrawlGame.languagePack.getCardStrings(makeID(entry.nameId)).NAME;
                __instance.name = cardName;
                __instance.originalName = cardName;
            }

            if (isStatusCardArtDisabledFor(__instance)) {
                return;
            }

            Texture cardImg = TextureLoader.getTexture(SonicMod.imagePath(entry.image));
            __instance.portrait = new TextureAtlas.AtlasRegion(cardImg, 0, 0, cardImg.getWidth(), cardImg.getHeight());
        }
    }
}
