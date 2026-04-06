package theHedgehog.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.actions.UnblockedVigorAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class GizoidSonic extends BaseCard {
    public static final String ID = makeID("GizoidSonic");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            3
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;


    public GizoidSonic() {
        super(ID, info);
        this.cardsToPreview = new SonicFlare();

        setDamage(cardsToPreview.baseDamage, UPG_DAMAGE);
        setExhaust(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    ///
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m2.isDeadOrEscaped()) {
                addToBot(new UnblockedVigorAction(m2, new DamageInfo(p, damage, this.damageTypeForTurn), 0.5F));
            }
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GizoidSonic();
    }
}
