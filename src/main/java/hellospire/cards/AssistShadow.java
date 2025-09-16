package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class AssistShadow extends BaseCard {
    public static final String ID = makeID("AssistShadow");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(190, 190, 190);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(0, 0, 0);

    public AssistShadow() {
        super(ID, info);

        setEthereal(true);
        setExhaust(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Shadow));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            setEthereal(false);
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistShadow();
    }
}
