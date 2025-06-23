package hellospire.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.character.Sonic;

@SpirePatch(clz = MainMusic.class, method = "getSong")
public class MainMusicPatch {
    @SpirePostfixPatch
    public static Music Postfix(Music __result, MainMusic __instance, String key) {
        // Exordium
        // TheCity
        // TheBeyond
        // TheEnding
        // MENU

        if (!(AbstractDungeon.player instanceof Sonic)){
            return __result;
        }

        if (MyModConfig.enableBossSpearShieldMusic && "TheEnding".equals(key)) {
            return MainMusic.newMusic(SonicMod.audioPath("music/Biolizard_Supporting_Me.mp3"));
        }
        return __result;
    }
}