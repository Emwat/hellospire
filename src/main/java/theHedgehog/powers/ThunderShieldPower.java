package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theHedgehog.actions.ThunderShieldAction;
import theHedgehog.cards.Ring;

import static theHedgehog.SonicMod.makeID;

public class ThunderShieldPower extends BasePower {
    public static final String POWER_ID = makeID("ThunderShieldPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public ThunderShieldPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        // String calculation = " NL NL Expected Total Orb Dmg: " + calculateOrbDamage();

        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + amount + DESCRIPTIONS[4];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + amount + DESCRIPTIONS[5];
        }

        // 0 "At the end of your turn and after your orbs activate their passives, Gain #b",
        // 1        " #yRing. ",
        // 2        " #yRings. ",
        // 3        "Your #yRings trigger Lightning #b",
        // 4        " time.",
        // 5        " times."
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        addToBot(new MakeTempCardInHandAction(new Ring().makeStatEquivalentCopy(), this.amount));
        // for (int i = 0; i < amount; i++) {
        //     addToBot(new ChannelAction(new Lightning()));
        // }
        addToBot(new ThunderShieldAction(AbstractDungeon.player, amount));
    }


    // // in case someone decides to not play any cards and hand is full
    // @Override
    // public void atStartOfTurnPostDraw() {
    //     super.atStartOfTurnPostDraw();
    //     addToBot(new ModXFastAction(this::updateDescription));
    // }
    //
    // // in case someone creates a Ring
    // @Override
    // public void onCreateCard(AbstractCard abstractCard) {
    //     addToBot(new ModXFastAction(this::updateDescription));
    // }
    //
    // // in case hand is full
    // @Override
    // public void onPlayCard(AbstractCard card, AbstractMonster m) {
    //     super.onPlayCard(card, m);
    //     addToBot(new ModXFastAction(this::updateDescription));
    // }
    //
    // // in case someone plays Ring, Jump, or Peelout
    // @Override
    // public void onExhaust(AbstractCard card) {
    //     super.onExhaust(card);
    //     addToBot(new ModXFastAction(this::updateDescription));
    // }
    //
    // private int calculateOrbDamage() {
    //     int rings = ModGetPowerAmount(RingPower.POWER_ID);
    //     int totalDamage = 0;
    //     OrbSimulation orbSim = new OrbSimulation();
    //     int handSpace = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
    //     int incomingRings = Math.min(amount, handSpace);
    //     orbSim.Initialize(incomingRings);
    //
    //     totalDamage += orbSim.GetPassiveLightningDamage();
    //
    //     for (int i = 0; i < amount; i++) {
    //         orbSim.Channel(new Lightning(), false);
    //     }
    //     totalDamage += orbSim.evokeDamage;
    //
    //     for (int i = 0; i < rings + incomingRings; i++) {
    //         totalDamage += orbSim.GetPassiveLightningDamage();
    //     }
    //
    //     return totalDamage;
    // }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
