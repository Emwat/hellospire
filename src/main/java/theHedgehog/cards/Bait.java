package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHedgehog.character.Sonic;
import theHedgehog.powers.BaitPower;
import theHedgehog.util.CardStats;

public class Bait extends BaseCard {
    public static final String ID = makeID("Bait");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1
    );

    public Bait() {
        super(ID, info);
        setExhaust(true);
    }

    // @Override
    // public void triggerWhenDrawn() {
    //     applyDebuffCosts();
    // }

    // @Override
    // public void applyPowers() {
    //     applyDebuffCosts();
    //     super.applyPowers();
    // }

    private void applyDebuffCosts() {
        int debuffsAmount = 0;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                debuffsAmount++;
            }
        }
        setCostForTurn(this.costForTurn + debuffsAmount);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new ApplyPowerAction(p, p, new FrailPower(p, 1, false)));
        addToBot(new ApplyPowerAction(m, p, new BaitPower(m, p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new Bait();
    }
}
