package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Crouch extends BaseCard {
    public static final String ID = makeID("Crouch");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public Crouch() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    /// Gain !B! Block. NL Exhaust up to !M! cards in your hand.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ExhaustAction(magicNumber, false, true, true));
//        addToBot(new SelectCardsInHandAction(2, "Select 2 cards to exhaust", false, true, null, cards -> {
//            for (AbstractCard card : cards) {
//                addToBot(new ExhaustAction());
//            }
//        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Crouch();
    }
}
