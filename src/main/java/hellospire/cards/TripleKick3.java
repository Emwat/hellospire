package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.MyModConfig;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class TripleKick3 extends BaseCard {
    public static final String ID = makeID("TripleKick3");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    public TripleKick3() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setEthereal(true);
        setExhaust(true);
        if (MyModConfig.enableKicksForStrikeDummy) {
            tags.add(CardTags.STRIKE);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 3)));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 3)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new TripleKick3();
    }
}
