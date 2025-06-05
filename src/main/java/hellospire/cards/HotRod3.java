package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class HotRod3 extends BaseCard {
    public static final String ID = makeID("HotRod3");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public HotRod3() {
        super(ID, info);

        setBlock(HotRod.BLOCK_OVERLOADED, 1);
        loadCardImage(SonicMod.imagePath("cards/skill/HotRod.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HotRod3();
    }
}
