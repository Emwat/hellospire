package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Piping extends BaseCard {
    public static final String ID = makeID("Piping");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public Piping() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
    }

    /// Gain !B! Block. NL Exhaust up to !M! cards in your hand.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, block));
        addToBot(new SelectCardsInHandAction(
                1, "Select a card to copy",
                false, false, filter -> true, cards -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard card : cards) {
                addToBot(new ExhaustSpecificCardAction(card, p.hand, true));
                addToBot(new MakeTempCardInHandAction(card, 1));
            }
        }
        ));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Piping();
    }
}
