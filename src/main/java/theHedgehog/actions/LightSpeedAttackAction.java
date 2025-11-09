package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.effects.LightSpeedAttackEffect;

public class LightSpeedAttackAction extends AbstractGameAction {
    private AbstractCard card;

    public LightSpeedAttackAction(AbstractCard card) {
        this.card = card;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster)this.target);
            this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
            this.addToTop(new SFXAction("ATTACK_HEAVY", 0.1F));
            this.addToTop(new VFXAction(new LightSpeedAttackEffect(this.target.hb.cX, this.target.hb.cY)));
        }

        this.isDone = true;
    }
}
