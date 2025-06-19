package hellospire.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.cards.Ring;
import hellospire.cards.Trick;

import static hellospire.SonicMod.makeID;

public class ChaoPower extends BasePower {
    public static final String POWER_ID = makeID("ChaoPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private AbstractCard ring = new Ring();
    private AbstractCard trick = new Trick();

    public ChaoPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        this.flash();

        if (card.type == AbstractCard.CardType.ATTACK) {
            addToBot(new MakeTempCardInHandAction(trick, 1));
        } else if (card.type == AbstractCard.CardType.SKILL) {
            addToBot(new MakeTempCardInHandAction(ring, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
    }
}