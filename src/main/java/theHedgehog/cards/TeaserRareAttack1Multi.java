package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.WerehogAttackDamageRandomEnemyAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class TeaserRareAttack1Multi extends BaseCard {
    public static final String ID = makeID("TeaserRareAttack1Multi");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );

    // SwordBoomerang does 5 damage 3(4) hits
    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;
    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 1;

    public TeaserRareAttack1Multi() {
        super(ID, info);
        setBackgroundTexture(SonicMod.characterPath("cardback/werehog/bg_attack.png"),
                SonicMod.characterPath("cardback/werehog/bg_attack_p.png"));

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        tags.add(SonicTags.CLAW);
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for(int i = 0; i < this.magicNumber; ++i) {
            addToBot(new WerehogAttackDamageRandomEnemyAction(this));
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new TeaserRareAttack1Multi();
    }
}
