package theHedgehog.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LSDPower;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class LightSpeedDash extends BaseCard {
    public static final String ID = makeID("LightSpeedDash");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;

    public LightSpeedDash() {
        super(ID, info);
        cardsToPreview = new Ring();
        setMagic(MAGIC);
        setExhaust(true);
        setCostUpgrade(0);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ringsPlayed = CalculateRings();
        addToBot(new ApplyPowerAction(p, p, new LSDPower(p, magicNumber)));
        addToBot(new ModXFastAction(() -> {
            for (AbstractCard card : p.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    card.tags.add(SonicTags.RING_PLUS);
                }
            }
        }));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), ringsPlayed));
        addToBot(new ModFastAction(() -> {
            for (AbstractCard card : p.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    addToBot(new NewQueueCardAction(card, modGetRandomMonster(), true, true));
                }
            }
            if (ringsPlayed > 6) {
                addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                        SoundLibrary.FeelingGood,
                        SoundLibrary.Hehe,
                        SoundLibrary.SmallYahoo,
                        SoundLibrary.SmallYes
                ))));
            }
        }));

    }

    // @Override
    // public void triggerOnEndOfPlayerTurn() {
    //     // this function does not trigger.
    // }

    private int CalculateRings() {
        return BaseMod.MAX_HAND_SIZE - (AbstractDungeon.player.hand.size() - 1);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LightSpeedDash();
    }
}
