package theHedgehog.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.TextureLoader;

public class SuperFinisherEffect extends AbstractGameEffect {
    private final float durationEnd;
    private float x;
    private float y;
    private static float textureWidth;
    private static float textureHeight;
    private static Texture texture;
    private static Texture blackScreen;

    public SuperFinisherEffect(AbstractCard.CardTags tag) {

        // TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
        blackScreen = TextureLoader.getTexture(SonicMod.characterPath("overkill/black.png"));

        if (Sonic.isKnuckles()) {
            if (tag == SonicTags.ERA_CLASSIC) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/classic.png");
                textureWidth = 558;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_ADVENTURE) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/adventure.png");
                textureWidth = 836;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_MODERN) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/modern.png");
                textureWidth = 588;
                textureHeight = 768;
            }
        } else if (Sonic.isShadow()) {
            if (tag == SonicTags.ERA_CLASSIC) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/classic.png");
                textureWidth = 611;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_ADVENTURE) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/adventure.png");
                textureWidth = 479;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_MODERN) {
                texture = TextureLoader.getTexture(Sonic.currentModSkin.getCharacterPath() + "/overkill/modern.png");
                textureWidth = 858;
                textureHeight = 768;
            }
        } else {
            if (tag == SonicTags.ERA_CLASSIC) {
                texture = TextureLoader.getTexture(SonicMod.characterPath("overkill/classic.png"));
                textureWidth = 540;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_ADVENTURE) {
                texture = TextureLoader.getTexture(SonicMod.characterPath("overkill/adventure.png"));
                textureWidth = 616;
                textureHeight = 768;
            } else if (tag == SonicTags.ERA_MODERN) {
                texture = TextureLoader.getTexture(SonicMod.characterPath("overkill/modern.png"));
                textureWidth = 631;
                textureHeight = 768;
            }
        }
        // textureWidth = (float) texture.getWidth();
        // textureHeight = (float) texture.getHeight();



        this.startingDuration = 2.0F;
        this.duration = this.startingDuration;
        this.durationEnd = 1.0F;
        this.scale = Settings.scale * 3.0F;
        this.x = (float) Settings.WIDTH * 0.5F - textureWidth / 2.0F;
        this.y = (float) Settings.HEIGHT * 0.5F - textureHeight / 2.0F;
        this.color = Color.WHITE.cpy();

        // SonicMod.logger.info("(float) Settings.WIDTH * 0.7F - textureWidth / 2.0F " + ((float) Settings.WIDTH * 0.7F - textureWidth / 2.0F));
        // SonicMod.logger.info("(float) (-texture.getWidth()) / 2.0F " + (float) (-texture.getWidth()) / 2.0F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration < durationEnd) {
            this.color.a = Interpolation.fade.apply(0.0F, durationEnd, this.duration);
        } else {
            // left to right
            // this.x = Interpolation.swingIn.apply(
            //         (float) Settings.WIDTH * 0.7F - textureWidth / 2.0F,
            //         (-textureWidth) / 2.0F,
            //         this.duration - 1.0F);
            this.x = Interpolation.swingIn.apply(
                    (float) Settings.WIDTH * 0.3F - textureWidth / 2.0F,
                    (float) Settings.WIDTH * 0.7F + textureWidth / 2.0F,
                    this.duration - durationEnd);
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(blackScreen, 0, 0);
        sb.setColor(this.color);
        sb.draw(texture, this.x, this.y);
    }

    public void dispose() {
    }
}
