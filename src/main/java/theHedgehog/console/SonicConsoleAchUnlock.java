package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.util.UnlockUtil.unlockModAchievement;

// sample commands:
// sonicach1 BlueHedgehog:Ringmaster

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleAchUnlock extends ConsoleCommand {
    public SonicConsoleAchUnlock() {
        maxExtraTokens = 2; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        // leave minExtraTokens to zero. The command will not execute if you don't provide the extra parameters and will not give an error message
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
        if (tokens.length < 3) {
            DevConsole.log("3 Parameters required");
            return;
        }
        String subcommand = tokens[1];
        String password = tokens[2];
        if (!("emerl".equals(password))) {
            DevConsole.log("Invalid password.");
            return;
        }
        unlockModAchievement(subcommand);
        // UnlockTracker.unlockAchievement(subcommand);
        DevConsole.log(subcommand + " unlocked.");
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
            result.add("pwd");
        }
        if (tokens.length == 3) {
            complete = true;
        }

        return result;
    }
}
