package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.RagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistKnuckles extends BaseCard {
    public static final String ID = makeID("AssistKnuckles");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;
    private final String KEYWORD_STR = "CustomVar_STR";
    private static final int STR_AMT = 2;

    public AssistKnuckles() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_STR, STR_AMT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Knuckles));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, customVar(KEYWORD_STR)), customVar(KEYWORD_STR)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, customVar(KEYWORD_STR)), customVar(KEYWORD_STR)));
        addToBot(new ApplyPowerAction(p, p, new RagePower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistKnuckles();
    }
}
