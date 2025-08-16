package hellospire.util;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import hellospire.SonicMod;
import hellospire.character.SonicTipTracker;

import java.util.ArrayList;
import java.util.Map;

import static hellospire.SonicMod.makeID;
import static hellospire.character.SonicTipTracker.tips;

// valid commands:
// sonicunlock BlueHedgehog:Ringmaster

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleAchievement extends ConsoleCommand {
    public SonicConsoleAchievement() {
        maxExtraTokens = 1; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 1; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
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
        String subcommand = tokens[2];
        UnlockTracker.unlockAchievement(subcommand);
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        // SonicMod.logger.info("tokens " + tokens.length + " | depth : " + depth);
        ArrayList<String> result = new ArrayList<>();
        result.add(makeID("Ringmaster"));
        result.add(makeID("VigorAbuse"));

        if (tokens.length == 3) {
            complete = true;
        }

        return result;
    }
}
