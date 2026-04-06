package theHedgehog.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.TheBomb;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.TheBombPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.actions.YESSSAction;
import theHedgehog.cards.FalconPunchRare2;

import static theHedgehog.SonicMod.makeID;

public class FalconPunchRarePower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = makeID("FalconPunchRarePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public AbstractCreature monster;
    public int amountExhausted;
    // amount is turns.

    public FalconPunchRarePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        addToBot(new MakeTempCardInHandAction(new FalconPunchRare2()));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // @Override
    // public void updateDescription() {
    //     // "In {0} turn{1}, deal {d} damage to {mon}."
    //     this.description = DESCRIPTIONS[0]
    //             .replace("{0}", String.valueOf(amount))
    //             .replace("{1}", amount == 1 ? "" : "s")
    //             .replace("{d}", String.valueOf(amount2))
    //             .replace("{mon}", this.monster == null ? "error" : this.monster.name)
    //     ;
    // }

    // @Override
    // public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
    //     super.atEndOfTurnPreEndTurnCards(isPlayer);
    //     if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
    //         addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    //         if (this.amount == 1) {
    //             addToBot(new UseCardAction(new FalconPunchRare2(), this.monster));
    //         }
    //     }
    // }


    // public void atEndOfTurn(boolean isPlayer) {
    //     if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
    //         addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
    //         if (this.amount == 1) {
    //              doesn't even play
    //             addToBot(new UseCardAction(new FalconPunchRare2(), this.monster));
    //         }
    //     }
    // }

    // doesn't care about vuln
    // private void HardCodedVigor(){
    //     AbstractPower vigorPower = this.owner.getPower(VigorPower.POWER_ID);
    //     int vigorAmt = 0;
    //     if (vigorPower != null) {
    //         vigorAmt = vigorPower.amount;
    //     }
    //     addToBot(new YESSSAction(monster, new DamageInfo(AbstractDungeon.player, this.amount2 + vigorAmt, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    //     addToBot(new ReducePowerAction(this.owner, this.owner, vigorPower, vigorAmt));
    // }

    // Doesn't work
    // private void AddCardQueue() {
    //     AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(
    //             new FalconPunchRare2(),
    //             (AbstractMonster)this.monster,
    //             0,
    //             true,
    //             true), true);
    // }

    // @Override
    // public void atStartOfTurnPostDraw() {
    //     super.atStartOfTurnPostDraw();
    //     // addToBot(new ExhaustAction(amountExhausted, false));
    //     // addToBot(new ExhaustAction(amountExhausted, false));
    // }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
