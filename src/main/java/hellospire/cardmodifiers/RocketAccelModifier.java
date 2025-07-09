package hellospire.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.HeavyIncrementAction;
import hellospire.actions.HeavyKeepCostAction;
import hellospire.cards.BaseCard;
import hellospire.cards.RocketAccel;

import static hellospire.SonicMod.makeID;

//https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class RocketAccelModifier extends AbstractCardModifier {
    private int additionalDamage = RocketAccel.MAGIC;

    public RocketAccelModifier() {
    }

    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + this.additionalDamage;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RocketAccelModifier();
    }
}