package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theHedgehog.SonicTags;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Whirlwind extends BaseCard {
    public static final String ID = makeID("Whirlwind");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int HITS = 3;

    public Whirlwind() {
        super(ID, info);
        this.isMultiDamage = true;

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(HITS);
        tags.add(SonicTags.LIKE_IRONCLAD);
        tags.add(SonicTags.ERA_ADVENTURE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        addToBot(new VFXAction(new WhirlwindEffect(), 0.0F));
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new SFXAction("ATTACK_HEAVY"));
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            addToBot(new VFXAction(p, new CleaveEffect(), 0.0F));

        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Whirlwind();
    }
}
