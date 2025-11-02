package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistBig extends BaseCard {
    public static final String ID = makeID("AssistBig");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;
    private final String KEYWORD_DRAW = "CustomVar_DRAW";
    private static final int DRAW_AMT = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(93, 93, 157);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistBig() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DRAW, DRAW_AMT);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Big));
        // addToBot(new BetterDrawPileToHandAction(this.magicNumber));
        addToBot(new ScryAction(magicNumber));
        addToBot(new DrawCardAction(customVar(KEYWORD_DRAW)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistBig();
    }
}
