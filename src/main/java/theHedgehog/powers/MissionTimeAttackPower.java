package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.TombRedMask;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theHedgehog.SonicMod;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.rewards.MissionReward;
import theHedgehog.util.MissionTextures;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.relics.CDPastRelic.reviveCost;

public class MissionTimeAttackPower extends BasePower {
    public static final String POWER_ID = makeID("MissionTimeAttackPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int RANK_S_REWARD = SonicMod.RANK_S_REWARD;
    public static final int RANK_A_REWARD = SonicMod.RANK_A_REWARD;
    public static final int RANK_B_REWARD = SonicMod.RANK_B_REWARD;
    public static final int RANK_C_REWARD = SonicMod.RANK_C_REWARD;

    public static final int RANK_S_TURN = 6;
    public static final int RANK_A_TURN = 7;
    public static final int RANK_B_TURN = 9;

    public MissionTimeAttackPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    public void updateDescription() {
        this.description = String.format("%s%s%s%s%s%s%s%s",
                DESCRIPTIONS[0],
                DESCRIPTIONS[1], RANK_S_TURN,
                DESCRIPTIONS[2], RANK_A_TURN,
                DESCRIPTIONS[3], RANK_B_TURN,
                DESCRIPTIONS[4]
        );
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        amount++;
        MissionTextures.updateIconA(this, amount, RANK_S_TURN, RANK_A_TURN, RANK_B_TURN);
    }

    @Override
    public void onVictory() {
        CardCrawlGame.music.playTempBgmInstantly("STS_BossVictoryStinger_3_v3_MUSIC.ogg", false);
        if (amount <= RANK_S_TURN) {
            AbstractDungeon.getCurrRoom().rewards.add(new MissionReward(RANK_S_REWARD));
        } else if (amount <= RANK_A_TURN) {
            AbstractDungeon.getCurrRoom().rewards.add(new MissionReward(RANK_A_REWARD));
        } else if (amount <= RANK_B_TURN) {
            AbstractDungeon.getCurrRoom().rewards.add(new MissionReward(RANK_B_REWARD));
        } else {
            AbstractDungeon.getCurrRoom().rewards.add(new MissionReward(RANK_C_REWARD));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
