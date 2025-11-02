package theHedgehog.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;


public class ModAnimateHopAction extends AbstractGameAction {
    private boolean called = false;

    public ModAnimateHopAction(AbstractCreature owner) {
        this.setValues((AbstractCreature) null, owner, 0);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (!this.called) {
            this.source.useHopAnimation();
            this.called = true;
        }

        this.tickDuration();
    }

    // public void useHopAnimation(AbstractCreature c) {
    //     c.animX = 0.0F;
    //     c.animY = 0.0F;
    //     c.vY = 300.0F * Settings.scale;
    //     c.animationTimer = 0.7F;
    //     c.animation = AbstractCreature.CreatureAnimation.HOP;
    // }
}
