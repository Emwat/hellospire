// package hellospire.effects;
//
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
// import com.megacrit.cardcrawl.vfx.combat.SmokeBlurEffect;
//
// public class RunForwardEffect extends AbstractGameEffect {
//     private static float DURATION = 0.8f;
//     private static float DESTINATION = 250f * Settings.scale;
//     private float x;
//     private float y;
//
//     public RunForwardEffect(float x, float y) {
//         this.x = x;
//         this.y = y;
//         duration = DURATION;
//     }
//
//     public void update() {
//         duration -= Gdx.graphics.getDeltaTime();
//         float progress = 1f - duration / DURATION;
//         if (duration <= 0f) {
//             AbstractDungeon.player.animX = 0f;
//             isDone = true;
//             return;
//         }
//         AbstractDungeon.player.animX = (float)Math.sin(progress * Math.PI) * DESTINATION;
//     }
//
//     public void render(SpriteBatch sb) {
//     }
//
//     public void dispose() {
//     }
// }
