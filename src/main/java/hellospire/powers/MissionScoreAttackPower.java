package hellospire.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.SelfRepair;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import hellospire.SonicMod;
import hellospire.actions.FasterAction;
import hellospire.events.MissionEvent;
import hellospire.util.TextureLoader;

import static hellospire.SonicMod.makeID;

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


    public MissionScoreAttackPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    public void updateDescription() {
        this.description = String.format("%s%s%s%s%s%s%s",
                DESCRIPTIONS[0], RANK_S_SCORE,
                DESCRIPTIONS[1], RANK_A_SCORE,
                DESCRIPTIONS[2], RANK_B_SCORE,
                DESCRIPTIONS[3]
        );
    }

    // @Override
    // public void onPlayCard(AbstractCard card, AbstractMonster m) {
    //     super.onPlayCard(card, m);
    //     if (card.type == AbstractCard.CardType.ATTACK) {
    //         addToBot(new FasterAction(() -> {
    //             amount += card.damage;
    //         }));
    //     }
    // }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
    }

    public void addDamage(int damage) {
        amount += damage;
        updateIcon(amount, RANK_S_SCORE, RANK_A_SCORE, RANK_B_SCORE);
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
