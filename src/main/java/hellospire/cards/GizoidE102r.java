package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class GizoidE102r extends BaseCard {
    public static final String ID = makeID("GizoidE102r");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 20;
    private static final int MAGIC = 5;

    public GizoidE102r() {
        super(ID, info);

        this.isMultiDamage = true;
        setDamage(DAMAGE);
        setMagic(MAGIC);
        setExhaust(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(p, magicNumber)));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GizoidE102r();
    }
}
