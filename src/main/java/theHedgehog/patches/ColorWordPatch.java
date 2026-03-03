// 02/24/2026 03:19 AM This works. See HexColorOptionPatch for the attempt at using Locator
// package theHedgehog.patches;
//
// import basemod.ReflectionHacks;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.evacipated.cardcrawl.modthespire.lib.*;
// import com.megacrit.cardcrawl.cards.DamageInfo;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.characters.Ironclad;
// import com.megacrit.cardcrawl.helpers.FontHelper;
// import com.megacrit.cardcrawl.helpers.TipHelper;
// import theHedgehog.SonicMod;
//
// public class ColorWordPatch {
//     @SpirePatch(
//             clz = FontHelper.class,
//             method = "identifyColor"
//     )
//     public static class FontHelperIdentifyColorPatch {
//
//         @SpirePrefixPatch
//         public static SpireReturn<Color> Prefix(String word) {
//             // [f3acd2ff]
//             if (word.length() >= 10 && word.charAt(0) == '[' && word.charAt(9) == ']') {
//                 // Need to be careful of words like [Continue]
//                 if (isNumeric(word.substring(1, 9)))
//                     return SpireReturn.Return(Color.valueOf(word.substring(1, 9)));
//             }
//
//             // if (word.length() >= 2 &&  word.charAt(0) == '#') {
//             //     switch (word.charAt(1)) {
//             //         case 'o': {
//             //             return SpireReturn.Return(Color.BROWN.cpy());
//             //         }
//             //         case 'c': {
//             //             return SpireReturn.Return(Color.CYAN.cpy());
//             //         }
//             //     }
//             // }
//             return SpireReturn.Continue();
//             }
//     }
//
//
//     @SpirePatch(clz = FontHelper.class, method = "renderSmartText", paramtypez = {
//             SpriteBatch.class,
//             BitmapFont.class,
//             String.class,
//             float.class,
//             float.class,
//             float.class,
//             float.class,
//             Color.class
//     })
//     public static class HexCode {
//         @SpireInsertPatch(
//                 loc = 955,
//                 localvars = {"word"}
//         )
//         public static void Insert(SpriteBatch sb,
//                                   BitmapFont font,
//                                   String msg,
//                                   float x,
//                                   float y,
//                                   float lineWidth,
//                                   float lineSpacing,
//                                   Color baseColor,
//                                   @ByRef String[] word) {
//
//             if (word[0].length() >= 10 && word[0].charAt(0) == '[' && word[0].charAt(9) == ']') {
//                 // Need to be careful of words like [Continue]
//                 if (isNumeric(word[0].substring(1, 9))) {
//                     word[0] = word[0].substring(8);
//                 }
//             }
//
//         }
//     }
//
//     private static boolean isNumeric(String cadena) {
//         if (cadena.length() == 0 ||
//                 (cadena.charAt(0) != '-' && Character.digit(cadena.charAt(0), 16) == -1))
//             return false;
//         if (cadena.length() == 1 && cadena.charAt(0) == '-')
//             return false;
//
//         for (int i = 1; i < cadena.length(); i++)
//             if (Character.digit(cadena.charAt(i), 16) == -1)
//                 return false;
//         return true;
//     }
// }