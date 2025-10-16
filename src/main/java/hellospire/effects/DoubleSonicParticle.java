// package hellospire.effects;
//
//
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.math.MathUtils;
// import com.brashmonkey.spriter.Player;
// import com.esotericsoftware.spine.*;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.helpers.ImageMaster;
// import hellospire.CustomAnimationListener;
// import hellospire.CustomSpriterAnimation;
// import hellospire.character.Sonic;
//
// import static hellospire.SonicMod.characterPath;
//
//
// public class DoubleSonicParticle extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
//     public static SkeletonMeshRenderer sr;
//     private static int W;
//     private float scale = 1.5F;
//     private Texture img;
//     private float x;
//     private float px;
//     private float y;
//     private CustomSpriterAnimation animation;
//     public Player.PlayerListener listener;
//     public Sonic sonic;
//
//     public DoubleSonicParticle(AbstractPlayer player) {
//         if (!(player instanceof Sonic)) {
//             return;
//         }
//
//         this.sonic = (Sonic) player;
//         this.animation = new CustomSpriterAnimation(characterPath("animation/blue/SonicBattlePose.scml")); // Animation
//         this.img = ImageMaster.loadImage(characterPath("animation/blue/SonicBattleNormal.png"));
//         listener = new CustomAnimationListener(sonic);
//         sonic.getAnimation().myPlayer.addListener(listener);
//         this.duration = 0.05F;
//         this.px = player.hb.cX;
//         this.x = ((player.hb.cX - W / 2.0F) - (75 * Settings.scale));
//         this.y = ((player.hb.cY - W / 2.0F) - (95 * Settings.scale));
//         this.renderBehind = true;
//     }
//
//     public void dispose() {
//         this.isDone = true;
//     }
//
//     public void update() {
//
//     }
//
//     public void finish() {
//         this.isDone = true;
//     }
//
//     public void render(SpriteBatch sb) {
//         sb.setColor(new Color(1F, 1F, 1F, 2F));
//         sb.draw(this.img,
//                 this.x - (float) this.img.getWidth() * Settings.scale / 2.0F,
//                 this.y + AbstractDungeon.sceneOffsetY,
//                 (float) this.img.getWidth() * Settings.scale,
//                 (float) this.img.getHeight() * Settings.scale,
//                 0, 0,
//                 this.img.getWidth(),
//                 this.img.getHeight(),
//                 false, false);
//     }
//
//
// }