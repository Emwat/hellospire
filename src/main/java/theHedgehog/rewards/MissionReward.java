package theHedgehog.rewards;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.SonicMod;
import theHedgehog.util.MissionTextures;

import static theHedgehog.SonicMod.makeID;

public class MissionReward extends CustomReward {
    public static final String ID = makeID("MissionReward");
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("RewardMissionMessage")).TEXT;
    public int amount;

    public MissionReward(int amount) {
        super(MissionTextures.textureRankS_large, String.format("%s%s%s", TEXT[0], amount, TEXT[1]), RewardTypePatch.MISSION_REWARD);

        if (amount >= SonicMod.RANK_S_REWARD) {
            this.icon = MissionTextures.textureRankS_large;
        } else if (amount >= SonicMod.RANK_A_REWARD) {
            this.icon = MissionTextures.textureRankA_large;
        } else if (amount >= SonicMod.RANK_B_REWARD) {
            this.icon = MissionTextures.textureRankB_large;
        } else {
            this.icon = MissionTextures.textureRankC_large;
        }
        this.amount = amount;
    }

    @Override
    public boolean claimReward() {
        AbstractDungeon.player.gainGold(this.amount);
        return true;
    }

}