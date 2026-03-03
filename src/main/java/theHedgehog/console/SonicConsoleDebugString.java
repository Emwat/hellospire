package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHedgehog.SonicMod;
import theHedgehog.cards.*;

import java.util.ArrayList;
import java.util.Map;

import static theHedgehog.character.SonicTipTracker.tips;

// valid commands:
// chaog

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleDebugString extends ConsoleCommand {
    public SonicConsoleDebugString() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = false;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        if (tokens.length == 1) {
            DevConsole.log("This command requires a parameter");
        } else if (tokens[1].equals("check")) {
            DevConsole.log("SonicMod.modDebugString is \"" + SonicMod.modDebugString + "\"");
        } else if (tokens[1].equals("clear")) {
            SonicMod.modDebugString = "";
            DevConsole.log("SonicMod.modDebugString is now \"" + SonicMod.modDebugString + "\"");
        } else {
            SonicMod.modDebugString = ConcatTokens(tokens);
            DevConsole.log("SonicMod.modDebugString is now \"" + SonicMod.modDebugString + "\"");
        }
    }


    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        String input = ConcatTokens(tokens);

        result.add("check");
        result.add("clear");
        result.add("the Ironclad");
        result.add("the Silent");
        result.add("the Defect");
        result.add("the Watcher");
        result.add("sonicsfriends");

        if (tokens.length > 1 && (tokens[1].equals("check") || tokens[1].equals("clear"))) {
            complete = true;
            return result;
        }

        if (tokens.length == 2) {
            for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
                if (!CardCrawlGame.playerName.equals(character.name))
                    result.add(character.name);
            }
        } else if (tokens.length > 2) {
            // NotPerfect: Suggestions are wonky.
            for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
                if (!CardCrawlGame.playerName.equals(character.name)) {
                    String[] splitted = character.name.split("\\s+", tokens.length - 1);
                    if (splitted.length > 1) {
                        result.add(splitted[1]);
                    }
                }
            }
        }

        if (tokens.length > 2) {
            for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
                if (input.equals(character.name)){
                    complete = true;
                    break;
                }
            }
        }

        return result;
    }

    private String ConcatTokens(String[] tokens){
        StringBuilder output = new StringBuilder();
        for (int i = 1; i < tokens.length; i++) {
            output.append(tokens[i]);
            if (i != tokens.length - 1)
                output.append(" ");
        }
        return output.toString();
    }

}
