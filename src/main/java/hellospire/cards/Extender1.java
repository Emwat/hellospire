package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.ExtenderPower;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

public class Extender1 extends BaseCard {
    public static final String ID = makeID("Extender1");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );


    public Extender1() {
        super(ID, info);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    // not used yet
    @Override
    public void onChoseThisOption() {
        AbstractCreature p = AbstractDungeon.player;
        addToBot(new IncreaseMaxOrbAction(2));
        // if (card.cardID.equals(c1.cardID)) {
        //     addToBot(new IncreaseMaxOrbAction(2));
        // } else if (card.cardID.equals(c2.cardID)) {
        //     addToBot(new DrawCardAction(2));
        // } else if (card.cardID.equals(c3.cardID)) {
        //     addToBot(new GainEnergyAction(2));
        // }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Extender1();
    }
}
