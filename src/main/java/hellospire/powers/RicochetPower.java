package hellospire.powers;

import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.cards.Ricochet;

import static hellospire.SonicMod.makeID;

public class RicochetPower extends BasePower {
    public static final String POWER_ID = makeID("RicochetPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RicochetPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        AbstractCard rico = new Ricochet();
        if (card.type == AbstractCard.CardType.ATTACK && owner.hasPower("Vigor")){
            for (AbstractCard discardedCard : AbstractDungeon.player.discardPile.group){
                if (discardedCard.cardID == rico.cardID) {
                    addToBot(new SFXAction(SoundLibrary.Spring));
                    addToBot(new DiscardToHandAction(discardedCard));
                }
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}