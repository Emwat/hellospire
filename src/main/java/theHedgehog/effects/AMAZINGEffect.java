package theHedgehog.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import theHedgehog.SonicMod;

public class AMAZINGEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private static TextureAtlas.AtlasRegion img = null;

    /// options are amazing/outstanding/awesome/great/good/slam
    /// but only amazing works rn b/c idk what im doing
    public AMAZINGEffect(String word) {
        if (img == null) {
            // TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
            TextureAtlas atlas = new TextureAtlas(SonicMod.vfxPath("AtlasAmazing.atlas"));
            img = atlas.findRegion("260/" + word);
        }

        this.startingDuration = 2.0F;
        this.duration = this.startingDuration;
        this.scale = Settings.scale * 3.0F;
        this.x = (float)Settings.WIDTH * 0.5F - (float)img.packedWidth / 2.0F;
        this.y = (float)img.packedHeight / 2.0F;
        this.color = Color.WHITE.cpy();
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        if (this.duration < 1.0F) {
            this.color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration);
        } else {
            this.y = Interpolation.swingIn.apply((float)Settings.HEIGHT * 0.7F - (float)img.packedHeight / 2.0F, (float)(-img.packedHeight) / 2.0F, this.duration - 1.0F);
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale, this.scale, this.duration);
    }

    public void dispose() {
    }
}
