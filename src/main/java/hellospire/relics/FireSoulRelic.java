package hellospire.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import hellospire.SonicTags;
import hellospire.character.Sonic;

import static hellospire.SonicMod.makeID;

public class FireSoulRelic extends BaseRelic implements CustomSavable<Integer> {
    private static final String NAME = "FireSoulRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final int initialBlockAmount = 1;

    public FireSoulRelic() {
        super(ID, NAME, Sonic.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = initialBlockAmount;
        UpdateDescriptions();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
        if (targetCard.hasTag(SonicTags.CREST_OF_FIRE)){
            addToTop(new GainBlockAction(AbstractDungeon.player, counter));
        }
    }

    @Override
    public Integer onSave() {
        return counter;
    }

    @Override
    public void onEnterRestRoom() {
        super.onEnterRestRoom();
        this.flash();
        counter++;
        UpdateDescriptions();
    }

    @Override
    public void onLoad(Integer savedInteger) {
        if (savedInteger == null) {
            return;
        }
        if (savedInteger >= 0 ) {
            counter = savedInteger;
        }
        UpdateDescriptions();
    }

    private void UpdateDescriptions(){
        description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
}
