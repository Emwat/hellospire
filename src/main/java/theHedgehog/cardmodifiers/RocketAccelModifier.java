package theHedgehog.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.cards.BaseCard;

// https://github.com/daviscook477/BaseMod/wiki/CardModifiers
public class RocketAccelModifier extends AbstractCardModifier {
    private int additionalDamage;

    public RocketAccelModifier() {
        new RocketAccelModifier(0);
    }

    public RocketAccelModifier(int additionalDamage) {
        this.additionalDamage = additionalDamage;
    }

    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + this.additionalDamage;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);

        if (card instanceof BaseCard) {
            BaseCard baseCard = (BaseCard) card;
            if (card.type == AbstractCard.CardType.ATTACK) {
                baseCard.setBackgroundTexture(
                        SonicMod.characterPath("cardback/rocketaccel/bg_attack.png"),
                        SonicMod.characterPath("cardback/rocketaccel/bg_attack_p.png")
                );
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RocketAccelModifier();
    }
}