package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Concentrate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.cards.silent.ConcentrateUnlock;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistRouge extends BaseCard {
    public static final String ID = makeID("AssistRouge");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;
    private final String KEYWORD_DEX = "CustomVar_DEX";
    private static final int DEX_AMT = 2;

    public AssistRouge() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DEX, DEX_AMT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Rouge));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, customVar(KEYWORD_DEX)), customVar(KEYWORD_DEX)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, customVar(KEYWORD_DEX)), customVar(KEYWORD_DEX)));
        addToBot(new DiscardAction(p, p, magicNumber, false));
        addToBot(new GainEnergyAction(2));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistRouge();
    }
}
