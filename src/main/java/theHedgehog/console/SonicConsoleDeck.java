package theHedgehog.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHedgehog.cards.*;
import theHedgehog.relics.*;

import java.util.ArrayList;

// valid commands:
// sonicdeck

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleDeck extends ConsoleCommand {
    public SonicConsoleDeck() {
        maxExtraTokens = 1; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 1; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        if (tokens.length > 1) {
            String firstToken = tokens[1];

            if (firstToken.equals("casino")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(1, GetCasino());
                // AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new CDFutureRelic());
                // AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new PowerBrakeRelic());
            } else if (firstToken.equals("chaos")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(1, GetChaosAttacks());
            } else if (firstToken.equals("everything")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(3, GetEverything());
            } else if (firstToken.equals("future")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(1, GetFuture());
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new CDFutureRelic());
            } else if (firstToken.equals("past")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(1, GetPast());
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new CDPastRelic());
            } else if (firstToken.equals("relics")) {
                int floor = -1;
                if (tokens.length == 3) {
                    floor = Integer.parseInt(tokens[2]);
                }
                CardCrawlGame.music.justFadeOutTempBGM();
                GetRandomRelicsPerFloor(floor);
            } else if (firstToken.equals("sonicsfriends")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                AddCardsToMasterDeck(1, GetSonicsFriends());
            } else if (firstToken.equals("upgs")) {
                int floor = -1;
                if (tokens.length == 3) {
                    floor = Integer.parseInt(tokens[2]);
                }
                CardCrawlGame.music.justFadeOutTempBGM();
                GetRandomUpgradesPerFloor(floor);
            } else if (firstToken.equals("catchup")) {
                int floor = -1;
                if (tokens.length == 3) {
                    floor = Integer.parseInt(tokens[2]);
                }
                CardCrawlGame.music.justFadeOutTempBGM();
                GetRandomRelicsPerFloor(floor);
                GetRandomUpgradesPerFloor(floor);
            } else if (firstToken.equals("testrelics")) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new StampRelic());
                GetEveryRelic();
            }
        }
    }

    private float randomX() {
        return MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
    }

    private float randomY() {
        return MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("catchup");
        result.add("casino");
        result.add("chaos");
        result.add("everything");
        result.add("future");
        result.add("past");
        result.add("relics");
        result.add("sonicsfriends");
        result.add("upgs");

        return result;
    }

    private void AddCardsToMasterDeck(int cycles, ArrayList<AbstractCard> everyCard) {
        for (int i = 0; i < cycles; i++) {
            for (AbstractCard c : everyCard) {
                if (c.isInnate && i > 0) {
                    continue;
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeStatEquivalentCopy(), randomX(), randomY()));
            }
        }
    }

    private void GetRandomRelicsPerFloor(int floor) {
        int relicsForEveryXFloors = 6;
        int currentFloor = AbstractDungeon.floorNum;
        if (floor > 0) {
            currentFloor = floor;
        }
        int formula = currentFloor / relicsForEveryXFloors;
        DevConsole.log("Sending you " + formula + " relics.");

        for (int i = 0; i < formula; i++) {
            AbstractRelic.RelicTier relicTier = AbstractDungeon.returnRandomRelicTier();
            AbstractRelic relic = AbstractDungeon.returnRandomRelic(relicTier);
            if (relic instanceof DeadBranch) {
                relic = new Mango().makeCopy();
            }
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), relic);
        }

        if (currentFloor >= 18) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new BustedCrown());
        }
        if (currentFloor >= 35) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new SacredBark());
        }
    }

    private void GetRandomUpgradesPerFloor(int floor) {
        int upgradesForEveryXFloors = 6;
        int currentFloor = AbstractDungeon.floorNum;
        if (floor > 0) {
            currentFloor = floor;
        }
        int formula = currentFloor / upgradesForEveryXFloors;
        DevConsole.log("Sending you " + formula + " upgrades.");

        for (int i = 0; i < formula; i++) {
            int generatedRandomNumber = (int) (Math.random() * AbstractDungeon.player.masterDeck.size());
            AbstractCard randomCard = AbstractDungeon.player.masterDeck.group.get(generatedRandomNumber);
            randomCard.upgrade();

            if (i < 20) {
                float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(randomCard.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            }
        }
    }

    private void GetEveryRelic() {
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new WarpedTongs());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new ChaoRelic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new CrystalRingRelic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new FireSoulRelic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new GoldenGloveRelic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new Player2Relic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new PowerBrakeRelic());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(randomX(), randomY(), new RingEnergyBonusRelic());
    }

    private ArrayList<AbstractCard> GetCasino() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new BackSpinKick());
        cards.add(new HeavyBounceSlam());
        cards.add(new MeteorKick());
        cards.add(new SpinningNeedleAttack());
        cards.add(new SpinningNeedleAttack());
        cards.add(new Windmill());
        cards.add(new Windmill());

        cards.add(new Bumper());
        cards.add(new Bumper());
        cards.add(new DropDash());
        cards.add(new WallJump());
        cards.add(new WallJump());

        cards.add(new Assist());
        cards.add(new AssistAmy());
        cards.add(new AssistCharmy());
        cards.add(new AssistSticks());
        cards.add(new AssistJet());

        cards.add(new DashPanel());
        cards.add(new BlueTornado());
        cards.add(new DizzySpin());
        cards.add(new SpeedBreak());

        cards.add(new SlotMachineGame());
        cards.add(new MagicHands());
        cards.add(new Relax());

        cards.add(new Drift());

        cards.add(new Momentum());

        return cards;
    }

    private ArrayList<AbstractCard> GetChaosAttacks() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new BackSpinKickRare());
        cards.add(new BoostRare());
        cards.add(new FalconPunchRare());
        cards.add(new InstaShieldRare());
        cards.add(new ScissorKickRare());
        cards.add(new SonicEagleRare());
        cards.add(new TeaserRare());

        return cards;
    }

    private ArrayList<AbstractCard> GetFuture() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new DirectJump());
        cards.add(new RampJump());
        cards.add(new AssistEspio());
        cards.add(new AssistCharmy());
        cards.add(new AssistVector());
        cards.add(new SlotMachineGame());
        cards.add(new ScissorKick());
        cards.add(new VolcanoSlider());

        return cards;
    }

    private ArrayList<AbstractCard> GetEverything() {
        ArrayList<AbstractCard> everyCard = new ArrayList<>();
        everyCard.add(new BackSpinKickRare());
        everyCard.add(new BoostRare());
        everyCard.add(new FalconPunchRare());
        everyCard.add(new InstaShieldRare());
        everyCard.add(new ScissorKickRare());
        everyCard.add(new SonicEagleRare());
        everyCard.add(new TeaserRare());
        everyCard.add(new ChaosEmeraldAttack());

        everyCard.add(new TeaserRareAttack1());
        everyCard.add(new TeaserRareAttack1Multi());
        everyCard.add(new TeaserRareAttack2());

        // everyCard.add(new Acceleration());
        everyCard.add(new AMAZING());
        everyCard.add(new Assist());
        everyCard.add(new AssistAmy());
        everyCard.add(new AssistBarry());
        everyCard.add(new AssistBig());
        everyCard.add(new AssistBarry());
        everyCard.add(new AssistBig());
        everyCard.add(new AssistBlaze());
        everyCard.add(new AssistCharmy());
        everyCard.add(new AssistChip());
        everyCard.add(new AssistCream());
        everyCard.add(new AssistEspio());
        everyCard.add(new AssistJet());
        everyCard.add(new AssistKnuckles());
        everyCard.add(new AssistLilac());
        // everyCard.add(new AssistRosy());
        everyCard.add(new AssistRouge());
        everyCard.add(new AssistShadow());
        everyCard.add(new AssistSilver());
        everyCard.add(new AssistSticks());
        everyCard.add(new AssistTails());
        everyCard.add(new AssistTikal());
        everyCard.add(new Athleticism());
        everyCard.add(new BackSpinKick());
        // everyCard.add(new Bait());
        // everyCard.add(new BecauseScience());
        everyCard.add(new BlastOff());
        everyCard.add(new BlastProcessing());
        // everyCard.add(new BlueBlur());
        // everyCard.add(new BlueBomber());
        everyCard.add(new Boost());
        everyCard.add(new BouncePad());
        everyCard.add(new Bumper());
        everyCard.add(new Checkpoint());
        everyCard.add(new ClawsUnleashed());
        // everyCard.add(new CloseShave());
        everyCard.add(new Crouch());
        everyCard.add(new DashPanel());
        everyCard.add(new DebugMode());
        everyCard.add(new DirectJump());
        everyCard.add(new DizzySpin());
        everyCard.add(new DoubleAirKick());
        everyCard.add(new Drift());
        everyCard.add(new DropDash());
        everyCard.add(new EndlessBoost());
        everyCard.add(new Enerbeam());
        everyCard.add(new Extender());
        everyCard.add(new FalconPunch());
        everyCard.add(new FireSomersault());
        everyCard.add(new FireTackle());
        everyCard.add(new FlagPole());
        everyCard.add(new FootSweep());
        everyCard.add(new GrindRail());
        everyCard.add(new Hailstorm());
        everyCard.add(new HeavyBounceSlam());
        everyCard.add(new HomingAttack());
        everyCard.add(new HorseKick());
        everyCard.add(new HotRod());
        everyCard.add(new InstaShield());
        everyCard.add(new LevelUp());
        everyCard.add(new LightSpeedAttack());
        everyCard.add(new LightSpeedDash());
        everyCard.add(new LoopDeLoop());
        everyCard.add(new MagicHands());
        everyCard.add(new MeteorKick());
        everyCard.add(new Momentum());
        everyCard.add(new NiceSmile());
        everyCard.add(new PeelOut());
        everyCard.add(new Piping());
        // everyCard.add(new PunchRush());
        everyCard.add(new QuickAir());
        everyCard.add(new QuickStep());
        everyCard.add(new RampJump());
        everyCard.add(new Relax());
        everyCard.add(new Ricochet());
        everyCard.add(new Ring());
        everyCard.add(new ScissorKick());
        everyCard.add(new SecretRoute());
        everyCard.add(new Shield());
        everyCard.add(new Shortcut());
        // everyCard.add(new SkyRing());
        everyCard.add(new Slide());
        everyCard.add(new SlotMachineGame());
        everyCard.add(new SmoothLanding());
        everyCard.add(new SonicBoom());
        everyCard.add(new SonicEagle());
        everyCard.add(new SonicFlare());
        everyCard.add(new SonicWave());
        // everyCard.add(new SonicWind());
        everyCard.add(new SpeedBreak());
        everyCard.add(new SpeedUp());
        everyCard.add(new SpinDash());
        everyCard.add(new SpinningNeedleAttack());
        everyCard.add(new Strike());
        everyCard.add(new SuperSonicForm());
        everyCard.add(new Taunt());
        everyCard.add(new Teaser());
        everyCard.add(new ThunderShield());
        everyCard.add(new TopKick());
        everyCard.add(new Trick());
        everyCard.add(new TrickFinisher());
        everyCard.add(new Trickster());
        everyCard.add(new TripleKick());
        everyCard.add(new Turbulence());
        everyCard.add(new WallJump());
        everyCard.add(new Whirlwind());
        everyCard.add(new Windmill());
        everyCard.add(new WindUpPunch());
        return everyCard;
    }

    private ArrayList<AbstractCard> GetPast() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new LightSpeedDash());
        cards.add(new LightSpeedAttack());
        cards.add(new Momentum());
        cards.add(new DirectJump());
        cards.add(new WallJump());
        cards.add(new BouncePad());
        cards.add(new SpinDash());

        return cards;
    }

    private ArrayList<AbstractCard> GetSonicsFriends() {
        ArrayList<AbstractCard> characterCards = new ArrayList<>();
        characterCards.add(new AssistAmy());
        characterCards.add(new AssistBarry());
        characterCards.add(new AssistBig());
        characterCards.add(new AssistBlaze());
        characterCards.add(new AssistCharmy());
        characterCards.add(new AssistChip());
        characterCards.add(new AssistCream());
        characterCards.add(new AssistEspio());
        characterCards.add(new AssistJet());
        characterCards.add(new AssistKnuckles());
        characterCards.add(new AssistLilac());
        characterCards.add(new AssistRouge());
        characterCards.add(new AssistShadow());
        characterCards.add(new AssistSilver());
        characterCards.add(new AssistSticks());
        characterCards.add(new AssistTails());
        characterCards.add(new AssistTikal());
        characterCards.add(new AssistVector());
        return characterCards;
    }
}
