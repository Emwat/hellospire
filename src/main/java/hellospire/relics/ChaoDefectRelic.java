package hellospire.relics;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Plasma;
import hellospire.character.Sonic;

import static hellospire.SonicMod.makeID;

public class ChaoDefectRelic extends BaseRelic {
    private static final String NAME = "ChaoDefectRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public ChaoDefectRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        addToTop(new IncreaseMaxOrbAction(1));
        addToBot(new ChannelAction(new Plasma()));
        //addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ChaoPower(AbstractDungeon.player)));
    }


}
