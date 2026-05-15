package theHedgehog.console;

import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import theHedgehog.SonicMod;
import theHedgehog.events.EnemyBossSonic;

import java.util.ArrayList;
import java.util.Arrays;

// valid commands:
// chaog

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleSonicEnemy extends ConsoleCommand {
    public SonicConsoleSonicEnemy() {
        maxExtraTokens = 0; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = false;
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        MapRoomNode cur = AbstractDungeon.currMapNode;
        if (cur == null) {
            DevConsole.log("cannot fight when there is no map");
            return;
        }
        String[] encounterArray = Arrays.<String>copyOfRange(tokens, 1, tokens.length);
        String encounterName = "SonicEnemy";
        if (BaseMod.underScoreEncounterIDs.containsKey(encounterName))
            encounterName = (String)BaseMod.underScoreEncounterIDs.get(encounterName);
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            AbstractDungeon.monsterList.add(1, encounterName);
        } else {
            AbstractDungeon.monsterList.add(0, encounterName);
        }
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);
        node.room = (AbstractRoom)new MonsterRoom();
        ArrayList<MapEdge> curEdges = cur.getEdges();
        for (MapEdge edge : curEdges)
            node.addEdge(edge);
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.nextRoomTransitionStart();
    }
}
