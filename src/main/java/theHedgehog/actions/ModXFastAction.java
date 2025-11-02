package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class ModXFastAction extends AbstractGameAction {
    private final Runnable action;

    public ModXFastAction(Runnable action) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.action = action;
    }

    @Override
    public void update() {
        action.run();
        this.isDone = true;
    }
}
