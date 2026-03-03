package theHedgehog.strings;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;


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

            SonicTalkStrings dialogues = SonicMod.modLocalizedStrings.getTalkString(makeID("Time Eater"));
            String[] sonicReplies = dialogues.DIALOG;
            String[] timeEaterSays = dialogues.DIALOG_0;
            int randomNumber = MathUtils.random(sonicReplies.length - 1);

            if (___firstTurn[0]) {
                // String[] dialog = TimeEaterExtraDialog.extraDialog.get(__instance);

                AbstractDungeon.actionManager.addToBottom(new TalkAction(__instance, timeEaterSays[randomNumber], talkDuration, talkDuration));
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, sonicReplies[randomNumber], talkDuration, talkDuration));
                ___firstTurn[0] = false;
            }

        }
    }

}