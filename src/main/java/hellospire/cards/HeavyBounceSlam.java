package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.Objects;

public class HeavyBounceSlam extends BaseCard {
    public static final String ID = makeID("HeavyBounceSlam");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public HeavyBounceSlam() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    ///  Evoke all of your frost orbs. NL Deal 2x damage for each frost orb evoked.",
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int evokedFrostOrbs = 0;
        int totalDamage = 0;
        for (AbstractOrb orb : p.orbs){
            if(Objects.equals(orb, new Frost())){
                evokedFrostOrbs++;
            }
        }
        totalDamage = evokedFrostOrbs * magicNumber;
        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new HeavyBounceSlam();
    }
}
