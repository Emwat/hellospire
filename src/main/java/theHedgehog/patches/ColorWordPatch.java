// package theHedgehog.patches;
//
// import basemod.ReflectionHacks;
// import com.badlogic.gdx.graphics.Color;
// import com.evacipated.cardcrawl.modthespire.lib.*;
// import com.megacrit.cardcrawl.helpers.FontHelper;
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
//             if (word.length() >= 2 &&  word.charAt(0) == '#') {
//                 switch (word.charAt(1)) {
//                     case 'o': {
//                         return SpireReturn.Return(Color.BROWN.cpy());
//                     }
//                     case 'c': {
//                         return SpireReturn.Return(Color.CYAN.cpy());
//                     }
//                 }
//             }
//             return SpireReturn.Continue();
//         }
//
//     }
// }