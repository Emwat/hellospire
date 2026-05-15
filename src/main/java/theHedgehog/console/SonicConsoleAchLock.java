package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.SonicMod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static com.megacrit.cardcrawl.unlock.UnlockTracker.achievementPref;
import static theHedgehog.SonicMod.makeID;

// sample command:
// sonicach0 BlueHedgehog:Ringmaster

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleAchLock extends ConsoleCommand {
    public SonicConsoleAchLock() {
        maxExtraTokens = 2; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = false;
        /**
         * If this flag is true and you don't implement your own logic overriding the command syntax check function,
         * it checks if what is typed in is in the options you said the command has.
         * Note that this only applies to the autocompletion feature of the console, and has no bearing on what the command does when executed!
         * If unspecified, simpleCheck = false.
         */


        // followup.put("whateveryouwantmetobebaby", YourSecondCommand.class);
        /**
         * Doing this adds this word as a possible followup to your current command, and passes it to YourSecondCommand.
         * You may add as many of these as you like.
         */

    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (tokens.length < 2) {
            DevConsole.log("2 Parameters required");
            return;
        }
        String subcommand = tokens[1];
        DevConsole.log("locking achievement " + subcommand + ".");

        Lock(subcommand);
        // achievementPref.putBoolean(subcommand, false);
        // achievementPref.flush();
        DevConsole.log(subcommand + " command complete.");
        DevConsole.log("Restart required.");

        // DevConsole.log(subcommand + " locked.");
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        // SonicMod.logger.info("tokens " + tokens.length + " | depth : " + depth);
        ArrayList<String> result = new ArrayList<>();
        result.add(makeID("GooglyEyes"));
        result.add(makeID("KingOfRings"));
        result.add(makeID("SonicAllStar"));
        result.add(makeID("SuperSonic"));
        result.add(makeID("VigorAbuse"));

        if (tokens.length == 2) {
            complete = true;
        }

        return result;
    }

    private void Lock(String key) {
        final String CONFIG_FILE_NAME = "achievementsSave";
        SpireConfig achievementSave;

        try {
            achievementSave = new SpireConfig("ModAchievement", CONFIG_FILE_NAME);
            achievementSave.load();
            achievementSave.setBool(key, false);
            saveProperties(achievementSave);
            CardCrawlGame.mainMenuScreen.statsScreen.refreshData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties(SpireConfig achievementSave) {
        if (achievementSave != null) {
            try {
                achievementSave.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SonicMod.logger.error("achievementSave not saved!");
        }
    }
}
