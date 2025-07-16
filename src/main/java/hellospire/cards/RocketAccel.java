package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.cardmodifiers.RocketAccelModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class RocketAccel extends BaseCard {
    public static final String ID = makeID("RocketAccel");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 28;
    private static final int UPG_DAMAGE = 10;
    public static final int MAGIC = 4;

    public RocketAccel() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int amountOfAttacks = p.hand.getAttacks().size();
                if (amountOfAttacks > 1) {
                    int randomNumber1 = AbstractDungeon.cardRng.random(0, amountOfAttacks - 1);

                    int randomNumber2 = randomNumber1;
                    while (randomNumber1 == randomNumber2) {
                        randomNumber2 = AbstractDungeon.cardRng.random(0, amountOfAttacks - 1);
                    }

                    CardModifierManager.addModifier(p.hand.getAttacks().group.get(randomNumber1), new RocketAccelModifier());
                    CardModifierManager.addModifier(p.hand.getAttacks().group.get(randomNumber2), new RocketAccelModifier());
                } else if (amountOfAttacks == 1) {
                    CardModifierManager.addModifier(p.hand.getAttacks().group.get(0), new RocketAccelModifier());
                }
                this.isDone = true;
            }
        });
    }


    @Override
    public AbstractCard makeCopy() { // Optional
        return new RocketAccel();
    }

}
