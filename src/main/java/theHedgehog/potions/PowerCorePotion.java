package theHedgehog.potions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theHedgehog.cards.*;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LevelUpFlightPower;
import theHedgehog.powers.LevelUpPowerPower;
import theHedgehog.powers.LevelUpSpeedPower;

import java.util.ArrayList;
import java.util.Arrays;

import static theHedgehog.SonicMod.makeID;

public class PowerCorePotion extends BasePotion {
    public static final String ID = makeID("PowerCorePotion");
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 98, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 255, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 32, 1);

    // private static final Texture SPOTS_COLOR = CardHelper.getColor(255, 255, 255);
    // TogetherInSpire needs these textures
    private static final Texture containerImg = ImageMaster.POTION_S_CONTAINER;
    private static final Texture liquidImg = ImageMaster.POTION_S_LIQUID;
    private static final Texture hybridImg = ImageMaster.POTION_S_HYBRID;
    private static final Texture spotsImg = ImageMaster.POTION_S_SPOTS;
    private static final Texture outlineImg = ImageMaster.POTION_S_OUTLINE;

    // BLUE   0, 98, 255
    // RED    255, 32, 1
    // YELLOW 255, 255, 0
    public PowerCorePotion() {
        super(ID, 1, PotionRarity.COMMON, PotionSize.S, LIQUID_COLOR, HYBRID_COLOR, null);
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

    @Override
    public void use(AbstractCreature abstractCreature) {
        AbstractCard ring = new Ring().makeStatEquivalentCopy();
        BaseCard.setCostForCombat(ring, 0);
        for (int i = 0; i < potency; i++) {
            addToBot(new MakeTempCardInHandAction(ring, 1));
            InputHelper.moveCursorToNeutralPosition();
            // AbstractCreature p = AbstractDungeon.player;
            // addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, 1), 1));
            // addToBot(new ApplyPowerAction(p, p, new LevelUpFlightPower(p, 1), 1));
            // addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, 1), 1));
            addToBot(new ChooseOneAction(new ArrayList<>(Arrays.asList(
                    new LevelUpSpeedPick(),
                    new LevelUpFlightPick(),
                    new LevelUpPowerPick()
            ))));
            // addToBot(new DiscoveryPowerCoreAction());
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PowerCorePotion();
    }
}
