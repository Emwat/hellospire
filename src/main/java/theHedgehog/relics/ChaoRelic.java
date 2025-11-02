package theHedgehog.relics;

import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;

public class ChaoRelic extends BaseRelic {
    private static final String NAME = "ChaoRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public ChaoRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        addToTop(new IncreaseMaxOrbAction(1));
        //addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChaoPower(AbstractDungeon.player)));
    }


}
