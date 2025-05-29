package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import hellospire.SonicTags;
import hellospire.actions.CrestOfFireAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class FireTackle extends BaseCard {
    public static final String ID = makeID("FireTackle");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public FireTackle(){
        this(0);
    }

    /// "DESCRIPTION": "Deal !D! damage. NL When you are attacked this turn, deal !M! damage to the attacker."
    public FireTackle(int upgrades) {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC);
        this.timesUpgraded = upgrades;
        tags.add(SonicTags.CREST_OF_FIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new CrestOfFireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), p, this));
        addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, magicNumber)));

    }

    public void upgrade() {
//        this.upgradeDamage(UPG_DAMAGE);
        this.upgradeMagicNumber(UPG_MAGIC);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FireTackle(this.timesUpgraded);
    }
}
