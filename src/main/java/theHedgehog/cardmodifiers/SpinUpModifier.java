package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import theHedgehog.SonicTags;
import theHedgehog.actions.HeavyIncrementAction;
import theHedgehog.actions.HeavyKeepCostAction;

import static theHedgehog.SonicMod.makeID;

//https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class SpinUpModifier extends AbstractCardModifier {

    private final static String spinUpKeyword = CardCrawlGame.languagePack.getUIString(makeID("modifierSpinUp")).TEXT[0];

    public SpinUpModifier() {

    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.tags.add(SonicTags.SPIN_UP);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new HeavyIncrementAction(card));
    }

    @Override
    public void onOtherCardPlayed(AbstractCard card, AbstractCard otherCard, CardGroup group) {
        addToBot(new HeavyKeepCostAction(card));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return spinUpKeyword + "NL " + rawDescription;
    }


    @Override
    public AbstractCardModifier makeCopy() {
        return new SpinUpModifier();
    }
}