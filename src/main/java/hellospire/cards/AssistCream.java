package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistCream extends BaseCard {
    public static final String ID = makeID("AssistCream");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int SLOT_INCREASE = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(244, 200, 135);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(228, 121, 56);

    public AssistCream() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
        tags.add(CardTags.HEALING);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Cream));
        addToBot(new HealAction(p, p, magicNumber));
        addToBot(new IncreaseMaxOrbAction(SLOT_INCREASE));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistCream();
    }
}
