package theHedgehog.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHedgehog.cards.*;
import theHedgehog.powers.LevelUpFlightPower;
import theHedgehog.powers.LevelUpPowerPower;
import theHedgehog.powers.LevelUpSpeedPower;

import java.util.ArrayList;
import java.util.Arrays;

public class DiscoveryPowerCoreAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public DiscoveryPowerCoreAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }

        ArrayList<AbstractCard> generatedCards = new ArrayList<>(Arrays.asList(
                new LevelUpSpeedPick(),
                new LevelUpFlightPick(),
                new LevelUpPowerPick()
        ));
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, "idk", true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    GiveBuff(AbstractDungeon.player, disCard, 1);
                }
                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private void GiveBuff(AbstractCreature p, AbstractCard card, int potency){
        if (false) {
            //ignore for better formatting
        } else if (LevelUpSpeedPick.ID.equals(card.cardID)) { addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, potency), potency));
        } else if (LevelUpFlightPick.ID.equals(card.cardID)) { addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, potency), potency));
        } else if (LevelUpPowerPick.ID.equals(card.cardID)) { addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, potency), potency));
        }
    }
}
