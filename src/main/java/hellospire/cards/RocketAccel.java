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
import hellospire.actions.ModFastAction;
import hellospire.cardmodifiers.RocketAccelModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class RocketAccel extends BaseCard {
    public static final String ID = makeID("RocketAccel");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    // Bludgeon does 32/42
    private static final int DAMAGE = 24;
    private static final int UPG_DAMAGE = 4;
    private static final int MAGIC = 8;
    private static final int UPG_MAGIC = 4;

    public RocketAccel() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ModFastAction(() -> {
            int amountOfAttacks = p.hand.getAttacks().size();
            if (amountOfAttacks > 1) {
                int randomNumber1 = AbstractDungeon.cardRng.random(0, amountOfAttacks - 1);
                int tries = 0;
                int maxTries = 99;

                int randomNumber2 = randomNumber1;
                while (randomNumber1 == randomNumber2) {
                    tries++;
                    if (tries >= maxTries) {
                        break;
                    }
                    randomNumber2 = AbstractDungeon.cardRng.random(0, amountOfAttacks - 1);
                }

                AbstractCard card1 = p.hand.getAttacks().group.get(randomNumber1);
                AbstractCard card2 = p.hand.getAttacks().group.get(randomNumber2);
                CardModifierManager.addModifier(card1, new RocketAccelModifier(magicNumber));
                card1.flash();
                CardModifierManager.addModifier(card2, new RocketAccelModifier(magicNumber));
                card2.flash();
            } else if (amountOfAttacks == 1) {
                CardModifierManager.addModifier(p.hand.getAttacks().group.get(0), new RocketAccelModifier(magicNumber));
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new RocketAccel();
    }

}
