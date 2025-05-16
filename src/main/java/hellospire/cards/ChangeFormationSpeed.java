package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.daily.mods.Flight;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.powers.LevelUpSpeedPower;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class ChangeFormationSpeed extends BaseCard {
    public static final String ID = makeID("ChangeFormationSpeed");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public ChangeFormationSpeed() {
        super(ID, info);
        setMagic(magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int SpeedPower = p.getPower("LevelUpSpeed").amount;
        int FlightPower = p.getPower("LevelUpFlight").amount;
        int PowerPower = p.getPower("LevelUpPower").amount;
        int totalPower = FlightPower + PowerPower;

        addToBot(new ApplyPowerAction(p, p, new LevelUpSpeedPower(p, totalPower)));
        if (FlightPower > 0) {
            addToBot(new ReducePowerAction(p, p, "FlightPower", FlightPower));
        }
        if (PowerPower > 0) {
            addToBot(new ReducePowerAction(p, p, "PowerPower", PowerPower));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ChangeFormationSpeed();
    }
}
