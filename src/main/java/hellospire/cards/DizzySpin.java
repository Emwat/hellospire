package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hellospire.character.MyCharacter;
import hellospire.powers.DizzyPower;
import hellospire.util.CardStats;

public class DizzySpin extends BaseCard {
    public static final String ID = makeID("DizzySpin");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 2;

    public DizzySpin() {
        super(ID, info);

//        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
//        setCostForTurn(AbstractDungeon.cardRandomRng.random(0, 3));
    }

    // "DESCRIPTION": "Deal !D! damage. Randomize the cost of ALL cards in your hand for this turn."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (magicNumber > 0) {
            addToBot(new DrawCardAction(magicNumber));
        }
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            int newCost = AbstractDungeon.cardRandomRng.random(3);
            card.setCostForTurn(newCost);
        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new DizzyPower(mo, 2)));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DizzySpin();
    }
}
