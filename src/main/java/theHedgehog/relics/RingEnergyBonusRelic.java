package theHedgehog.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;

public class RingEnergyBonusRelic extends BaseRelic {
    private static final String NAME = "RingEnergyBonusRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static final int NumberOfRingsForEnergy = 3;

    public RingEnergyBonusRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + NumberOfRingsForEnergy + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        RingEnergyBonusRelic thisRelic = this;
        addToTop(new ModFastAction(() -> {
            int ringCount = 0;
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    ringCount++;
                }
            }
            if (ringCount >= NumberOfRingsForEnergy) {
                thisRelic.flash();
                addToBot(new GainEnergyAction(ringCount / 3));
            }
        }));
    }

}
