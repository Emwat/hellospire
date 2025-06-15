package hellospire.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.WallJumpAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class WallJump extends BaseCard {
    public static final String ID = makeID("WallJump");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    public WallJump() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        setBlock(BLOCK, UPG_BLOCK);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WallJumpAction(p, block, this.freeToPlayOnce, this.energyOnUse));
    }
}
