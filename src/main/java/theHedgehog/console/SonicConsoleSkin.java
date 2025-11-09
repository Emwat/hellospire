package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHedgehog.character.ModSkinDictionary;
import theHedgehog.character.Sonic;

import java.util.ArrayList;

// valid commands:
// sonicskin

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleSkin extends ConsoleCommand {
    public SonicConsoleSkin() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
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

        if (!(AbstractDungeon.player instanceof Sonic)) {
            DevConsole.log("Character must be playing as Sonic.");
            return;
        }

        if (!ModSkinDictionary.getModAnimations().containsKey(firstToken)) {
            DevConsole.log(firstToken + " is invalid.");
            return;
        }

        ((Sonic)AbstractDungeon.player).setSkin(firstToken);
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        if (tokens.length == 2) {
            result.addAll(ModSkinDictionary.getModAnimations().keySet());
        }

        if (tokens.length == 3) {
            complete = true;
        }
        return result;
    }
}
