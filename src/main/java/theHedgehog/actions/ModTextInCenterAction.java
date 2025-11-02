package theHedgehog.actions;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import theHedgehog.MyModConfig;

public class ModTextInCenterAction extends AbstractGameAction {
    private final float x;
    private final float y;
    private final Color color;
    private boolean used = false;
    private final String msg;

    public ModTextInCenterAction(String text, Color color) {
        // this.setValues(source, source);
        this.msg = text;
        this.actionType = ActionType.TEXT;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.color = color;
        this.x = (float)Settings.WIDTH * 0.5F;
        this.y = (float)Settings.HEIGHT * 0.20F;
    }

    public void update() {
        if (!this.used && MyModConfig.enableTextPopUps) {
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.x, this.y, this.msg, color));
            this.used = true;
        }

        this.tickDuration();
    }

    public static enum TextType {
        STUNNED,
        INTERRUPTED;
    }
}
