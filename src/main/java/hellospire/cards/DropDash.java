package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.DodgeAndRoll;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class DropDash extends BaseCard {
    public static final String ID = makeID("DropDash");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;
//    private static final int MAGIC = 4;

    ///"DESCRIPTION": Gain !B! Block. Gain HALF of !B! next turn.
    public DropDash() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
//        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        int halfBlock = (int)(block * 0.5F);
        addToBot(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, halfBlock), halfBlock));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DropDash();
    }
}
