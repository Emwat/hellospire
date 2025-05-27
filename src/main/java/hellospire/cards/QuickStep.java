package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickStep extends BaseCard {
    public static final String ID = makeID("QuickStep");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;

    /// Gain !B! Block. NL If this card is to the very right of your hand, draw !M! cards.
    public QuickStep() {
        super(ID, info);
        // Overflow error
//        this.cardsToPreview = new QuickAir();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
        setExhaust(true);
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
        AbstractCard leftCard = new QuickAir();
//        leftCard.exhaust = true;
        if(this.upgraded) {
            leftCard.upgrade();
        }

        addToBot(new MakeTempCardInHandAction(leftCard, 1));


    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new QuickStep();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }
        if (CheckIfRightCard(this, AbstractDungeon.player.hand)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    private boolean CheckIfRightCard(AbstractCard card, CardGroup hand) {
        if(hand.size() <= 0){
            return false;
        }

        return hand.group.get(hand.size() - 1) == card;
    }
}
