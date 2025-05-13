package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import hellospire.character.MyCharacter;
import hellospire.powers.ModDrawReductionPower;
import hellospire.util.CardStats;

public class Acceleration extends BaseCard {
    public static final String ID = makeID("Acceleration");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Acceleration() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }

    /// "You draw one less card next turn. Gain !M! Focus."
    /// TODO: DrawReduction works as if TimeEater applied it. We need to make a new DrawReduction.
    /// Like it's lasting TWO turns! We only want one turn.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new ApplyPowerAction(p, p, new ModDrawReductionPower(p, 1)));
        addToTop(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Acceleration();
    }
}
