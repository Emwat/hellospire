package theHedgehog.stances;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import theHedgehog.SonicMod;
import theHedgehog.powers.FireIcePower;

public class IceStance extends AbstractStance {
    public static final String STANCE_ID = SonicMod.makeID("IceStance");
    private static final StanceStrings stanceStrings = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private static final String NAME = stanceStrings.NAME;
    private static final String[] DESCRIPTION = stanceStrings.DESCRIPTION;

    public IceStance() {
        this.ID = STANCE_ID;
        this.name = NAME;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        AbstractPower fireIcePower = AbstractDungeon.player.getPower(FireIcePower.POWER_ID);
        this.description = DESCRIPTION[0].replace("{0}", String.valueOf(fireIcePower.amount));
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        super.onPlayCard(card);
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower fireIcePower = AbstractDungeon.player.getPower(FireIcePower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, fireIcePower.amount));
    }

    @Override
    public void onEnterStance() {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.FIREBRICK, true));
    }

    @Override
    public void onExitStance() {

    }

    @Override
    public final void updateAnimation() {
        this.updateParticleEffect();

        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = this.getAuraEffectInterval();
            // AbstractDungeon.effectsQueue.add(new AggressionStanceAuraEffect());
        }
    }

    public void updateParticleEffect() {
        if (Settings.DISABLE_EFFECTS) {
            return;
        }
    }

    public float getAuraEffectInterval() {
        return MathUtils.random(0.3F, 0.4F);
    }
}