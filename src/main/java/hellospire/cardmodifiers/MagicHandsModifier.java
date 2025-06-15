package hellospire.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static hellospire.SonicMod.makeID;

//https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class MagicHandsModifier extends AbstractCardModifier {

    private final static String magicHandsKeyword = CardCrawlGame.languagePack.getUIString(makeID("modifierMagicHands")).TEXT[0];

    public MagicHandsModifier() {

    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.length() < 75) {
            return magicHandsKeyword + " NL " + rawDescription;
        }
        return magicHandsKeyword + rawDescription;
    }

    @Override
    public void onExhausted(AbstractCard card) {
        addToBot(new ChangeStanceAction("Wrath"));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MagicHandsModifier();
    }
}