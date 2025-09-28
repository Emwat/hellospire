package hellospire.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SkewerAction;
import com.megacrit.cardcrawl.actions.unique.TempestAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import hellospire.MyModConfig;

public class DriftAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int magicNumber;
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private final float x;
    private final float y;
    private final Color color;
    private int msgsPlayed = 0;

    public DriftAction(AbstractPlayer p, int magicNumber, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.magicNumber = magicNumber;
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
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        effect = effect * magicNumber;

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                addToBot(new ModFastAction(() -> {
                    int orbsPlayed = 0;
                    for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                        orb.onStartOfTurn();
                        orb.onEndOfTurn();
                        if (!(orb instanceof EmptyOrbSlot)) {
                            orbsPlayed++;
                        }
                    }
                    boolean used = false;

                    if (!used && MyModConfig.enableTextPopUps) {
                        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(this.x, this.y, orbsPlayed + " Orbs played!", GradualColor(orbsPlayed)));
                        msgsPlayed++;
                        used = true;
                    }
                }));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }

    private Color GradualColor(int orbsPlayed) {
        int mult = 3;
        if (orbsPlayed < 3 * mult) {
            return Color.WHITE.cpy();
        } else if (orbsPlayed < 6 * mult) {
            return Color.GREEN.cpy();
        } else if (orbsPlayed < 9 * mult) {
            return Color.PINK.cpy();
        } else if (orbsPlayed < 12 * mult) {
            return Color.BLUE.cpy();
        } else if (orbsPlayed < 15 * mult) {
            return Color.RED.cpy();
        } else if (orbsPlayed < 18 * mult) {
            return Color.GOLD.cpy();
        }
        return Color.GOLD.cpy();
    }
}
