package theHedgehog.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.ActivatePassiveOrbAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.TeaserRarePower;
import theHedgehog.util.CardStats;
import theHedgehog.util.TextureLoader;

public class TeaserRare extends BaseCard {
    public static final String ID = makeID("TeaserRare");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private final String KEYWORD_DARK = "CustomVar_DARK";
    private static final int DARK_AMT = 1;

    /// "Channel !M! Dark. NL Shuffle a Claws Unleashed into your Draw Pile."
    public TeaserRare() {
        super(ID, info);
        SetChaosEmeraldCardback();
        loadCardImage(SonicMod.imagePath("cards/skill/Teaser.png"));

        // this.cardsToPreview = new ClawsUnleashed();
        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DARK, DARK_AMT);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // AbstractCard createdCard = this.cardsToPreview.makeStatEquivalentCopy();

        for (int i = 0; i < customVar(KEYWORD_DARK); i++) {
            addToBot(new ChannelAction(new Dark()));
        }
        // addToBot(new ActivatePassiveOrbAction(p));
        addToBot(new ApplyPowerAction(p, p, new TeaserRarePower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new TeaserRare();
    }
}
