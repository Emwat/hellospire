package hellospire.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hellospire.powers.MissionScoreAttackPower;

public class ScoreAttackPatch {
    @SpirePatch(clz = AbstractMonster.class, method = "damage")
    public static class IncreaseNonAttackDamagePatch {
        public static void Prefix(AbstractMonster m, DamageInfo info) {
            if (AbstractDungeon.player != null &&
                    (info.owner == null || info.owner == AbstractDungeon.player) &&
                    !m.hasPower(MissionScoreAttackPower.POWER_ID) && !m.hasPower(IntangiblePlayerPower.POWER_ID)) {

                MissionScoreAttackPower scoreAttackPower = (MissionScoreAttackPower) AbstractDungeon.player.getPower(MissionScoreAttackPower.POWER_ID);

                if (scoreAttackPower != null) {
                    scoreAttackPower.addDamage(info.output);
                }
            }
        }
    }
}
