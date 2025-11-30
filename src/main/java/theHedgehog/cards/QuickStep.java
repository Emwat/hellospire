package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ActivatePassiveOrbAction;
import theHedgehog.character.Sonic;
import theHedgehog.relics.AirBoostShoesRelic;
import theHedgehog.util.CardStats;

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

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 2;

    public QuickStep() {
        super(ID, info);
        // Overflow error
        // this.cardsToPreview = new QuickAir();

        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.RandomSoundAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.QuickAir1,
                SoundLibrary.QuickAir2,
                SoundLibrary.QuickAir3
        ))));
        addToBot(new GainBlockAction(p, block));
        if (CheckIfRightCard(this, p.hand)) {
            // addToBot(new DrawCardAction(p, 1));
            // addToBot(new ChannelAction(new Lightning()));
            addToBot(new ActivatePassiveOrbAction(p));
        }
        AbstractCard leftCard = new QuickAir();
        if (this.upgraded) {
            leftCard.upgrade();
        }

        addToBot(new MakeTempCardInHandAction(leftCard, 1));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new QuickStep();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (isPlayerHandNull()) {
            return;
        }

        if (AbstractDungeon.player.hasRelic(AirBoostShoesRelic.ID)) {
            return;
        }

        if (AbstractDungeon.player.orbs.isEmpty()) {
            return;
        }

        if (CheckIfRightCard(this, AbstractDungeon.player.hand) && HasChanneledOrb()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

}
