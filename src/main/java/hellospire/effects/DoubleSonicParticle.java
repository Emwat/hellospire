// package slimebound.vfx;
//
//
// import basemod.animations.AbstractAnimation;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.g2d.TextureAtlas;
// import com.badlogic.gdx.math.MathUtils;
// import com.brashmonkey.spriter.Player;
// import com.esotericsoftware.spine.*;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
// import com.megacrit.cardcrawl.core.AbstractCreature;
// import com.megacrit.cardcrawl.core.CardCrawlGame;
// import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.helpers.SlimeAnimListener;
// import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
//         private float scale = 1.5F;
//     private Texture img;
//     private float x;
//     private float px;
//     private float y;
//     private CustomSpriterAnimation animation;
//     private Sonic p;
//
//
//     public DoubleSonicParticle(AbstractPlayer p) {
//         if (!(p instanceof Sonic)) {
//             return;
//         }
//
//         Sonic sonic = (Sonic) p;
//         this.animation = new CustomSpriterAnimation(characterPath("animation/SonicBattlePose.scml")); // Animation
//
//         Player.PlayerListener listener = new CustomAnimationListener(sonic);
//         ((Sonic) p).getAnimation().myPlayer.addListener(listener);
//
//         this.p = sonic;
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
//
//
//         if (this.atlas == null) {
//             sb.setColor(new Color(1F, 1F, 1F, 2F));
//             sb.draw(this.img, this.x - (float) this.img.getWidth() * Settings.scale / 2.0F, this.y + AbstractDungeon.sceneOffsetY, (float) this.img.getWidth() * Settings.scale, (float) this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
//         } else {
//             this.state.update(Gdx.graphics.getDeltaTime());
//             this.state.apply(this.skeleton);
//             this.skeleton.updateWorldTransform();
//             this.skeleton.setPosition(this.x, this.y + AbstractDungeon.sceneOffsetY);
//             this.skeleton.setColor(new Color(1F, 1F, 1F, 2F));
//             // this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
//             sb.end();
//             CardCrawlGame.psb.begin();
//             AbstractMonster.sr.draw(CardCrawlGame.psb, this.skeleton);
//             CardCrawlGame.psb.end();
//             sb.begin();
//             sb.setBlendFunction(770, 771);
//
//
//         }
//     }
//
//
// }