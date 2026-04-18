package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.RagePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistKnuckles extends BaseCard implements OnObtainCard {
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
    private static final int UPG_STR_AMT = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(218, 170, 142);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(230, 34, 56);

    public AssistKnuckles() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_STR, STR_AMT, UPG_STR_AMT);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
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

    @Override
    public void onObtainCard() {
        removeAssistCard(this.upgraded);
    }
}
