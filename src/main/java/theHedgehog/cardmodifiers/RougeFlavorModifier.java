package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;

import java.util.ArrayList;
import java.util.Arrays;

//https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class RougeFlavorModifier extends AbstractCardModifier {

    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(184, 144, 179);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public RougeFlavorModifier() {

    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        if (Settings.language != Settings.GameLanguage.ENG) {
            return;
        }

        String flavor = GenerateFlavor();

        card.rawDescription = flavor;
        card.initializeDescription();
        flavor = "Good luck, Big Blue.";

        FlavorText.AbstractCardFlavorFields.boxColor.set(card, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(card, FLAVOR_TEXT_COLOR);
        FlavorText.AbstractCardFlavorFields.flavor.set(card, flavor);
    }

    private String GenerateFlavor(){
        final String starterCard1 = "Homing Attack";
        final String starterCard2 = "Bounce Pad";
        final String starterCard3 = "Strike";
        final ArrayList<String> starterCards = new ArrayList<>(Arrays.asList(
                starterCard1, starterCard2, starterCard3
        ));
        final ArrayList<String> textColors = new ArrayList<>(Arrays.asList(
                "[#ff6563ff]", "[#fff6e2ff]", "[#efc851ff]", "[#87ceebff]"
        ));
        final String starter = "9138682208680565125232538";
        final String cardsss = "2221113231111211222321221";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < starter.length(); i++) {
            stringBuilder.append(textColors.get(AbstractDungeon.miscRng.random(textColors.size() - 1)));
            stringBuilder.append(starterCards.get(Character.getNumericValue(cardsss.charAt(i)) - 1).charAt(Character.getNumericValue(starter.charAt(i))));
        }

        return stringBuilder.toString();
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RougeFlavorModifier();
    }
}