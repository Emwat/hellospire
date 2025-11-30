package theHedgehog.strings;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Champ;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

@SpirePatch(clz = Champ.class, method = "usePreBattleAction")
public class TalksChampPatch {
    @SpirePrefixPatch
    public static void Prefix(Champ __instance) {
        if (!(AbstractDungeon.player instanceof Sonic)){
            return;
        }

        if (!Sonic.currentModSkin.getName().contains("Sonic")) {
            return;
        }

        String[] sonicReplies = CardCrawlGame.languagePack.getCharacterString(makeID("TheHedgehog")).TEXT;

        ArrayList<String> derp = new ArrayList<>();
        derp.add("What is a @LOUSE@ doing here?");
        derp.add("The Exordium is the @OTHER@ way.");

        int randomNumber = MathUtils.random(derp.size() - 1);
        String taunt =  derp.get(randomNumber);
        float talkDuration = 5;

        if (randomNumber == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CHAMP_2A"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(__instance, taunt, talkDuration, talkDuration));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(3F));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, sonicReplies[6], talkDuration, talkDuration));
        } else if (randomNumber == 1) {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, sonicReplies[7], talkDuration, talkDuration));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(3F));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CHAMP_2A"));
            AbstractDungeon.actionManager.addToBottom(new TalkAction(__instance, taunt, talkDuration, talkDuration));
        }
    }
}

// 06:23:29.841 INFO BlueHedgehog> Defensive Stance
// 06:23:29.842 INFO BlueHedgehog> Execute
// 06:23:29.842 INFO BlueHedgehog> Face Slap
// 06:23:29.842 INFO BlueHedgehog> NAMEThe Champ
// 06:23:29.842 INFO BlueHedgehog> DIALOG
// 06:23:29.842 INFO BlueHedgehog> You call that a weapon?
// 06:23:29.842 INFO BlueHedgehog> Come at me!
// 06:23:29.842 INFO BlueHedgehog> Do your worst! NL @HAHAHA!@
// 06:23:29.842 INFO BlueHedgehog> Have a free shot! NL Futile weakling!
// 06:23:29.843 INFO BlueHedgehog> ~You've~ ~done~ ~it~ ~now...~
// 06:23:29.843 INFO BlueHedgehog> @DEFEAT??@ NL @IMPOSSIBLE!!@
// 06:23:29.843 INFO BlueHedgehog> ~DIE~ ~.~ ~.~ ~.~
// 06:23:29.843 INFO BlueHedgehog> Face my wrath!
// 06:23:29.843 INFO BlueHedgehog> @THAT'S@ NL @MY@ @BELT!!@
