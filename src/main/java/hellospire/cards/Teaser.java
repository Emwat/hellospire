package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Teaser extends BaseCard {
    public static final String ID = makeID("Teaser");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;

    /// "Channel !M! Dark. NL Shuffle a Claws Unleashed into your Draw Pile."
    public Teaser() {
        super(ID, info);
        this.cardsToPreview = new ClawsUnleashed();
        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard createdCard = this.cardsToPreview.makeStatEquivalentCopy();

        addToBot(new ChannelAction(new Dark()));
        addToBot(new MakeTempCardInDrawPileAction(createdCard, 1, true, true, false));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Teaser();
    }
}
