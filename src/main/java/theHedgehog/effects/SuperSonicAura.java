package theHedgehog.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;

public class SuperSonicAura extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private AbstractPlayer player;
    protected float particleTimer;
    protected float particleTimer2;

    public SuperSonicAura(AbstractPlayer player) {
        this.player = player;
        particleTimer = 0;
        particleTimer2 = 0;
    }

    public void dispose() {
        this.isDone = true;
    }

    public void update() {
        if (!Settings.DISABLE_EFFECTS) {
            particleTimer -= Gdx.graphics.getDeltaTime();
            if (particleTimer < 0.0F) {
                particleTimer = 0.4F;
                AbstractDungeon.effectsQueue.add(new SuperSonicParticleEffect(player.hb.cX, player.hb.cY));
            }
        }

        particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (particleTimer2 < 0.0F) {
            particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new AuraEffect(Color.valueOf("#FFD800FF"), player));
        }
    }

    public void finish() {
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {

    }


}