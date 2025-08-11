package hellospire.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.rewards.RewardItem;
import hellospire.SonicMod;
import hellospire.actions.FasterAction;
import hellospire.actions.ModTextInCenterAction;
import hellospire.events.MissionEvent;
import hellospire.util.TextureLoader;

import static hellospire.SonicMod.makeID;

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

    public static final int RANK_S_TURN = 3;
    public static final int RANK_A_TURN = 4;
    public static final int RANK_B_TURN = 5;
    public static final int MISSION_AMOUNT = 20;

    public MissionRingRacePower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        amount2 = 0;
    }

    public void updateDescription() {
        this.description = String.format("%s%s%s%s%s%s%s",
                DESCRIPTIONS[0], RANK_S_TURN,
                DESCRIPTIONS[1], RANK_A_TURN,
                DESCRIPTIONS[2], RANK_B_TURN,
                DESCRIPTIONS[3]
        );
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        amount++;
        updateIcon(amount, RANK_S_TURN, RANK_A_TURN, RANK_B_TURN);
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
        if (amount <= RANK_S_TURN) {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_S_REWARD));
        } else if (amount <= RANK_A_TURN) {
            AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(RANK_A_REWARD));
        } else if (amount <= RANK_B_TURN) {
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
