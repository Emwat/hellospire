package hellospire.actions;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

public class ModTextAboveCreatureAction extends AbstractGameAction {
    private Color color;
    private boolean used = false;
    private String msg;

    public ModTextAboveCreatureAction(AbstractCreature source, com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType type) {
        if (type == com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.STUNNED) {
            this.setValues(source, source);
            this.msg = AbstractCreature.TEXT[3];
            this.actionType = ActionType.TEXT;
            this.duration = Settings.ACTION_DUR_FASTER;
        } else if (type == com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction.TextType.INTERRUPTED) {
            this.setValues(source, source);
            this.msg = AbstractCreature.TEXT[4];
            this.actionType = ActionType.TEXT;
            this.duration = Settings.ACTION_DUR_FASTER;
        } else {
            this.isDone = true;
        }

    }

    public ModTextAboveCreatureAction(AbstractCreature source, String text, Color color) {
        this.setValues(source, source);
        this.msg = text;
        this.actionType = ActionType.TEXT;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.color = color;
    }

    public void update() {
        if (!this.used) {
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.source.hb.cX - this.source.animX, this.source.hb.cY + this.target.hb.height / 2.0F, this.msg, color));
            this.used = true;
        }

        this.tickDuration();
    }

    public static enum TextType {
        STUNNED,
        INTERRUPTED;
    }
}
