package theHedgehog.util;

import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.MyModConfig;

import static theHedgehog.SonicMod.makeID;

public class UnlockUtil {

    public static void registerUnlockCardBundle(AbstractPlayer.PlayerClass player, int index, String card1, String card2, String card3) {
        CustomUnlockBundle currentBundle;

        currentBundle = new CustomUnlockBundle(card1, card2, card3);

        UnlockTracker.addCard(card1);
        UnlockTracker.addCard(card2);
        UnlockTracker.addCard(card3);

        BaseMod.addUnlockBundle(currentBundle, player, index);

        if (MyModConfig.unlockEverything || UnlockTracker.unlockProgress.getInteger(player.toString() + "UnlockLevel") > index + 1) {
            UnlockTracker.unlockCard(card1);
            UnlockTracker.unlockCard(card2);
            UnlockTracker.unlockCard(card3);
        }
    }


    public static void registerUnlockRelicBundle(AbstractPlayer.PlayerClass player, int index, String relic1, String relic2, String relic3) {
        CustomUnlockBundle currentBundle;

        currentBundle = new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, relic1, relic2, relic3);

        UnlockTracker.addRelic(relic1);
        UnlockTracker.addRelic(relic2);
        UnlockTracker.addRelic(relic3);

        BaseMod.addUnlockBundle(currentBundle, player, index);

        if (MyModConfig.unlockEverything || UnlockTracker.unlockProgress.getInteger(player.toString() + "UnlockLevel") > index) {
            while (UnlockTracker.lockedRelics.contains(relic1)) {
                UnlockTracker.lockedRelics.remove(relic1);
            }
            while (UnlockTracker.lockedRelics.contains(relic2)) {
                UnlockTracker.lockedRelics.remove(relic2);
            }
            while (UnlockTracker.lockedRelics.contains(relic3)) {
                UnlockTracker.lockedRelics.remove(relic3);
            }
            UnlockTracker.markRelicAsSeen(relic1);
            UnlockTracker.markRelicAsSeen(relic2);
            UnlockTracker.markRelicAsSeen(relic3);
        }
    }

    public static void unlockModAchievement(String achievement) {
        if (Loader.isModLoaded("ModAchievement")){
            if (!UnlockTracker.isAchievementUnlocked(makeID(achievement))) {
                UnlockTracker.unlockAchievement(makeID(achievement));
            }
        }
    }


}
