package hellospire.potions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
import com.megacrit.cardcrawl.potions.PoisonPotion;
import com.megacrit.cardcrawl.powers.SlowPower;
import hellospire.cards.LevelUpFlightPick;
import hellospire.cards.LevelUpPowerPick;
import hellospire.cards.LevelUpSpeedPick;
import hellospire.cards.Ring;
import hellospire.character.Sonic;

import java.util.ArrayList;
import java.util.Arrays;

import static hellospire.SonicMod.makeID;

public class SlowPotion extends BasePotion {
    public static final String ID = makeID("SlowPotion");
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 98, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 255, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 32, 1);

    // BLUE   0, 98, 255
    // RED    255, 32, 1
    // YELLOW 255, 255, 0

    // TogetherInSpire needs these textures
    private static final Texture containerImg = ImageMaster.POTION_M_CONTAINER;
    private static final Texture liquidImg = ImageMaster.POTION_M_LIQUID;
    private static final Texture hybridImg = ImageMaster.POTION_M_HYBRID;
    private static final Texture spotsImg = ImageMaster.POTION_M_SPOTS;
    private static final Texture outlineImg = ImageMaster.POTION_M_OUTLINE;

    public SlowPotion() {
        super(ID, 1, PotionRarity.RARE, PotionSize.M, LIQUID_COLOR, HYBRID_COLOR, null);
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
            for (AbstractCreature c : AbstractDungeon.getMonsters().monsters) {
                addToBot(new ApplyPowerAction(c, AbstractDungeon.player,
                        new SlowPower(c, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SlowPotion();
    }
}
