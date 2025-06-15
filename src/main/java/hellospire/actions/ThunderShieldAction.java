package hellospire.actions;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.SonicMod;
import hellospire.cards.Ring;

import java.util.Objects;

public class ThunderShieldAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ThunderShieldAction(AbstractPlayer p, int amount) {
        this.amount = amount;
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        int rings = 0;

        for (AbstractCard cardInHand : p.hand.group) {
            if (Objects.equals(cardInHand.cardID, Ring.ID)) {
                rings++;
            }
        }

        for (AbstractOrb orb : p.orbs) {
            if (Objects.equals(orb.name, "Lightning")) {
                for (int i = 0; i < rings; i++) {
                    for (int j = 0; j < amount; j++) {
                        orb.onStartOfTurn();
                        orb.onEndOfTurn();
                    }
                }
            }
        }
        this.isDone = true;
    }
}
