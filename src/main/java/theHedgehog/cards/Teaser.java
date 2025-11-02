package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class Teaser extends BaseCard implements BranchingUpgradesCard {
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

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }

    public void baseUpgrade() {
        BranchingUpgradesCard tmp = (BranchingUpgradesCard) this.cardsToPreview;
        tmp.doNormalUpgrade();
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void branchUpgrade() {
        BranchingUpgradesCard tmp = (BranchingUpgradesCard) this.cardsToPreview;
        tmp.doBranchUpgrade();
        this.loadCardImage(imageSkillPath("Teaser2.png"));
        this.portraitImg = TextureLoader.getTexture(imageSkillPath("Teaser2_p.png"));
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
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
