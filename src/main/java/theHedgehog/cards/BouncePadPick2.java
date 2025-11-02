package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class BouncePadPick2 extends BaseCard {
    public static final String ID = makeID("BouncePadPick2");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BouncePadPick2() {
        super(ID, info);
        this.cardsToPreview = new Trick();
        setMagic(MAGIC, UPG_MAGIC);

        loadCardImage(imageSkillPath("Trick.png"));

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber, true));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BouncePadPick2();
    }
}
