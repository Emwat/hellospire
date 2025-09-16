package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.DizzyPower;
import hellospire.util.CardStats;

public class AssistCharmy extends BaseCard {
    public static final String ID = makeID("AssistCharmy");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DIZZY_AMOUNT = 2;
    private static final int UPG_DIZZY_AMOUNT = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final String KEYWORD_DIZZY = "CustomVar_DIZZY";
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(244, 200, 135);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(228, 121, 56);

    public AssistCharmy() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DIZZY, DIZZY_AMOUNT, UPG_DIZZY_AMOUNT);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Charmy));
        addToBot(new ApplyPowerAction(m, p, new DizzyPower(m, customVar(KEYWORD_DIZZY)), customVar(KEYWORD_DIZZY)));
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(new Lightning()));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistCharmy();
    }
}
