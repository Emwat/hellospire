package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class Momentum extends BaseCard {
    public static final String ID = makeID("Momentum");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Momentum() {
        super(ID, info);

//        setDamage(DAMAGE, UPG_DAMAGE);
//        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    /// "Gain Vigor for every card in your Exhaust Pile."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, p.exhaustPile.size())));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Momentum();
    }
}
