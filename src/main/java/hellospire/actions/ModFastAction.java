package hellospire.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class ModFastAction extends AbstractGameAction {
    private final Runnable action;

    public ModFastAction(Runnable action) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.action = action;
    }

    @Override
    public void update() {
        action.run();
        this.isDone = true;
    }
}
