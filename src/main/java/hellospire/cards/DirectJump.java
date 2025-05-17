package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.powers.DirectJumpPower;
import hellospire.util.CardStats;

public class DirectJump extends BaseCard {
    public static final String ID = makeID("DirectJump");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

//    private static final int BLOCK = 6;
//    private static final int UPG_BLOCK = 2;

    public DirectJump() {
        super(ID, info);

//        setBlock(BLOCK, UPG_BLOCK);
    }

    /// When you gain Block this turn, add a Ring to your hand.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DirectJumpPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DirectJump();
    }
}
