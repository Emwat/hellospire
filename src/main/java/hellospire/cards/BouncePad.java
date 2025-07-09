package hellospire.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class BouncePad extends BaseCard {
    public static final String ID = makeID("BouncePad");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;


    public BouncePad() {
        super(ID, info);
        MultiCardPreview.add(this, new Ring(), new Trick());
        // this.cardsToPreview = new Ring();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> picks = new ArrayList<AbstractCard>(Arrays.asList(
                new BouncePadPick1(),
                new BouncePadPick2()
        ));

        if (this.upgraded) {
            for (AbstractCard c : picks) {
                c.upgrade();
            }
        }

        addToBot(SoundLibrary.SoundAction(SoundLibrary.Spring));
        addToBot(new GainBlockAction(p, block));
        addToBot(new ChooseOneAction(picks));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BouncePad();
    }
}
