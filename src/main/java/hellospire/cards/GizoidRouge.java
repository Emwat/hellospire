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

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 5;
    private static final int MAGIC = 20;
    private static final int UPG_MAGIC = 5;


    public GizoidRouge() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
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
