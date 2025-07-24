package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.SilverPower;
import hellospire.util.CardStats;

public class AssistSilver extends BaseCard {
    public static final String ID = makeID("AssistSilver");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;
    private static final int SCRY_AMOUNT = 3;
    private static final String KEYWORD_SCRY = "CustomVar_SCRY";

    public AssistSilver() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
        setCustomVar(KEYWORD_SCRY, SCRY_AMOUNT);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(SCRY_AMOUNT));
        addToBot(new ApplyPowerAction(p, p, new SilverPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistSilver();
    }
}
