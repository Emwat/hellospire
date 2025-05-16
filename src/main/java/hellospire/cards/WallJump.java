package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ReinforcedBody;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.WallJumpAction;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class WallJump extends BaseCard {
    public static final String ID = makeID("WallJump");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -1
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;
    private boolean freetoPlayOnce;

    ///"DESCRIPTION": "Add Height to your hand. NL Gain !B! Block. NL Repeat this X times."
    public WallJump() {
        super(ID, info);
        this.cardsToPreview = new Height();


        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new WallJumpAction(p, block, this.freetoPlayOnce, this.energyOnUse));
    }
}
