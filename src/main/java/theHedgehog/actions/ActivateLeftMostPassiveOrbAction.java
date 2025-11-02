package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class ActivateLeftMostPassiveOrbAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ActivateLeftMostPassiveOrbAction(AbstractPlayer p) {
        this.p = p;
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (p.orbs.isEmpty()) {
            this.isDone = true;
            return;
        }
        AbstractOrb leftMostOrb = null;

        for (int i = p.orbs.size() - 1; i >= 0; i--) {
            AbstractOrb orb = p.orbs.get(i);
            if (!(orb instanceof EmptyOrbSlot)){
                leftMostOrb = orb;
                break;
            }
        }
        if (leftMostOrb != null) {
            leftMostOrb.onStartOfTurn();
            leftMostOrb.onEndOfTurn();
        }

        this.isDone = true;
    }
}