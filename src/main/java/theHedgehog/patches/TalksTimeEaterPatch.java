package theHedgehog.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import theHedgehog.character.Sonic;

import java.util.ArrayList;


public class TalksTimeEaterPatch {

    // @SpirePatch(clz = TimeEater.class, method = SpirePatch.CLASS)
    // public static class TimeEaterExtraDialog {
    //     public static SpireField<String[]> extraDialog = new SpireField(() -> CardCrawlGame.languagePack.getMonsterStrings(makeID("TalksPatchTimeEater")).DIALOG);
    // }

    @SpirePatch(clz = TimeEater.class, method = "takeTurn")
    public static class TimeEaterDialog {

        @SpirePrefixPatch
        public static void Prefix(TimeEater __instance, @ByRef boolean[] ___firstTurn) {
            float talkDuration = 5;

            if (!(AbstractDungeon.player instanceof Sonic)) {
                return;
            }

            if (!Sonic.currentModSkin.getName().contains("Sonic")) {
                return;
            }

            ArrayList<String> sonicDialog = new ArrayList<>();
            sonicDialog.add("Did I keep you waiting?");
            sonicDialog.add("Fastest Thing Alive vs The Time Eater!");

            ArrayList<String> rivalDialog = new ArrayList<>();
            rivalDialog.add("You've messed with time... NL @FOR@ @TOO@ @LONG!@");
            rivalDialog.add("~The~ ~Stage...~ NL ~is~ ~Set...~");
            int randomNumber = MathUtils.random(rivalDialog.size() - 1);

            if (___firstTurn[0]) {
                // String[] dialog = TimeEaterExtraDialog.extraDialog.get(__instance);

                AbstractDungeon.actionManager.addToBottom(new TalkAction(__instance, rivalDialog.get(randomNumber), talkDuration, talkDuration));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, sonicDialog.get(randomNumber), talkDuration, talkDuration));
                ___firstTurn[0] = false;
            }

        }
    }

}