package hellospire.patches;

import basemod.abstracts.CustomUnlock;
import basemod.abstracts.CustomUnlockBundle;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import hellospire.MyModConfig;
import hellospire.SonicMod;
import hellospire.character.Sonic;

// @SpirePatch(clz = CustomUnlockBundle.class, method = SpirePatch.CONSTRUCTOR, paramtypez={
//         AbstractUnlock.UnlockType.class,
//         String.class,
//         String.class,
//         String.class})
// public class debugPatch {
//     @SpirePostfixPatch
//     public static void Postfix(CustomUnlockBundle __instance, AbstractUnlock.UnlockType type, String unlock1, String unlock2, String unlock3) {
//         SonicMod.logger.info("type: " + type);
//         SonicMod.logger.info("unlock1 " + unlock1);
//         SonicMod.logger.info("unlock2 " + unlock2);
//         SonicMod.logger.info("unlock3 " + unlock3);
//     }
// }

//
// @SpirePatch(clz = CustomUnlock.class, method = SpirePatch.CONSTRUCTOR, paramtypez={
//         AbstractUnlock.UnlockType.class,
//         String.class})
// public class debugPatch {
//     @SpirePrefixPatch
//     public static void LogSomethingPls(CustomUnlock __instance, AbstractUnlock.UnlockType type, String id) {
//         SonicMod.logger.info("type: " + type);
//         SonicMod.logger.info("id " + id);
//     }
// }