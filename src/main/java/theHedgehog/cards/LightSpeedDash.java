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

    public LightSpeedDash() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        this.setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ringsPlayed = CalculateRings();
        addToBot(new ApplyPowerAction(p, p, new LSDPower(p, 1)));
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

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LightSpeedDash();
    }
}
