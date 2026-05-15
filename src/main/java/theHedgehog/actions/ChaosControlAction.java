package theHedgehog.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import theHedgehog.MyModConfig;
import theHedgehog.powers.ChaosControlPower;

public class ChaosControlAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private final float x;
    private final float y;
    private final Color color;
    private int msgsPlayed = 0;

    public ChaosControlAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.energyOnUse = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;

        this.actionType = ActionType.TEXT;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.color = Color.WHITE.cpy();
        this.x = (float) Settings.WIDTH * 0.5F;
        this.y = (float) Settings.HEIGHT * 0.20F;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        int orbsPlayed = 0;
        boolean used = false;

        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        if (effect > 0) {
            addToBot(new ApplyPowerAction(this.p, this.p, new ChaosControlPower(this.p, effect)));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
