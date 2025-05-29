package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.character.Sonic;
import hellospire.powers.RicochetPower;
import hellospire.util.CardStats;

public class Ricochet extends BaseCard {
    public static final String ID = makeID("Ricochet");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public Ricochet() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.hasPower(makeID("GainedRichochet"))) {
            addToBot(new ApplyPowerAction(p, p, new RicochetPower(p)));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        int amount = getPower(p, "Vigor");
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
//        if (amount > 0) {
//            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, amount)));
//        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Ricochet();
    }
}
