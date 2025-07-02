package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PanachePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.SonicTags;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.cardmodifiers.MagicHandsModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.Objects;

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
        tags.add(SonicTags.HEAVY);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HeavyIncrementAction(this));

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
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        addToBot(new HeavyKeepCostAction(this));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MagicHands();
    }
}
