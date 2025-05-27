package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class VolcanoSlider extends BaseCard {
    public static final String ID = makeID("VolcanoSlider");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 7;
    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 3;


    public VolcanoSlider() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : getCardsToTheLeft()) {
            addToBot(new ExhaustSpecificCardAction(c, p.hand, true));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int exhaustedCards = getCardsToTheLeft().size();
        int realBaseDamage = this.baseDamage;

        this.baseDamage += exhaustedCards * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new VolcanoSlider();
    }

    private ArrayList<AbstractCard> getCardsToTheLeft() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardsToTheLeft = new ArrayList<>();
        boolean keepGoing = true;

        for (AbstractCard q : p.hand.group) {
            if (q == this) {
                keepGoing = false;
                break;
            }

            if (keepGoing) {
                cardsToTheLeft.add(q);
            }
        }

        return cardsToTheLeft;
    }
}
