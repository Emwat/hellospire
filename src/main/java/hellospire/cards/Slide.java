package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Slide extends BaseCard {
    public static final String ID = makeID("Slide");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public Slide() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        tags.add(SonicTags.ANTI_DASH);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new DiscardPileToTopOfDeckAction(p));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Slide();
    }
}
