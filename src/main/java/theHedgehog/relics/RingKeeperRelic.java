package theHedgehog.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.cards.Ring;
import theHedgehog.character.Sonic;
import theHedgehog.powers.RingPower;

import static theHedgehog.SonicMod.makeID;

public class RingKeeperRelic extends BaseRelic implements CustomSavable<Integer> {
    private static final String NAME = "RingKeeperRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.DEPRECATED; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static boolean hasSoundPlayed = false;
    private static final int toleranceToPain = 3;

    // Putting these stats here in case I want to revisit this relic for tolerance.
    // This enemy intends to Attack for a total of 0-4 damage.
    // This enemy intends to Attack for a total of 5-9 damage.
    // This enemy intends to Attack for a total of 10-14 damage.
    // This enemy intends to Attack for a total of 15-19 damage.
    // This enemy intends to Attack for a total of 20-24 damage.
    // This enemy intends to Attack for a total of 25-29 damage.
    // This enemy intends to Attack for a total of 30+ damage.

    public RingKeeperRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public Integer onSave() {
        return counter;
    }

    @Override
    public void onLoad(Integer savedInteger) {
        if (savedInteger == null) {
            return;
        }
        if (savedInteger >= 0) {
            counter = savedInteger;
        }
    }

    @Override
    public void atBattleStart() {
        addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), counter));
    }

    @Override
    public void atTurnStart() {
        hasSoundPlayed = false;
    }

    @Override
    public void wasHPLost(int damageAmount) {
        BaseRelic thisRelic = this;
        if (damageAmount >= toleranceToPain && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToTop(new ModFastAction(() -> {
                // CardCrawlGame.sound.play(SoundLibrary.LoseRings);
                RingPower ringPower = (RingPower) AbstractDungeon.player.getPower(RingPower.POWER_ID);
                if (ringPower != null && ringPower.amount > 0 && !hasSoundPlayed) {
                    thisRelic.flash();
                    addToBot(SoundLibrary.SoundAction(SoundLibrary.LoseRings));
                    hasSoundPlayed = true;
                }

                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.hasTag(SonicTags.RING)) {
                        addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
                    }
                }
                addToBot(new ModFastAction(() -> {
                    if (ringPower != null) {
                        ringPower.CalculateNumberOfRings();
                    }
                }));
            }));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void onVictory() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            // this code does not work.
            // int energyLeft = EnergyPanel.totalCount;
            // int ringsLeftover = 0;
            // for (AbstractCard card : AbstractDungeon.player.hand.group) {
            //     if (Ring.ID.equals(card.cardID)) {
            //         ringsLeftover++;
            //         if (energyLeft > 0) {
            //             energyLeft--;
            //             int amountToHeal = RingPower.calculateAmountToHeal(card.heal);
            //             if (amountToHeal > 0) {
            //                 p.heal(amountToHeal);
            //             }
            //         }
            //     }
            // }
            AbstractPower ringPower = AbstractDungeon.player.getPower(RingPower.POWER_ID);
            if (ringPower != null && ringPower.amount > 0) {
                setCounter(ringPower.amount);
            } else {
                setCounter(0);
            }
        }

    }


}
