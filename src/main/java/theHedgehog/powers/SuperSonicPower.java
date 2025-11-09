package theHedgehog.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.effects.SuperSonicAura;

import static theHedgehog.SonicMod.makeID;

public class SuperSonicPower extends BasePower {
    public static final String POWER_ID = makeID("SuperSonicPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    protected Color c;
    protected float angle;
    protected float particleTimer;
    protected float particleTimer2;
    private SuperSonicAura VFX;

    public SuperSonicPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        this.c = Color.WHITE.cpy();
        this.particleTimer = 0.0F;
        this.particleTimer2 = 0.0F;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // @Override
    // public void atStartOfTurn() {
    //     this.flash();
    //     this.addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
    //     this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
    // }


    public void onInitialApplication() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.effectsQueue.add(new SmokePuffEffect(p.hb.cX, p.hb.cY));
        if (!(p instanceof Sonic)) {
            return;
        }

        addToBot(new ModXFastAction(() -> {
            if (Sonic.currentModSkin.hasAnimation("super")) {
                ((Sonic) p).playAnimation("super");
            }
        }));

        VFX = new SuperSonicAura(p);
        addToBot(new VFXAction(VFX));
    }

    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof Sonic && p.currentHealth > 0) {
            ((Sonic) p).playAnimation("idle", true);
        }
    }

    public float[] _lightsOutGetXYRI() {
        // 08/28/2025 07:43 PM
        // he's green and he doesn't get brighter w/ more stacks of dex
        int shine = 1;
        AbstractPower str = owner.getPower(DexterityPower.POWER_ID);
        if (str != null) {
            shine = str.amount / 7;
        }
        return new float[]{owner.hb.cX, owner.hb.cY, (100f + 10 * shine) * Settings.scale, (0.5f + 0.05f * shine)};
    }

    public Color[] _lightsOutGetColor() {
        // return new Color[]{new Color(1.0f, 245f / 255f, 0f, 1f)};
        return new Color[] {Color.RED.cpy()};
        // return new Color[] {Color.GOLD.cpy()};
        // return new Color[] {Color.YELLOW};
    }

    // public void render(SpriteBatch sb) {
    //     if (this.img != null) {
    //         sb.setColor(this.c);
    //         sb.setBlendFunction(770, 1);
    //         sb.draw(this.img,
    //                 AbstractDungeon.player.drawX - 256.0F + AbstractDungeon.player.animX,
    //                 AbstractDungeon.player.drawY - 256.0F + AbstractDungeon.player.animY + AbstractDungeon.player.hb_h / 2.0F,
    //                 256.0F, 256.0F, 512.0F, 512.0F,
    //                 Settings.scale, Settings.scale, -this.angle, 0, 0, 512, 512, false, false);
    //         sb.setBlendFunction(770, 771);
    //     }
    // }
    //
    // public void update() {
    //     this.updateAnimation();
    // }
    //
    // public void updateAnimation() {
    //     if (!Settings.DISABLE_EFFECTS) {
    //         this.particleTimer -= Gdx.graphics.getDeltaTime();
    //         if (this.particleTimer < 0.0F) {
    //             this.particleTimer = 0.04F;
    //             AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
    //         }
    //     }
    //
    //     this.particleTimer2 -= Gdx.graphics.getDeltaTime();
    //     if (this.particleTimer2 < 0.0F) {
    //         this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
    //         AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
    //     }
    // }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}