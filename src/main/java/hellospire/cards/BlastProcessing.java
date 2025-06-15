package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.BlastProcessingPower;
import hellospire.powers.ThunderShieldPower;
import hellospire.util.CardStats;

public class BlastProcessing extends BaseCard {
    public static final String ID = makeID("BlastProcessing");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 2;

    public BlastProcessing() {
        super(ID, info);

        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BlastProcessingPower(p, magicNumber), magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.setInnate(true);
        }

        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlastProcessing();
    }
}
