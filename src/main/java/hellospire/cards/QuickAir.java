package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.ActivateLeftMostPassiveOrbAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class QuickAir extends BaseCard {
    public static final String ID = makeID("QuickAir");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    /// Gain !B! Block. NL Add a Quick Step to your hand.
    public QuickAir() {
        super(ID, info);
        this.cardsToPreview = new QuickStep();
        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.RandomSoundAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.QuickAir1,
                SoundLibrary.QuickAir2,
                SoundLibrary.QuickAir3
        ))));
        addToBot(new GainBlockAction(p, block));
        if (CheckIfLeftCard(this, p.hand)) {
            // addToBot(new DrawCardAction(p, 1));
            // addToBot(new ChannelAction(new Frost()));
            addToBot(new ActivateLeftMostPassiveOrbAction(p));
        }
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.upgradeBlock(UPG_BLOCK);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new QuickAir();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.orbs.isEmpty()) {
            return;
        }

        if (CheckIfLeftCard(this, AbstractDungeon.player.hand) && HasChanneledOrb()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }




}
