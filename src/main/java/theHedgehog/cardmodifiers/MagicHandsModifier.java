package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.util.ExtraIcons;
import theHedgehog.util.TextureLoader;

import static theHedgehog.SonicMod.makeID;

// https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class MagicHandsModifier extends AbstractCardModifier {
    public static String ID = makeID("modifierMagicHands");
    private final static String magicHandsKeyword = CardCrawlGame.languagePack.getUIString(ID).TEXT[0];
    private final static String magicHandsKeywordNL = CardCrawlGame.languagePack.getUIString(ID).TEXT[1];
    private final static Texture doNotThrowIcon = TextureLoader.getTexture(SonicMod.imagePath("ui/hold.png"));

    public MagicHandsModifier() {

    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (!card.isEthereal) {
            card.selfRetain = true;
        }
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
        if (rawDescription.length() < 100) {
            return magicHandsKeywordNL + rawDescription;
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

    static Color purpleBorder = CardHelper.getColor(239, 103, 246);

    @Override
    public Color getGlow(AbstractCard card) {
        return purpleBorder;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}