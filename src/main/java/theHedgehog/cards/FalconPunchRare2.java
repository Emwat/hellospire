package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.YESSSAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.FalconPunchRarePower;
import theHedgehog.util.CardStats;
import theHedgehog.util.GeneralUtils;

public class FalconPunchRare2 extends BaseCard {
    public static final String ID = makeID("FalconPunchRare2");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;
    private static final int DOUBLE_DAMAGE_MULTIPLIER = 2;

    /// "DESCRIPTION": "Deal !D! damage. NL When you are attacked this turn, deal !M! damage to the attacker."
    public FalconPunchRare2() {
        super(ID, info);
        loadCardImage(SonicMod.imagePath("cards/attack/FalconPunchSuper.png"));
        // loadCardImage(SonicMod.imagePath("cards/attack/FalconPunch.png"));
        SetChaosEmeraldCardback();

        setDamage(DAMAGE, UPG_DAMAGE);
        setEthereal(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new YESSSAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int vigorAndMore = GeneralUtils.getVigorAndMoreAmount2(this.baseDamage);
        this.baseDamage = ((this.baseDamage + vigorAndMore) * DOUBLE_DAMAGE_MULTIPLIER) - vigorAndMore;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new FalconPunchRare2();
    }
}
