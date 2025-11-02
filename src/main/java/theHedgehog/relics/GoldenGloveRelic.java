package theHedgehog.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LSDPower;

import static theHedgehog.SonicMod.makeID;

public class GoldenGloveRelic extends BaseRelic {
    private static final String NAME = "GoldenGloveRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private final int HPamt = 2;

    public GoldenGloveRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        if (c.hasTag(SonicTags.RING)) {
            if (!AbstractDungeon.player.hasPower(LSDPower.POWER_ID)) {
                this.flash();
            }
            addToBot(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, HPamt));
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + HPamt + DESCRIPTIONS[1];
    }
}
