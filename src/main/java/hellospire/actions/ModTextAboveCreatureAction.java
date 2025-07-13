package hellospire.actions;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import hellospire.MyModConfig;

public class ModTextAboveCreatureAction extends AbstractGameAction {
    private Color color;
    private boolean used = false;
    private String msg;

    public ModTextAboveCreatureAction(AbstractCreature source, String text, Color color) {
        this.setValues(source, source);
        this.msg = text;
        this.actionType = ActionType.TEXT;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.color = color;
    }

    public void update() {
        if (!this.used && MyModConfig.enableTextPopUps) {
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
