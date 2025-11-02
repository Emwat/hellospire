package theHedgehog.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import theHedgehog.character.Sonic;
import theHedgehog.patches.StampPatch;

import java.util.function.Predicate;

import static theHedgehog.SonicMod.makeID;

public class StampRelic extends BaseRelic implements ClickableRelic, CustomBottleRelic, CustomSavable<Integer> {
    private static final String NAME = "StampRelic"; // The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); // This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; // The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; // The sound played when the relic is clicked.

    public AbstractCard card = null;

    public StampRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);

    }


    @Override
    public Predicate<AbstractCard> isOnCard() {
        return StampPatch.inBottledStamp::get;
    }

    @Override
    public String getUpdatedDescription() {
        if (card == null) {
            return DESCRIPTIONS[0];
        } else {
            return DESCRIPTIONS[1] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2];
        }
    }

    @Override
    public void onRightClick() {
        if (card != null) {
            AbstractDungeon.effectList.add(new PurgeCardEffect(card));
            AbstractDungeon.player.masterDeck.removeCard(card);
            card = null;
            this.grayscale = true;
        }
        // AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Madness));
    }

    public AbstractCard getCard() {
        return card.makeCopy();
    }

    @Override
    public void onEquip() {
        card = AbstractDungeon.player.masterDeck.group.get(AbstractDungeon.player.masterDeck.group.size() - 1);
    }

    @Override
    public Integer onSave() {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                setDescriptionAfterLoading();
            }
        }
    }

    public void setDescriptionAfterLoading() {
        boolean cardExists = false;

        if (card != null) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.uuid == card.uuid) {
                    cardExists = true;
                    break;
                }
            }
        }

        if (!cardExists) {
            tips.clear();
            this.description = this.DESCRIPTIONS[3];
            this.grayscale = true;
            initializeTips();
        }

        if (cardExists) {
            this.description = this.DESCRIPTIONS[1] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2];
            this.grayscale = false;
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
        }
    }

}
