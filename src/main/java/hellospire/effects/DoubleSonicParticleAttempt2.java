// package hellospire.effects;
//
// import basemod.animations.AbstractAnimation;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.g2d.TextureAtlas;
// import com.badlogic.gdx.math.MathUtils;
// import com.brashmonkey.spriter.Point;
// import com.esotericsoftware.spine.*;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.core.AbstractCreature;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
// import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//
// public class DoubleSonicParticleAttempt2 extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
//     public static SkeletonMeshRenderer sr;
//     private static int W;
//     public AnimationState state;
//     public AbstractPlayer p;
//     private float scale = 1.5F;
//     private Texture img;
//     private float x;
//     private float px;
//     private AbstractCreature.CreatureAnimation animation;
//     private float y;
//
//     public DoubleSonicParticleAttempt2(AbstractPlayer p) {
//
//         this.duration = 0.05F;
//         this.p = p;
//         this.px = p.hb.cX;
//         this.x = ((p.hb.cX - W / 2.0F) + (100 * Settings.scale));
//         this.y = ((p.hb.cY - W / 2.0F) - (95 * Settings.scale));
//         this.renderBehind = true;
//         // this.animationA.renderModel(batch, env);
//         // BaseMod.publishAnimationRender(sb);
//     }
//
//
//     public void dispose() {
//         this.isDone = true;
//     }
//
//     public void update() {
//
//
//     }
//
//     public void finish() {
//         this.isDone = true;
//
//     }
//
//
//     public void render(SpriteBatch sb) {
//         this.drawer.batch = batch;
//
//         for(this.frameRegulator += Gdx.graphics.getDeltaTime(); this.frameRegulator - 0.016666668F >= 0.0F; this.frameRegulator -= 0.016666668F) {
//             this.myPlayer.update();
//         }
//
//         AbstractPlayer player = AbstractDungeon.player;
//         if (player != null) {
//             this.myPlayer.setPosition(new Point(x, y));
//             this.drawer.draw(this.myPlayer);
//             if (drawBones) {
//                 batch.end();
//                 this.renderer.setAutoShapeType(true);
//                 this.renderer.begin();
//                 this.drawer.drawBoxes(this.myPlayer);
//                 this.drawer.drawBones(this.myPlayer);
//                 this.renderer.end();
//                 batch.begin();
//             }
//         }
//     }
//
//
// }