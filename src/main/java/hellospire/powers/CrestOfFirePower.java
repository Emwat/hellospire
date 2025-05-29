package hellospire.powers;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import static hellospire.SonicMod.makeID;

public class CrestOfFirePower extends BasePower {
    public static final String POWER_ID = makeID("CrestOfFirePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private AbstractCard theCard;
    private AbstractMonster target;

    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public CrestOfFirePower(AbstractCreature owner, AbstractMonster m, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        theCard = card;
        target = m;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + (amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        if((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) &&
                !this.target.halfDead &&
                !this.target.hasPower("Minion")){
            theCard.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(this.theCard);
        }
    }
}