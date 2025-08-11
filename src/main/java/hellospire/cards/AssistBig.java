package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

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

    public AssistBig() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DRAW, DRAW_AMT);
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
