package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import static theHedgehog.SonicMod.makeID;

// https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class ModRetainModifier extends AbstractCardModifier {

    public static String ID = makeID("modifierRetain");
    private final static String retainKeyword = CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
    private final static String retainKeywordNL = CardCrawlGame.languagePack.getUIString(ID).TEXT[1];

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.length() < 75) {
            return retainKeywordNL + rawDescription;
        }
        return retainKeyword + rawDescription;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.selfRetain;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.isEthereal) {
            card.selfRetain = true;
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ModRetainModifier();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}