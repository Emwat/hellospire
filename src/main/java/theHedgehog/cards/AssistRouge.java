package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistRouge extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistRouge");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;
    private final String KEYWORD_DEX = "CustomVar_DEX";
    private static final int DEX_AMT = 2;
    private static final int ENERGY_AMT = 1;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(184, 144, 179);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistRouge() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(KEYWORD_DEX, DEX_AMT);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Rouge));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, customVar(KEYWORD_DEX)), customVar(KEYWORD_DEX)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, customVar(KEYWORD_DEX)), customVar(KEYWORD_DEX)));
        addToBot(new DiscardAction(p, p, magicNumber, false));
        addToBot(new GainEnergyAction(ENERGY_AMT));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistRouge();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.hasTag(SonicTags.UPG_ASSIST));
    }
}
