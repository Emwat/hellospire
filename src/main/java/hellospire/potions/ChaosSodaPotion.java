package hellospire.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.BottledMiracle;
import com.megacrit.cardcrawl.potions.Elixir;
import com.megacrit.cardcrawl.stances.CalmStance;
import hellospire.cards.*;
import hellospire.character.Sonic;

import java.util.ArrayList;
import java.util.Arrays;

import static hellospire.SonicMod.makeID;

public class ChaosSodaPotion extends BasePotion {
    public static final String ID = makeID("ChaosSodaPotion");
    private static final Color LIQUID_COLOR = CardHelper.getColor(0, 98, 255);
    private static final Color HYBRID_COLOR = CardHelper.getColor(255, 255, 0);
    private static final Color SPOTS_COLOR = CardHelper.getColor(255, 255, 255);

    // BLUE   0, 98, 255
    // RED    255, 32, 1
    // YELLOW 255, 255, 0


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
        addToBot(new ExhaustAction(potency, false, true));
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
