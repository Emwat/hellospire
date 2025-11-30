package theHedgehog.strings;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;

public class SonicTalkEncounters {
    static public void Chat(AbstractRoom room) {

        if (!(AbstractDungeon.player instanceof Sonic)) {
            return;
        }

        String monsterEncounter = AbstractDungeon.lastCombatMetricKey; // See MonsterHelper for more info
        SonicTalkStrings dialogs = SonicMod.modLocalizedStrings.getTalkString(AbstractDungeon.player.id + ":" + monsterEncounter);

        if (dialogs == null) {
            return;
        }

        if (dialogs.DIALOG.length == 1){
            say(dialogs.DIALOG[0]);
        } else {
            say(dialogs.DIALOG);
        }
    }

    static float duration = 3F;
    static float bubbleDuration = 3F;

    private static void say(String text) {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }

    private static void say(String[] texts) {
        String text = texts[AbstractDungeon.miscRng.random(0, texts.length - 1)];

        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, text, duration, bubbleDuration));
    }
}
