package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.cardmodifiers.MagicHandsModifier;
import hellospire.cardmodifiers.SpinUpModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class MagicHands extends BaseCard {
    public static final String ID = makeID("MagicHands");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public MagicHands() {
        super(ID, info);
        tags.add(SonicTags.LIKE_WATCHER);
        CardModifierManager.addModifier(this, new SpinUpModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // TODO: Don't worry Buddy
        if (this.upgraded) {
            addToBot(new ChangeStanceAction("Calm"));
        }
        addToBot(new SelectCardsInHandAction(1, "Retain. If the Selected Card is exhausted, enter Wrath.",
                false, false, filter -> !filter.tags.contains(SonicTags.DO_NOT_THROW), cards -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard card : cards) {
                CardModifierManager.addModifier(card, new MagicHandsModifier());
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MagicHands();
    }
}
