package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

import static theHedgehog.SonicMod.makeID;

// valid commands:
// sonicunlock

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleUnlock extends ConsoleCommand {
    public SonicConsoleUnlock() {
        maxExtraTokens = 1; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
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

        String subcommand = tokens.length > 1 ? tokens[1] : "";

        if (subcommand.isEmpty() || subcommand.equals("check")){
            DevConsole.log("UnlockLevel: " + UnlockTracker.getUnlockLevel(Sonic.Meta.THE_HEDGEHOG));
            DevConsole.log("CurrentProgress: " + UnlockTracker.getCurrentProgress(Sonic.Meta.THE_HEDGEHOG));
        } else if (subcommand.equals("reset")) {
            UnlockTracker.resetUnlockProgress(Sonic.Meta.THE_HEDGEHOG);
            DevConsole.log(subcommand + " unlock progress reset.");
        }

    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        // SonicMod.logger.info("tokens " + tokens.length + " | depth : " + depth);
        ArrayList<String> result = new ArrayList<>();
        result.add("check");
        result.add("reset");

        return result;
    }
}
