package theHedgehog.actions;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.relics.Inserter;
import theHedgehog.SonicTags;

public class ThunderShieldAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ThunderShieldAction(AbstractPlayer p, int amount) {
        this.amount = amount;
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (p.maxOrbs > 0) {
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof Lightning) {
                    for (AbstractCard cardInHand : p.hand.group) {
                        if (cardInHand.hasTag(SonicTags.RING)) {
                            for (int j = 0; j < amount; j++) {
                                orb.onStartOfTurn();
                                orb.onEndOfTurn();
                                addToBot(new ModXFastAction(cardInHand::flash));
                            }
                        }
                    }
                }
            }
        }
        this.isDone = true;
    }
}
