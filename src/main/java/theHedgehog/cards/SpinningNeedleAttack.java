package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.RandomizeCostAction;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class SpinningNeedleAttack extends BaseCard {
    public static final String ID = makeID("SpinningNeedleAttack");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SpinningNeedleAttack() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
        CardModifierManager.addModifier(this, new SpinUpModifier());
        tags.add(SonicTags.ERA_MODERN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new SpinningNeedleAttackAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
        addToBot(new DrawCardAction(1));
        addToBot(new ModFastAction(() -> {
            AbstractCard lastCard = p.hand.getTopCard();
            addToBot(new RandomizeCostAction(lastCard));
            addToBot(new DamageAction(m, new DamageInfo(p, damage + (lastCard.costForTurn * magicNumber), DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new SpinningNeedleAttack();
    }
}
