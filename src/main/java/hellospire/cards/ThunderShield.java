package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.ThunderShieldPower;
import hellospire.util.CardStats;

public class ThunderShield extends BaseCard {
    public static final String ID = makeID("ThunderShield");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;

    public ThunderShield() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlaySound(SoundLibrary.LightningShield));
        addToBot(new ApplyPowerAction(p, p, new ThunderShieldPower(p, magicNumber)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.setInnate(true);
        }

        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ThunderShield();
    }
}
