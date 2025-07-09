package hellospire.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.TextureLoader;

public class ChiliDogPatch {
    @SpirePatch(clz = BandageUp.class, method = SpirePatch.CONSTRUCTOR)
    public static class applyChiliDogImagePatch {
        @SpirePostfixPatch
        public static void applyChiliDogImagePatch(BandageUp __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return;
            }

            String cardName = "Chili Dog";
            Texture cardImg = TextureLoader.getTexture(SonicMod.imagePath("cards/skill/ChiliDog.png"));
            // Texture cardImgP = TextureLoader.getTexture(SonicMod.imagePath("cards/skill/ChiliDog_P.png"));

            __instance.name = cardName;
            __instance.originalName = cardName;
            __instance.portrait = new TextureAtlas.AtlasRegion(cardImg, 0, 0, cardImg.getWidth(), cardImg.getHeight());
            // __instance.jokePortrait = __instance.portrait;
        }
    }
}

