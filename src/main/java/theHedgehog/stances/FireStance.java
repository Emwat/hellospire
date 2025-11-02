package theHedgehog.stances;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theHedgehog.SonicMod;
import theHedgehog.effects.ModStanceAuraEffect;
import theHedgehog.effects.ModStanceParticleEffect;
import theHedgehog.powers.FireIcePower;

public class FireStance extends AbstractStance {
    public static final String STANCE_ID = SonicMod.makeID("FireStance");
    private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static final String NAME = stanceStrings.NAME;
    private static final String[] DESCRIPTION = stanceStrings.DESCRIPTION;

    public FireStance() {
        this.ID = STANCE_ID;
        this.name = NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        AbstractPower fireIcePower = AbstractDungeon.player.getPower(FireIcePower.POWER_ID);
        this.description = DESCRIPTION[0].replace("{0}", String.valueOf(fireIcePower.amount));
    }

    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.04F;
                AbstractDungeon.effectsQueue.add(new ModStanceParticleEffect());
            }
        }

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new ModStanceAuraEffect(STANCE_ID));
        }
    }

    @Override
    public void onEnterStance() {
        CardCrawlGame.sound.play("ATTACK_FLAME_BARRIER");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
    }
    @Override
    public void onExitStance() {

    }

    @Override
    public void onPlayCard(AbstractCard card) {
        super.onPlayCard(card);
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower fireIcePower = AbstractDungeon.player.getPower(FireIcePower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlameBarrierPower(p, fireIcePower.amount)));
    }

}