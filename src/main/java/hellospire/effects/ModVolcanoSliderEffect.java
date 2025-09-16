package hellospire.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveParticle;
import com.megacrit.cardcrawl.vfx.scene.TorchParticleXLEffect;

public class ModVolcanoSliderEffect extends AbstractGameEffect {
    private float waveTimer = 0.0F;
    private float x;
    private float y;
    private float cX;
    private static final float WAVE_INTERVAL = 0.03F;
    private int power;

    public ModVolcanoSliderEffect(float x, float y, float cX, int power) {
        this.x = x + 120.0F * Settings.scale;
        this.y = y - 20.0F * Settings.scale;
        this.cX = cX;
        this.power = power;

        this.renderBehind = false;
    }

    public void update() {
        this.waveTimer -= Gdx.graphics.getDeltaTime();
        if (this.waveTimer < 0.0F) {
            this.waveTimer = 0.03F;
            this.x += 160.0F * Settings.scale;
            this.y -= 15.0F * Settings.scale;
            AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x, this.y));
            for (int i = 0; i < power * 2; i++) {
                AbstractDungeon.effectsQueue.add(new TorchParticleXLEffect(this.x - (i * 10F), this.y - (i * 10F)));
            }
            AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.x, this.y));

            if (this.x > this.cX) {
                this.isDone = true;
                CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER", 0.05F);
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }

}
