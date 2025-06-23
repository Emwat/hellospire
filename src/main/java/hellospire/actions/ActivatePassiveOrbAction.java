package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ActivatePassiveOrbAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ActivatePassiveOrbAction(AbstractPlayer p) {
        this.p = p;
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (AbstractDungeon.player.orbs.isEmpty()) {
            this.isDone = true;
            return;
        }

        AbstractOrb orb = AbstractDungeon.player.orbs.get(0);
        orb.onStartOfTurn();
        orb.onEndOfTurn();

        this.isDone = true;
    }
}