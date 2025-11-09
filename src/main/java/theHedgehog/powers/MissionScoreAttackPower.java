package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.powers.FadingPower;
import com.megacrit.cardcrawl.powers.ShiftingPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.util.MissionTextures;

import static theHedgehog.SonicMod.makeID;

public class MissionScoreAttackPower extends BasePower {
    public static final String POWER_ID = makeID("MissionScoreAttackPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int RANK_S_REWARD = SonicMod.RANK_S_REWARD;
    public static final int RANK_A_REWARD = SonicMod.RANK_A_REWARD;
    public static final int RANK_B_REWARD = SonicMod.RANK_B_REWARD;
    public static final int RANK_C_REWARD = SonicMod.RANK_C_REWARD;

    public static final int RANK_S_SCORE = 400;
    public static final int RANK_A_SCORE = 300;
    public static final int RANK_B_SCORE = 200;
    public static boolean hasSetFading = false;

    public MissionScoreAttackPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    public void updateDescription() {
        this.description = String.format("%s%s%s%s%s%s%s%s",
                DESCRIPTIONS[0],
                DESCRIPTIONS[1], RANK_S_SCORE,
                DESCRIPTIONS[2], RANK_A_SCORE,
                DESCRIPTIONS[3], RANK_B_SCORE,
                DESCRIPTIONS[4]
        );
    }

    // @Override
    // public void onPlayCard(AbstractCard card, AbstractMonster m) {
    //     super.onPlayCard(card, m);
    //     if (card.type == AbstractCard.CardType.ATTACK) {
    //         addToBot(new ModFastAction(() -> {
    //             amount += card.damage;
    //         }));
    //     }
    // }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        MissionTextures.updateIconZ(this, amount, RANK_S_SCORE, RANK_A_SCORE, RANK_B_SCORE);
        DoubleCheckTransient();
    }

    private void DoubleCheckTransient(){
        addToBot(new ModXFastAction(() -> {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (Transient.ID.equals(mo.name)) {
                    if (!mo.hasPower(ShiftingPower.POWER_ID)) {
                        SonicMod.logger.info("Activating DoubleCheckTransient!! - Shifting");
                        addToBot(new ApplyPowerAction(mo, mo, new ShiftingPower(mo)));
                        if (!hasSetFading && !mo.hasPower(FadingPower.POWER_ID)) {
                            SonicMod.logger.info("Activating DoubleCheckTransient!! - Fading");
                            addToBot(new ApplyPowerAction(mo, mo, new FadingPower(mo, AbstractDungeon.ascensionLevel >= 17 ? 6 : 5)));
                            addToBot(new ModXFastAction(() -> {
                                hasSetFading = true;
                            }));
                        }
                    }
                }
            }
        }));
    }

    public void addDamage(int damage) {
        amount += damage;
    }

    public void onVictory() {
        if (amount >= RANK_S_SCORE) {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_S_REWARD));
        } else if (amount >= RANK_A_SCORE) {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_A_REWARD));
        } else if (amount >= RANK_B_SCORE) {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_B_REWARD));
        } else {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_C_REWARD));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
