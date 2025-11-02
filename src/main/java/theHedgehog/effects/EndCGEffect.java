package theHedgehog.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static theHedgehog.SonicMod.characterPath;

public class EndCGEffect extends AbstractGameEffect {
    private static Texture img = ImageMaster.loadImage(characterPath("ending/throne.png"));
    private float FadeInOut;

    public EndCGEffect() {
        this.duration = 2.0F;
        this.FadeInOut = 0.2F;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        this.color.a = 0.0F;
    }

    public void update() {
        if (2.0F - this.duration < this.FadeInOut) {
            this.color.a = MathHelper.scaleLerpSnap(this.color.a, 1.0F);
        } else if (this.duration < this.FadeInOut) {
            this.color.a = MathHelper.scaleLerpSnap(this.color.a, 0.0F);
        }
        if (this.duration < 0.0F)
            this.isDone = true;
    }

    public void render(SpriteBatch spriteBatch) {
        if (!this.isDone) {
            spriteBatch.setColor(Color.WHITE.cpy());
            spriteBatch.draw(img, 0.0F, 0.0F, img
                    .getWidth() * Settings.scale, img
                    .getHeight() * Settings.scale);
            spriteBatch.setBlendFunction(770, 771);
        }
    }

    public void dispose() {
    }
}