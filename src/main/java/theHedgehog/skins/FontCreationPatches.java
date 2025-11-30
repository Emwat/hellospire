package theHedgehog.skins;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.FontHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class FontCreationPatches {
    private static final float TIP_FONT = 17.0F;
    public static BitmapFont tipFont;
    public static BitmapFont smallerTitleFont;
    public static BitmapFont biggerTitleFont;

    @SpirePatch(
            clz = FontHelper.class,
            method = "initialize"
    )
    public static class AddMyFont {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void patch() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method method = FontHelper.class.getDeclaredMethod("prepFont", Float.TYPE, Boolean.TYPE);
            method.setAccessible(true);
            tipFont = (BitmapFont)method.invoke(null, 17.0F, false);
        }

        @SpireInsertPatch(
                locator = Locator2.class
        )
        public static void patch2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method method = FontHelper.class.getDeclaredMethod("prepFont", Float.TYPE, Boolean.TYPE);
            method.setAccessible(true);
            smallerTitleFont = (BitmapFont)method.invoke(null, 22.0F, false);
            biggerTitleFont = (BitmapFont)method.invoke(null, 28.0F, false);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(FontHelper.class, "cardDescFont_N");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        private static class Locator2 extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(FontHelper.class, "cardTitleFont");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
