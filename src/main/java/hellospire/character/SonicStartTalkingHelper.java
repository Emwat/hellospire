package hellospire.character;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.SoundLibrary;

import java.util.ArrayList;
import java.util.Arrays;

public class SonicStartTalkingHelper {
    static public void Chat(AbstractRoom room) {
        String monsterName = room.monsters.getMonsterNames().get(0);

        ArrayList<String> elites = new ArrayList<>(Arrays.asList(
                "GremlinNob",
                "Lagavulin",
                "Sentry",
                "SlaverBoss",
                "BookOfStabbing",
                "GremlinLeader",
                "Reptomancer",
                "Nemesis",
                "GiantHead"
        ));

        if (elites.contains(monsterName)) {
            switch (monsterName) {
                case "GremlinNob":
                    say("Hey you. Get out of my way!");
                    break;
                case "Lagavulin":
                    say("Is that a giant egg?");
                    break;
                case "Sentry":
                    say("I remember... fighting these before...");
                    break;
            }
        }

    }

    static float duration = 3F;
    static float bubbleDuration = 3F;

    private static void say(String text){
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }

    private static void say(ArrayList<String> texts){
        String text = texts.get(AbstractDungeon.miscRng.random(0, texts.size() - 1));

        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }
}
