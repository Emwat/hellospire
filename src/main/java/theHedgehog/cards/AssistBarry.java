package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistBarry extends BaseCard {
    public static final String ID = makeID("AssistBarry");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(222, 212, 171);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(157, 137, 87);

    public AssistBarry() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaustive2();
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BetterDiscardPileToHandAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistBarry();
    }

    private void setExhaustive2() {
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
        exhaust = false;
    }
}
