package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.powers.MomentumPower;
import theHedgehog.util.CardStats;

public class Momentum extends BaseCard {
    public static final String ID = makeID("Momentum");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 1;

    public Momentum() {
        super(ID, info);
        setMagic(1);
        setCostUpgrade(0);
    }

    /// "Gain Vigor for every card in your Exhaust Pile."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MomentumPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Momentum();
    }
}
