package hellospire.relics;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import hellospire.actions.ModXFastAction;
import hellospire.actions.RandomizeCostAction;
import hellospire.cards.Ring;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpSpeedPower;

import static hellospire.SonicMod.makeID;

public class CDFutureRelic extends BaseRelic implements OnCreateCardInterface {
    private static final String NAME = "CDFutureRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.
    private static final int handSizeAdd = 3;

    public CDFutureRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        BaseMod.MAX_HAND_SIZE += handSizeAdd;
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, -1)));
        this.flash();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        BaseMod.MAX_HAND_SIZE -= handSizeAdd;
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if (card.cardID.equals(Ring.ID) || card.cardID.equals(hellospire.cardsPackExclusive.Ring.ID)) {
            addToBot(new ModXFastAction(() -> {
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (card.cost != newCost) {
                    card.cost = newCost;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                }
                card.freeToPlayOnce = false;
            }));
        }
    }
}