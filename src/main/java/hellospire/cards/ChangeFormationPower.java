package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.LevelUpPowerPower;
import hellospire.util.CardStats;

public class ChangeFormationPower extends BaseCard {
    public static final String ID = makeID("ChangeFormationPower");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public ChangeFormationPower() {
        super(ID, info);
        setMagic(magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int SpeedPower = getPower(p, "LevelUpSpeed");
        int FlightPower = getPower(p, "LevelUpFlight");
        int PowerPower = getPower(p, "LevelUpPower");
        int totalPower = SpeedPower + FlightPower;

        addToBot(new ApplyPowerAction(p, p, new LevelUpPowerPower(p, totalPower)));
        if (SpeedPower > 0) {
            addToBot(new ReducePowerAction(p, p, makeID("LevelUpSpeed"), SpeedPower));
        }
        if (FlightPower > 0) {
            addToBot(new ReducePowerAction(p, p, makeID("LevelUpFlight"), FlightPower));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChangeFormationPower();
    }
}
