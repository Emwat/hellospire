package theHedgehog.character;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.Arrays;

public class SonicStartTalkingHelper {
    static public void Chat(AbstractRoom room) {

        if (!(AbstractDungeon.player instanceof Sonic)) {
            return;
        }

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
                "GiantHead",
                "Hexaghost",
                "SlimeBoss",
                "TheGuardian"
        ));

        if (elites.contains(monsterName)) {
            switch (monsterName) {
                case "GremlinNob":
                    if (room.monsters.monsters.size() == 1) {
                        if (Sonic.currentModSkin.getName().contains("Sonic")) {
                            say(new ArrayList<>(Arrays.asList(
                                    "You're no match for me!",
                                    "Ha! You look just like this knucklehead I know.",
                                    "Hey you. Get out of my way!"
                            )));
                        }
                    } else {
                        if (Sonic.currentModSkin.getName().contains("Sonic")) {
                            say("Bring it on!");
                        }
                    }
                    break;
                case "Lagavulin":
                    if (Sonic.currentModSkin.getName().contains("Sonic")) {
                        say(new ArrayList<>(Arrays.asList(
                                "Is that a giant egg?",
                                "Sorry, but you have to go!"
                        )));
                    }
                    break;
                case "Sentry":
                    if ("Sentry".equals(room.monsters.getMonsterNames().get(1))) {
                        if (Sonic.currentModSkin.getName().contains("Sonic")) {
                            say(new ArrayList<>(Arrays.asList(
                                    "Let's rock!",
                                    "Time to party!"
                            )));
                        }
                    }
                    break;
                // case "TheGuardian":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 "Whoa!",
                //                 "Aw yeah! This could be fun!"
                //         )));
                //     }
                //     break;
                // case "Hexaghost":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 "Whoa!",
                //                 "Aw yeah! This could be fun!"
                //         )));
                //     }
                //     break;
                // case "SlimeBoss":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 "Aw yeah! This could be fun!",
                //                 "Aw yeah! This could be fun!",
                //                 "(Thunder, Rain, and Lightning!!)"
                //         )));
                //     }
                //     break;
                // case "SlaverBoss":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 ""
                //         )));
                //     }
                //     break;
                // case "BookOfStabbing":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 ""
                //         )));
                //     }
                //     break;
                // case "GremlinLeader":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 ""
                //         )));
                //     }
                //     break;
                // case "Reptomancer":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 ""
                //         )));
                //     }
                //     break;
                // case "Nemesis":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 ""
                //         )));
                //     }
                //     break;
                // case "GiantHead":
                //     if (Sonic.currentModSkin.getName().contains("Sonic")) {
                //         say(new ArrayList<>(Arrays.asList(
                //                 "Hey look! A giant talking head!",
                //                 ""
                //         )));
                //     }
                //     break;

            }
        }

    }

    static float duration = 3F;
    static float bubbleDuration = 3F;

    private static void say(String text) {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }

    private static void say(ArrayList<String> texts) {
        String text = texts.get(AbstractDungeon.miscRng.random(0, texts.size() - 1));

        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }
}
