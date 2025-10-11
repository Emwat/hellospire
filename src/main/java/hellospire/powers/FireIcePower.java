package hellospire.powers;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import hellospire.cards.BecauseSciencePick1;
import hellospire.cards.BecauseSciencePick2;

import java.util.ArrayList;
import java.util.Arrays;

import static hellospire.SonicMod.makeID;

public class FireIcePower extends BasePower {
    public static final String POWER_ID = makeID("FireIcePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public FireIcePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        ArrayList<AbstractCard> picks = new ArrayList<>(Arrays.asList(
                new BecauseSciencePick1(),
                new BecauseSciencePick2()
        ));

        addToBot(new ChooseOneAction(picks));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}