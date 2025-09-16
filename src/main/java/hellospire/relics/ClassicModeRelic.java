package hellospire.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
import hellospire.cards.BaseCard;
import hellospire.cards.Ring;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;

import static hellospire.SonicMod.makeID;

public class ClassicModeRelic extends BaseRelic {
    private static final String NAME = "ClassicModeRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static boolean hasSoundPlayed = false;
    private static final int toleranceToPain = 2;

    // Putting these stats here in case I want to revisit this relic for tolerance.
    // This enemy intends to Attack for a total of 0-4 damage.
    // This enemy intends to Attack for a total of 5-9 damage.
    // This enemy intends to Attack for a total of 10-14 damage.
    // This enemy intends to Attack for a total of 15-19 damage.
    // This enemy intends to Attack for a total of 20-24 damage.
    // This enemy intends to Attack for a total of 25-29 damage.
    // This enemy intends to Attack for a total of 30+ damage.

    public ClassicModeRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + toleranceToPain + DESCRIPTIONS[1];
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
}
