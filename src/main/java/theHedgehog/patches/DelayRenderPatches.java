// package theHedgehog.patches;
//
// // https://github.com/AutumnMooncat/Snowpunk/blob/master/src/main/java/Snowpunk/patches/DelayRenderPatches.java
//
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.evacipated.cardcrawl.modthespire.lib.*;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.rooms.AbstractRoom;
// import javassist.CannotCompileException;
// import javassist.expr.ExprEditor;
// import javassist.expr.MethodCall;
//
// public class DelayRenderPatches {
//     @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
//     public static class DelayRenderField {
//         public static SpireField<Boolean> delay = new SpireField<>(() -> false);
//     }
//
//     public static boolean isDelayed() {
//         return DelayRenderField.delay.get(AbstractDungeon.player);
//     }
//
//     public static void delayRender() {
//         DelayRenderField.delay.set(AbstractDungeon.player, true);
//     }
//
//     public static void resumeRender() {
//         DelayRenderField.delay.set(AbstractDungeon.player, false);
//     }
//
//     @SpirePatch2(clz = AbstractRoom.class, method = "render")
//     public static class PlayerRenderDelay {
//         @SpirePostfixPatch
//         public static void doRender(SpriteBatch sb) {
//             if (isDelayed()) {
//                 AbstractDungeon.player.render(sb);
//             }
//         }
//
//         @SpireInstrumentPatch
//         public static ExprEditor plz() {
//             return new ExprEditor() {
//                 @Override
//                 public void edit(MethodCall m) throws CannotCompileException {
//                     if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("render")) {
//                         m.replace("if(!Snowpunk.patches.DelayRenderPatches.isDelayed()) {$proceed($$);}");
//                     }
//                 }
//             };
//         }
//     }
// }