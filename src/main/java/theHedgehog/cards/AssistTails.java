package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistTails extends BaseCard {
    public static final String ID = makeID("AssistTails");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(217, 196, 138);
    private static final Color FLAVOR_TEXT_COLOR = Color.YELLOW.cpy();

    public AssistTails() {
        super(ID, info);
        setExhaust(true);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Tails));
        addToBot(new IncreaseMaxOrbAction(1));
        if (this.upgraded) {
            addToBot(new ChannelAction(new Lightning()));
        }
        addToBot(new ChannelAction(new Plasma()));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistTails();
    }
}
