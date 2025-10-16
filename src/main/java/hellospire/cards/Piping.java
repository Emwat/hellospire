package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.TrueGrit;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Piping extends BaseCard {
    public static final String ID = makeID("Piping");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 18;
    private static final int UPG_BLOCK = 4;

    public Piping() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new SelectCardsInHandAction(1, CardCrawlGame.languagePack.getUIString(makeID("PipingMessage")).TEXT[0],
                false, false, (card) -> true, (cards) -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard card : cards){
                AbstractCard newCopy = card.makeStatEquivalentCopy();
                addToBot(new ExhaustSpecificCardAction(card, p.hand, true));
                addToBot(new MakeTempCardInHandAction(newCopy, 1, true));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Piping();
    }
}
