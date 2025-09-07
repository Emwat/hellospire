package hellospire.cardsPackExclusive;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.cards.BaseCard;
import hellospire.cards.Ring;
import hellospire.character.Sonic;
import hellospire.util.CardStats;
import thePackmaster.ThePackmaster;

public class BouncePad extends BaseCard {
    public static final String ID = makeID("PackBouncePad");
    private static final CardStats info = Loader.isModLoaded("anniv5") ?
            new CardStats(
            ThePackmaster.Enums.PACKMASTER_RAINBOW,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    ) :
            new CardStats(
                    Sonic.Meta.CARD_COLOR,
                    CardType.SKILL,
                    CardRarity.SPECIAL,
                    CardTarget.SELF,
                    1
            );

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public BouncePad() {
        super(ID, info);
        this.cardsToPreview = new hellospire.cardsPackExclusive.Ring();

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BouncePad();
    }
}
