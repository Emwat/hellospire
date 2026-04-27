package theHedgehog.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
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
import theHedgehog.powers.RingPower;
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
            0
    );

    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 3;

    public LightSpeedDash() {
        super(ID, info);
        cardsToPreview = new Ring();
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ringsToCreate = CalculateRingsToCreate();
        // addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), ringsToCreate));
        addToBot(new ModFastAction(() -> {
            for (AbstractCard card : p.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    addToBot(new ExhaustSpecificCardAction(card, p.hand, true));
                }
            }

            addToBot(new ModXFastAction(() -> {
                RingPower ringPower = (RingPower) p.getPower(RingPower.POWER_ID);
                if (ringPower != null)
                    ringPower.CalculateNumberOfRings();
            }));
        }));
        addToBot(new HealAction(p, p, magicNumber));
    }

    // @Override
    // public void triggerOnEndOfPlayerTurn() {
    //     // this function does not trigger.
    // }

    private int CalculateRingsToCreate() {
        return BaseMod.MAX_HAND_SIZE - (AbstractDungeon.player.hand.size() - 1);
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new LightSpeedDash();
    }
}
