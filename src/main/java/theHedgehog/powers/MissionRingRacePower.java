package theHedgehog.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModTextInCenterAction;
import theHedgehog.rewards.MissionReward;
import theHedgehog.util.MissionTextures;

import static theHedgehog.SonicMod.makeID;

public class MissionRingRacePower extends BasePower {
    public static final String POWER_ID = makeID("MissionRingRacePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final int RANK_S_REWARD = SonicMod.RANK_S_REWARD;
    public static final int RANK_A_REWARD = SonicMod.RANK_A_REWARD;
    public static final int RANK_B_REWARD = SonicMod.RANK_B_REWARD;
    public static final int RANK_C_REWARD = SonicMod.RANK_C_REWARD;

    public static final int RANK_S_TURN = 4;
    public static final int RANK_A_TURN = 5;
    public static final int RANK_B_TURN = 7;
    public static final int MISSION_AMOUNT = 15;

    public MissionRingRacePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        amount2 = 0;
    }

    public void updateDescription() {
        this.description = String.format("%s%s%s%s%s%s%s%s%s%s",
                DESCRIPTIONS[0], MISSION_AMOUNT, DESCRIPTIONS[1],
                DESCRIPTIONS[2], RANK_S_TURN,
                DESCRIPTIONS[3], RANK_A_TURN,
                DESCRIPTIONS[4], RANK_B_TURN,
                DESCRIPTIONS[5]
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
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        amount2++;

        if (amount2 >= MISSION_AMOUNT) {
            addWinText();
            for (AbstractMonster m2 : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m2.isDeadOrEscaped()) {
                    addToBot(new InstantKillAction(m2));
                }
            }
        }
    }
    private void addWinText(){
        CardCrawlGame.music.playTempBgmInstantly("STS_BossVictoryStinger_3_v3_MUSIC.ogg", false);
        if (amount <= RANK_S_TURN) {
            addToTop(new ModTextInCenterAction("AMAZING!!! RANK S", Color.GOLD.cpy()));
        } else if (amount <= RANK_A_TURN) {
            addToTop(new ModTextInCenterAction("OUTSTANDING! RANK A", Color.PINK.cpy()));
        } else if (amount <= RANK_B_TURN) {
            addToTop(new ModTextInCenterAction("AWESOME! RANK B", Color.BLUE.cpy()));
        } else {
            addToTop(new ModTextInCenterAction("RANK C", Color.PINK.cpy()));
        }
    }

    public void onVictory() {
        CardCrawlGame.sound.play("BOSS_VICTORY_STINGER");
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
