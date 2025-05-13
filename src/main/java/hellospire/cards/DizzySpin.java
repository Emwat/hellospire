package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class DizzySpin extends BaseCard {
    public static final String ID = makeID("DizzySpin");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public DizzySpin() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);

    }

    // "DESCRIPTION": "Deal !D! damage. Randomize the cost of ALL cards in your hand for this turn."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (!this.upgraded) {
            for (AbstractCard card : AbstractDungeon.player.hand.group){
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (card.cost != newCost) {
                    card.cost = newCost;
                    card.costForTurn = card.cost;
                    card.isCostModified = true;
                }
                card.freeToPlayOnce = false;
            }
        } else {

        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DizzySpin();
    }
}
