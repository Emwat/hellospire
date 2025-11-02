package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Momentum extends BaseCard {
    public static final String ID = makeID("Momentum");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 4;

    public Momentum() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        CardModifierManager.addModifier(this, new SpinUpModifier());
    }

    /// "Gain Vigor for every card in your Exhaust Pile."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int vigorAmount = p.exhaustPile.size() + (!this.upgraded ? 0 : p.hand.size());

        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, vigorAmount), vigorAmount));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Momentum();
    }
}
