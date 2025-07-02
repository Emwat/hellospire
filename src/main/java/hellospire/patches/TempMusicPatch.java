package hellospire.patches;

import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.character.Sonic;

@SpirePatch(clz = TempMusic.class, method = "getSong")
public class TempMusicPatch {
    @SpirePostfixPatch
    public static Music Postfix(Music __result, TempMusic __instance, String key) {
        // case "SHOP":
        // case "SHRINE":
        // case "MINDBLOOM":
        // case "BOSS_BOTTOM":
        // case "BOSS_CITY":
        // case "BOSS_BEYOND":
        // case "BOSS_ENDING":
        // case "ELITE":
        // case "CREDITS":
        // mp3 is fine here, but since I'm editing the sound files anyway for a better loop, I will work with ogg

        if (!(AbstractDungeon.player instanceof Sonic)){
            return __result;
        }

        if (MyModConfig.enableSound && "STS_BossVictoryStinger_3_v3_MUSIC.ogg".equals(key)) {
            return MainMusic.newMusic(SonicMod.audioPath("music/ActClear.mp3"));
        }

        if (MyModConfig.enableBoss2Music && "BOSS_CITY".equals(key)) {
            return MainMusic.newMusic(SonicMod.audioPath("music/Egg_Emperor.ogg"));
        }

        if (MyModConfig.enableBoss3Music && "BOSS_BEYOND".equals(key)) {
            return MainMusic.newMusic(SonicMod.audioPath("music/Egg_Dragoon.ogg"));
        }

        if (MyModConfig.enableBossHeartMusic && "BOSS_ENDING".equals(key)) {
            return MainMusic.newMusic(SonicMod.audioPath("music/Big_Arms.ogg"));
        }
        return __result;
    }
}