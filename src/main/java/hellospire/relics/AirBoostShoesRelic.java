package hellospire.relics;

import hellospire.character.Sonic;

import static hellospire.SonicMod.makeID;

public class AirBoostShoesRelic extends BaseRelic {
    private static final String NAME = "AirBoostShoesRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.

    public AirBoostShoesRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
