package theHedgehog.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.ArrayList;


public class EvokeAllOrbsWithoutRemovingAction extends AbstractGameAction {
    private boolean called = false;

    public EvokeAllOrbsWithoutRemovingAction() {
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        ArrayList<AbstractOrb> orbs = AbstractDungeon.player.orbs;
        for (int i = 0; i < orbs.size(); ++i) {
            if (!orbs.isEmpty() && !(orbs.get(i) instanceof EmptyOrbSlot)) {
                ((AbstractOrb) orbs.get(i)).onEvoke();
            }
            this.addToBot(new EvokeOrbAction(1));
        }

        this.tickDuration();
        // this.isDone = true;
    }
}
