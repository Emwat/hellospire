package theHedgehog.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import javassist.CtBehavior;

import static theHedgehog.util.GeneralUtils.getColorOrDefault;
import static theHedgehog.util.GeneralUtils.isNumeric;

public class HexColorOptionPatch {
    @SpirePatch(
            clz = FontHelper.class,
            method = "identifyColor"
    )
    public static class FontHelperIdentifyColorPatch {
        @SpirePrefixPatch
        public static SpireReturn<Color> Prefix(String word) {
            Color output = getColorOrDefault(word);
            if (output != null) {
                return SpireReturn.Return(output);
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(clz = FontHelper.class, method = "renderSmartText", paramtypez = {
            SpriteBatch.class,
            BitmapFont.class,
            String.class,
            float.class,
            float.class,
            float.class,
            float.class,
            Color.class
    })
    public static class HexCode {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"word"}
        )
        public static void InsertAfterIdentifyColor(SpriteBatch sb,
                                                    BitmapFont font,
                                                    String msg,
                                                    float x,
                                                    float y,
                                                    float lineWidth,
                                                    float lineSpacing,
                                                    Color baseColor,
                                                    @ByRef String[] word) {


            if (word[0].length() >= 9 && word[0].startsWith("[#") && word[0].charAt(8) == ']') {
                if (isNumeric(word[0].substring(2, 8))) {
                    word[0] = word[0].substring(7);
                }
            } else if (word[0].length() >= 10 && word[0].charAt(0) == '[' && word[0].charAt(9) == ']') {
                // Need to be careful of words like [Continue]
                if (isNumeric(word[0].substring(1, 9))) {
                    word[0] = word[0].substring(8);
                }
            }


        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "identifyColor");
                int[] output = LineFinder.findInOrder(ctBehavior, matcher);
                output[0]++;
                return output;
            }
        }
    }


}