package theHedgehog.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.MyModConfig;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;

@SpirePatch(clz = MainMusic.class, method = "getSong")
public class AudioMainMusicPatch {
    // Prefix + SpireReturn so the vanilla Music object is never created (a postfix replacing
    // __result left that stream undisposed).
    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(MainMusic __instance, String key) {
        // Exordium
        // TheCity
        // TheBeyond
        // TheEnding
        // MENU

        if (!(AbstractDungeon.player instanceof Sonic)){
            return SpireReturn.Continue();
        }

        if (MyModConfig.enableBossSpearShieldMusic && "TheEnding".equals(key)) {
            return SpireReturn.Return(MainMusic.newMusic(SonicMod.audioPath("music/Biolizard_Supporting_Me.ogg")));
        }
        return SpireReturn.Continue();
    }
}