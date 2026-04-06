package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import theHedgehog.SonicTags;
import theHedgehog.actions.ActivatePassiveOrbAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class InstaShield extends BaseCard {
    public static final String ID = makeID("InstaShield");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public InstaShield() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setDamage(DAMAGE, UPG_DAMAGE);
        // setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.LIKE_WATCHER);
        tags.add(SonicTags.ERA_CLASSIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        // addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, magicNumber)));
        addToBot(new ActivatePassiveOrbAction(p));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new InstaShield();
    }
}
