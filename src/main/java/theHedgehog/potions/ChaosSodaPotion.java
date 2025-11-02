package theHedgehog.potions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.stances.CalmStance;
import theHedgehog.actions.ModExhaustAction;
import theHedgehog.cards.*;
import theHedgehog.character.Sonic;

import static theHedgehog.SonicMod.makeID;

public class ChaosSodaPotion extends BasePotion {
    public static final String ID = makeID("ChaosSodaPotion");
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 98, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 255, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 255, 255);
    private static final String ExhaustSelectMessage = CardCrawlGame.languagePack.getUIString(makeID("ChaosSodaPotionMessage")).TEXT[0];

    // BLUE   0, 98, 255
    // RED    255, 32, 1
    // YELLOW 255, 255, 0

    // TogetherInSpire needs these textures
    private static final Texture containerImg = ImageMaster.POTION_BOTTLE_CONTAINER;
    private static final Texture liquidImg = ImageMaster.POTION_BOTTLE_LIQUID;
    private static final Texture hybridImg = ImageMaster.POTION_BOTTLE_HYBRID;
    private static final Texture spotsImg = ImageMaster.POTION_BOTTLE_SPOTS;
    private static final Texture outlineImg = ImageMaster.POTION_BOTTLE_OUTLINE;

    public ChaosSodaPotion() {
        super(ID, 1, PotionRarity.UNCOMMON, PotionSize.BOTTLE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);

        this.isThrown = false;
        this.labOutlineColor = new Color(35f / 255f, 119f / 255f, 183f / 255f, 1f);
        playerClass = Sonic.Meta.THE_HEDGEHOG;
    }

    @Override
    public String getDescription() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            return DESCRIPTIONS[1];
        } else {
            return DESCRIPTIONS[0];
        }
    }

    // TODO: The exhaust action says "Exhaust any number of cards. That's not true here."
    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractCard trick = new Trick();
        trick.setCostForTurn(0);
        trick.upgrade();
        addToBot(new ModExhaustAction(potency, ExhaustSelectMessage, false, true, true));
        addToBot(new MakeTempCardInHandAction(trick.makeStatEquivalentCopy(), potency));
        addToBot(new IncreaseMaxOrbAction(potency));
        addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ChaosSodaPotion();
    }
}
