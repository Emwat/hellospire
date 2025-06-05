package hellospire.cards;

import basemod.BaseMod;
import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Objects;

public class SlotMachinePull extends BaseCard {
    public static final String ID = makeID("SlotMachinePull");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public SlotMachinePull() {
        super(ID, info);

        this.returnToHand = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1));
        if (p.hand.group.size() >= BaseMod.MAX_HAND_SIZE){
            this.returnToHand = false;
//            addToBot(new DiscardSpecificCardAction(this));
        }
    }

    @Override
    public void atTurnStart() {
        this.returnToHand = true;
        super.atTurnStart();
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);

        addToBot(new DiscardSpecificCardAction(this));
    }

    private void ExhaustALLEggmanCards(){
        ExhaustCurses(AbstractDungeon.player.hand);
        ExhaustCurses(AbstractDungeon.player.drawPile);
        ExhaustCurses(AbstractDungeon.player.discardPile);
    }

    private void ExhaustCurses(CardGroup pile) {
        for (AbstractCard card : pile.group) {
            if (Objects.equals(card.cardID, SlotMachinePullEggman.ID)) {
                addToBot(new ExhaustSpecificCardAction(card, pile));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SlotMachinePull();
    }
}
