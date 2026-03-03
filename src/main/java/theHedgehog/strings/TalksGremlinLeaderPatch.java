package theHedgehog.strings;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.beyond.TimeEater;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;


public class TalksGremlinLeaderPatch {

    // @SpirePatch(clz = TimeEater.class, method = SpirePatch.CLASS)
    // public static class TimeEaterExtraDialog {
    //     public static SpireField<String[]> extraDialog = new SpireField(() -> CardCrawlGame.languagePack.getMonsterStrings(makeID("TalksPatchTimeEater")).DIALOG);
    // }

    @SpirePatch(clz = GremlinLeader.class, method = "takeTurn")
    public static class GremlinLeaderDialog {

        @SpirePostfixPatch
        public static void Postfix(GremlinLeader __instance) {
            float talkDuration = 3;

            if (!(AbstractDungeon.player instanceof Sonic)) {
                return;
            }

            if (!Sonic.isSonic()) {
                return;
            }

            SonicTalkStrings dialogues = SonicMod.modLocalizedStrings.getTalkString(makeID("Gremlin Leader"));
            String[] sonicReplies = dialogues.DIALOG;
            String[] GremlinLeaderSays = dialogues.DIALOG_1;
            int randomNumber = MathUtils.random(2);

            if (__instance.nextMove == 3) {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, sonicReplies[randomNumber], talkDuration, talkDuration));
            }

        }
    }

}