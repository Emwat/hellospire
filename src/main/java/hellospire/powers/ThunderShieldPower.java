package hellospire.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import hellospire.cards.Ring;

import java.util.Objects;

import static hellospire.SonicMod.makeID;

public class ThunderShieldPower extends BasePower {
    public static final String POWER_ID = makeID("ThunderShieldPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ThunderShieldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {

        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractCard heightCard = new Ring();

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard cardInHand : AbstractDungeon.player.hand.group) {
                    if (Objects.equals(cardInHand.cardID, heightCard.cardID)) {
                        addToBot(new ChannelAction(new Lightning()));
                    }
                }
                this.isDone = true;
            }
        });
    }

}