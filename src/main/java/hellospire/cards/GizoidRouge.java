package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.GreedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class GizoidRouge extends BaseCard {
    public static final String ID = makeID("GizoidRouge");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 25;
    private static final int MAGIC = 25;


    public GizoidRouge() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC);
        setExhaust(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GreedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));

    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GizoidRouge();
    }
}
