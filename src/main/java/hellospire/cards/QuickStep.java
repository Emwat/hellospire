package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.sun.tools.javac.comp.Check;
import hellospire.SoundLibrary;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickStep extends BaseCard {
    public static final String ID = makeID("QuickStep");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;

    /// Gain !B! Block. NL If this card is to the very right of your hand, draw !M! cards.
    public QuickStep() {
        super(ID, info);
        // Overflow error
//        this.cardsToPreview = new QuickAir();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayRandomSound(new ArrayList<>(Arrays.asList(
                SoundLibrary.QuickAir1,
                SoundLibrary.QuickAir2,
                SoundLibrary.QuickAir3
        ))));
        addToBot(new GainBlockAction(p, block));
        if (CheckIfRightCard(this, p.hand)){
            addToBot(new DrawCardAction(p, 1));
        }
        addToBot(new MakeTempCardInHandAction(new QuickAir(), 1));


    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new QuickStep();
    }

    public boolean CheckIfRightCard(AbstractCard card, CardGroup hand) {
        if(hand.size() <= 0){
            return false;
        }

        return hand.group.get(hand.size() - 1) == card;
    }
}
