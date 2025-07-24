package hellospire.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.util.ExtraIcons;
import hellospire.util.TextureLoader;

import static hellospire.SonicMod.makeID;

// https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class MagicHandsModifier extends AbstractCardModifier {

    private final static String magicHandsKeyword = CardCrawlGame.languagePack.getUIString(makeID("modifierMagicHands")).TEXT[0];
    private final Texture doNotThrowIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/hold.png"));

    public MagicHandsModifier() {

    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
        card.tags.add(SonicTags.DO_NOT_THROW);
    }

    @Override
    public void onUpdate(AbstractCard card) {
        super.onUpdate(card);
        ExtraIcons.icon(doNotThrowIcon)
                .drawColor(new Color(1, 1, 1, card.transparency))
                .render(card);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (rawDescription.length() < 75) {
            return magicHandsKeyword + "NL " + rawDescription;
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

    Color purpleBorder = CardHelper.getColor(239, 103, 246);

    @Override
    public Color getGlow(AbstractCard card) {
        return purpleBorder;
    }

}