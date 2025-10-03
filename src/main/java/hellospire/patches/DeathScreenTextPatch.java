package hellospire.patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.colorless.BandageUp;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.TextureLoader;

import java.util.ArrayList;

public class DeathScreenTextPatch {
    @SpirePatch(clz = DeathScreen.class, method = "getDeathText")
    public static class changeDeathTextMessagePatch {
        @SpirePrefixPatch
        public static SpireReturn<String> changeDeathTextMessagePatch(DeathScreen __instance) {
            if (!(AbstractDungeon.player instanceof Sonic)){
                return SpireReturn.Continue();
            }

            ArrayList<String> list = new ArrayList<>();
            list.add("This is not happening.");
            list.add("It's not your day.");
            list.add("E Rank");
            list.add("Better luck next time.");
            list.add("You can hear a heartbeat pulsing very loudly.");
            list.add("Don't give up Sonic! -Amy");
            list.add("Someone murdered my darling Sonic! -Amy");
            list.add("What would Sonic do? ...No, he wouldn't do that. Let me try again. -Barry");
            list.add("Want to go fishing? -Big");
            list.add("Ooh! I bet I could do it. Let me try! Let me try! -Charmy");
            list.add("Sonic, you must live. -Chip");
            list.add("You don't belong here! -Cultist");
            list.add("*wants to cry but doesn't* -Cream");
            list.add("Focus your spirit! -Espio");
            list.add("Farewell! Sonic the hedgehog! -Eggman");
            list.add("You are unworthy! -Knuckles");
            list.add("Hmph! So YOU'RE supposed to be the fastest thing alive? -Jet");
            list.add("The dirt suits you so well! -Jet");
            list.add("I guess he was just a regular hedgehog after all. -Shadow");
            list.add("It's no use! -Silver");
            list.add("How could someone like YOU cause the destruction of our world? -Silver");
            list.add("Sonic!!! -Tails");

            return SpireReturn.Return(list.get(MathUtils.random(list.size() - 1)));
        }
    }
}
