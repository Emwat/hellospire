package hellospire.potions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.ChooseCalm;
import com.megacrit.cardcrawl.cards.optionCards.ChooseWrath;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.DexterityPotion;
import com.megacrit.cardcrawl.potions.StancePotion;
import com.megacrit.cardcrawl.potions.StrengthPotion;
import hellospire.SoundLibrary;
import hellospire.actions.DiscoveryPowerCoreAction;
import hellospire.cards.LevelUpFlightPick;
import hellospire.cards.LevelUpPowerPick;
import hellospire.cards.LevelUpSpeedPick;
import hellospire.cards.Ring;
import hellospire.character.Sonic;

import java.util.ArrayList;
import java.util.Arrays;

import static hellospire.SonicMod.makeID;

public class PowerCorePotion extends BasePotion {
    public static final String ID = makeID("PowerCorePotion");
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 98, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 255, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 32, 1);

    // private static final Texture SPOTS_COLOR = CardHelper.getColor(255, 255, 255);
    // TogetherInSpire needs these textures
    private static final Texture containerImg = ImageMaster.POTION_BOTTLE_CONTAINER;
    private static final Texture liquidImg = ImageMaster.POTION_BOTTLE_LIQUID;
    private static final Texture hybridImg = ImageMaster.POTION_BOTTLE_HYBRID;
    private static final Texture spotsImg = ImageMaster.POTION_BOTTLE_SPOTS;
    private static final Texture outlineImg = ImageMaster.POTION_BOTTLE_OUTLINE;

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
        for (int i = 0; i < potency; i++) {
            addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), 1));
            InputHelper.moveCursorToNeutralPosition();
            addToBot(new ChooseOneAction(new ArrayList<AbstractCard>(Arrays.asList(
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
