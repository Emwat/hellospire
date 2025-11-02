package theHedgehog.util;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import theHedgehog.SonicMod;
import theHedgehog.character.SonicTipTracker;

import java.util.ArrayList;
import java.util.Map;

import static theHedgehog.character.SonicTipTracker.tips;

// valid commands:
// sonictip check all
// sonictip see all
// sonictip unsee all
//

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleTip extends ConsoleCommand {
    public SonicConsoleTip() {
        maxExtraTokens = 2; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
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
        String firstToken = tokens[1];
        String specificTip = tokens.length >= 3 ? tokens[2] : null;
        boolean isSee = "see".equals(tokens[1]);

        if ("check".equals(firstToken)) {
            DevConsole.log("checking...");
            SonicMod.logger.info("checking...");

            for (Map.Entry<String, Boolean> tip : tips.entrySet()) {
                DevConsole.log(tip.getKey() + " : " + tip.getValue());
                SonicMod.logger.info(tip.getKey() + " : " + tip.getValue());
            }
        } else if (isSee == false && "all".equals(specificTip)) {
            SonicTipTracker.reset();
        } else if (isSee && "all".equals(specificTip)) {
            tips.replaceAll((k, v) -> true);
        } else {
            tips.put(specificTip, isSee);
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        if (tokens.length == 2) {
            result.add("check");
            result.add("see");
            result.add("unsee");
        }

        if (tokens[depth].equals("check")) {
            complete = true;
        } else if (tokens.length == 4) {
            complete = true;
            /**
             * Setting complete to true displays "Command is complete" in the autocomplete window.
             * This is not necessary if "simpleCheck = true" in the constructor and you don't have additional logic for it!
             */
        }

        if (tokens.length == 3) {
            for (Map.Entry<String, Boolean> tip : tips.entrySet()) {
                result.add(tip.getKey());
            }
        }

        return result;
    }
}
