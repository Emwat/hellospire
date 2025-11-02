package theHedgehog.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DeathScreen;
import theHedgehog.character.Sonic;

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
            if (Sonic.currentModSkin.getName().contains("Shadow")) {
                list.add("I promise you... REVENGE.");
                list.add("Throw it all away!");
                list.add("You live an endless life forever!");
                list.add("You have to face it again and again!");
                list.add("We all danced in fire.");
                list.add("Trapped in this machine.");
                list.add("As the Eggman watches.");
                list.add("A tragic mystery.");
                list.add("A shadow of myself. Just who am I?");
                list.add("Scan horizons.");
                list.add("Walk into my mystery.");
                list.add("Capture you or set you free.");
                list.add("Do you remember me?");
                list.add("Somewhere in chaos we'll all find ourselves.");
                list.add("This destruction is the only tale we tell.");
                list.add("White is black and black is white.");
                list.add("Right is wrong and wrong is right.");
                list.add("One step forward, two steps back.");
                list.add("Somewhere in between.");
                list.add("Well we're almost dead.");
                list.add("Only through trial do we find the strength we need.");
                list.add("Heroes rise again.");
                list.add("Got a hundred thousand pounds sitting on my back.");
            } else {
                list.add("This is not happening.");
                list.add("It's not your day.");
                list.add("E Rank");
                list.add("Better luck next time.");
                list.add("*Hits car* Pause. Restart. *Hits car again* -City E.");
                list.add("You can hear a heartbeat pulsing very loudly.");
                list.add("Every world has its end.");
                list.add("Are you sure you don't need my help? It looks like you could use it. -Amy");
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
                list.add("Unworthy! -Knuckles");
                list.add("Hmph! So YOU'RE supposed to be the fastest thing alive? -Jet");
                list.add("The dirt suits you so well! -Jet");
                list.add("I guess he was just a regular hedgehog after all. -Shadow");
                list.add("It's no use! -Silver");
                list.add("How could someone like YOU cause the destruction of our world? -Silver");
                list.add("Sonic!!! -Tails");
            }

            return SpireReturn.Return(list.get(MathUtils.random(list.size() - 1)));
        }
    }
}
