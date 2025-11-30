package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theHedgehog.character.Sonic;
import theHedgehog.powers.LevelUpFlightPower;
import theHedgehog.powers.LevelUpPowerPower;
import theHedgehog.powers.LevelUpSpeedPower;
import theHedgehog.powers.RingPower;
import theHedgehog.util.CardStats;

public class BlueBomber extends BaseCard {
    public static final String ID = makeID("BlueBomber");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 14;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public BlueBomber() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int ringPower = getPower(AbstractDungeon.player, RingPower.POWER_ID);
        if (getPower(AbstractDungeon.player, LevelUpSpeedPower.POWER_ID) > 0) {
            ringPower = ringPower * (getPower(AbstractDungeon.player, LevelUpSpeedPower.POWER_ID) + 1);
        }
        if (getPower(AbstractDungeon.player, LevelUpFlightPower.POWER_ID) > 0) {
            ringPower = ringPower * (getPower(AbstractDungeon.player, LevelUpFlightPower.POWER_ID) + 1);
        }
        if (getPower(AbstractDungeon.player, LevelUpPowerPower.POWER_ID) > 0) {
            ringPower = ringPower * (getPower(AbstractDungeon.player, LevelUpPowerPower.POWER_ID) + 1);
        }

        this.baseDamage += (getPower(AbstractDungeon.player, DexterityPower.POWER_ID) + ringPower) * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new BlueBomber();
    }
}
