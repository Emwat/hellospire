package theHedgehog.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHedgehog.character.Sonic;
import theHedgehog.powers.DizzyPower;

import static theHedgehog.SonicMod.makeID;

public class PowerBrakeRelic extends BaseRelic {
    private static final String NAME = "PowerBrakeRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.

    public PowerBrakeRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();

        AbstractPlayer p = AbstractDungeon.player;
        if (EnergyPanel.totalCount > 0) {
            this.flash();
            for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m2.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(m2, p, new DizzyPower(m2, EnergyPanel.totalCount), EnergyPanel.totalCount));
                }
            }
        }

    }
}
