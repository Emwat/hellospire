package theHedgehog;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomUnlockBundle;
import basemod.devcommands.ConsoleCommand;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.helpers.CardBorderGlowManager;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.events.beyond.WindingHalls;
import com.megacrit.cardcrawl.events.city.TheLibrary;
import com.megacrit.cardcrawl.events.exordium.ShiningLight;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theHedgehog.actions.ModWaitAction;
import theHedgehog.cards.*;
import theHedgehog.character.ModSkinDictionary;
import theHedgehog.character.Sonic;
import theHedgehog.character.SonicStartTalkingHelper;
import theHedgehog.effects.ModMindblastEffect;
import theHedgehog.effects.SuperFinisherEffect;
import theHedgehog.events.*;
import theHedgehog.multiplayer.Skindexer;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.util.*;
import theHedgehog.character.SonicTipTracker;
import theHedgehog.potions.BasePotion;
import theHedgehog.relics.BaseRelic;
import theHedgehog.rewards.AssistReward;
import theHedgehog.rewards.RewardTypePatch;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;
import thePackmaster.SpireAnniversary5Mod;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static basemod.BaseMod.addMonster;
import static com.megacrit.cardcrawl.screens.GameOverScreen.isVictory;


@SpireInitializer
public class SonicMod implements
        AddAudioSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        OnCardUseSubscriber,
        OnStartBattleSubscriber,
        OnPlayerTurnStartSubscriber,
        PostExhaustSubscriber,
        PostBattleSubscriber,
        PostDeathSubscriber,
        SetUnlocksSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; // Edit your pom.xml to change this
    public static SpireConfig sonicmodConfig; // Used for implementing dropdown?? 05/26/2025 11:32 AM

    static {
        loadModInfo();
    }

    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); // Used to output to the console.

    // This is used to prefix the IDs of various objects like cards and relics,
    // to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    // This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new SonicMod();
        Sonic.Meta.registerColor();
    }

    public SonicMod() {
        BaseMod.subscribe(this); // This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
        ModSkinDictionary.initializeModSkins();

        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("anniv5")) {
            SpireAnniversary5Mod.subscribe(new PackLoader());
        }
        if (MyModConfig.enableCrossModIntegrations && Loader.isModLoaded("skindex") || Loader.isModLoaded("spireTogether")) {
            Skindexer.register();
        }
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));

        // ModPanel settingsPanel = new ModPanel();
        // settingsPanel.addUIElement(new DropDown(new ArrayList<String>() {{
        //    add("Enable for All Characters");
        //    add("Enable for Only Sonic");
        //    add("Disable");
        // }}, 455 * Settings.xScale, 763 * Settings.yScale, settingsPanel,
        //        new Label(FontHelper.buttonLabelFont, "Flag: ", 400 * Settings.xScale, 750 * Settings.yScale, 0, 1, Color.WHITE),
        //        dropdown -> {
        //            sonicmodConfig.setString(ConfigField.FLAG.id, dropdown.selection);
        //            saveConfig();
        //        },
        //        index -> {
        //            sonicmodConfig.setInt(ConfigField.INDEX.id, index);
        //            saveConfig();
        //        }));
        // saveConfig();

        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, new MyModConfig());

        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard card) {
                return card instanceof CrestOfFireCard && ((CrestOfFireCard) card).willBurnPlayer(card);
                // return true if "card" follows this rule, else return false
            }

            @Override
            public Color getColor(AbstractCard card) {
                return CrestOfFireCard.CREST_OF_FIRE_BURN_GLOW_COLOR.cpy();
                // return an instance of Color to be used as the color. e.g. Color.WHITE.cpy().
            }

            @Override
            public String glowID() {
                return makeID("CrestOfFireBurnGlow");
                // return a string to be used as a unique ID for this glow.
                // It's recommended to follow the usual modding convention of "modname:name"
            }
        });

        BaseMod.registerCustomReward(
                RewardTypePatch.ASSIST_LOCKIN,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    // I don't understand this code at all.
                    return AssistReward.Constructor2(rewardSave.type, rewardSave.id, 0, rewardSave.bonusGold);
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    AssistReward a = (AssistReward) customReward;

                    return new RewardSave(customReward.type.toString(), a.transformedAssist.cardID, 0, a.isAssistUpgraded ? 1 : 0);
                });


        registerPotions();
        registerMonsters();
        registerEvents();

        loadConfig();
        ConsoleCommand.addCommand("sonictip", SonicConsoleTip.class);
        ConsoleCommand.addCommand("sonicunlock", SonicConsoleUnlock.class);
        ConsoleCommand.addCommand("soniceverything", SonicConsoleEverything.class);
        ConsoleCommand.addCommand("sonicskin", SonicConsoleSkin.class);
        ConsoleCommand.addCommand("sss", SonicConsoleDevCustom.class);
    }

    // /// Used for DropDown
    // private void saveConfig() {
    //     try {
    //         sonicmodConfig.save();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    //
    // /// Used for DropDown
    // public static int getIndex()
    // {
    //     if (sonicmodConfig == null) return 0;
    //     return sonicmodConfig.getInt(ConfigField.INDEX.id);
    // }
    //
    // /// Used for DropDown
    // public enum ConfigField
    // {
    //     INDEX("Index");
    //     final String id;
    //     ConfigField(String val)
    //     {
    //         this.id = val;
    //     }
    // }

    private void registerEvents() {

        if (MyModConfig.enableEventsForAllCharacters) {
            BaseMod.addEvent(new AddEventParams.Builder(ChaoGardenEvent.ID, ChaoGardenEvent.class)
                    .dungeonID(TheCity.ID)
                    .eventType(EventUtils.EventType.FULL_REPLACE)
                    .overrideEvent(TheLibrary.ID)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(GravitySwitchEvent.ID, GravitySwitchEvent.class)
                    .eventType(EventUtils.EventType.FULL_REPLACE)
                    .overrideEvent(WindingHalls.ID)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(MissionEvent.ID, MissionEvent.class)
                    .dungeonID(TheBeyond.ID)
                    .eventType(EventUtils.EventType.NORMAL)
                    .endsWithRewardsUI(true)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(RougeEvent.ID, RougeEvent.class)
                    .dungeonID(TheCity.ID)
                    .eventType(EventUtils.EventType.NORMAL)
                    .create()
            );
        } else if (MyModConfig.enableEventsForOnlySonic) {
            BaseMod.addEvent(new AddEventParams.Builder(ChaoGardenEvent.ID, ChaoGardenEvent.class)
                    .dungeonID(TheCity.ID)
                    .playerClass(Sonic.Meta.THE_HEDGEHOG)
                    .eventType(EventUtils.EventType.FULL_REPLACE)
                    .overrideEvent(TheLibrary.ID)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(GravitySwitchEvent.ID, GravitySwitchEvent.class)
                    .playerClass(Sonic.Meta.THE_HEDGEHOG)
                    .eventType(EventUtils.EventType.FULL_REPLACE)
                    .overrideEvent(WindingHalls.ID)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(MissionEvent.ID, MissionEvent.class)
                    .dungeonID(TheBeyond.ID)
                    .playerClass(Sonic.Meta.THE_HEDGEHOG)
                    .eventType(EventUtils.EventType.NORMAL)
                    .endsWithRewardsUI(true)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(RougeEvent.ID, RougeEvent.class)
                    .dungeonID(TheCity.ID)
                    .spawnCondition(() ->
                            !(AbstractDungeon.player.hasRelic(CDFutureRelic.ID)))
                    .playerClass(Sonic.Meta.THE_HEDGEHOG)
                    .eventType(EventUtils.EventType.NORMAL)
                    .create()
            );

            BaseMod.addEvent(new AddEventParams.Builder(TimeStoneEvent.ID, TimeStoneEvent.class)
                    .dungeonID(TheCity.ID)
                    .spawnCondition(() ->
                            AbstractDungeon.player instanceof Sonic &&
                                    AbstractDungeon.player.getPrefs().getInteger("ASCENSION_LEVEL", 1) > 0)
                    .playerClass(Sonic.Meta.THE_HEDGEHOG)
                    .eventType(EventUtils.EventType.FULL_REPLACE)
                    .overrideEvent(ShiningLight.ID)
                    .create()
            );
        }
    }

    private void registerMonsters() {
        addMonster("TimeAttackLaga", () -> new MonsterGroup(new AbstractMonster[]{
                        new ModLagavulin(true, -465.0F, -20.0F),
                        new ModLagavulin(true, -130.0F, 15.0F),
                        new ModLagavulin(true, 200.0F, -5.0F)
                })
        );
    }

    private void registerPotions() {
        new AutoAdd(modID) // Loads files from this mod
                .packageFilter(BasePotion.class) // In the same package as this class
                .any(BasePotion.class, (info, potion) -> { // Run this code for any classes that extend this class
                    // These three null parameters are colors.
                    // If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    // This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    // playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }



    /*----------Localization----------*/

    // This is used to load the appropriate localization files based on language.
    private static String getLangString() {
        return Settings.language.name().toLowerCase();
    }

    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); // no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        // While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        // Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                localizationPath(lang, "StanceStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                localizationPath(lang, "MonsterStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "Tutorials.json"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            } catch (Exception e) {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION, info.COLOR);
        if (!info.ID.isEmpty()) {
            keywords.put(info.ID, info);
        }
    }

    // These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }

    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }

    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }

    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    public static String endingPath(String file) {
        return resourcesFolder + "/images/character/ending/" + file;
    }

    public static String audioPath(String file) {
        return resourcesFolder + "/audio/" + file;
    }

    public static String audioEngPath(String file) {
        return resourcesFolder + "/audio/eng/" + file;
    }

    public static String vfxPath(String file) {
        return resourcesFolder + "/images/vfx/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = SonicMod.class.getName(); // getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + SonicMod.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(SonicMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        Sonic.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) // Loads files from this mod
                .packageFilter(BaseCard.class) // In the same package as this class
                .setDefaultSeen(false) // And marks them as seen in the compendium
                .cards(); // Adds the cards

        BaseMod.removeCard(Gizoid.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidAmy.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidChaos.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidCream.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidE102r.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidEggman.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidKnuckles.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidRouge.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidShadow.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidSonic.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(GizoidTails.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(Extender1.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(Extender2.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(Extender3.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(LevelUpSpeedPick.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(LevelUpFlightPick.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(LevelUpPowerPick.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(RelaxPick1.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(RelaxPick2.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(BouncePadPick1.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(BouncePadPick2.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(BecauseSciencePick1.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(BecauseSciencePick2.ID, Sonic.Meta.CARD_COLOR);

        BaseMod.removeCard(Acceleration.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(Bait.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(BlastOff.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(DebugMode.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(PunchRush.ID, Sonic.Meta.CARD_COLOR);
        BaseMod.removeCard(SkyRing.ID, Sonic.Meta.CARD_COLOR);

        if (!Loader.isModLoaded("anniv5")) {
            BaseMod.removeCard(theHedgehog.cardsPackExclusive.Boost.ID, Sonic.Meta.CARD_COLOR);
            BaseMod.removeCard(theHedgehog.cardsPackExclusive.BouncePad.ID, Sonic.Meta.CARD_COLOR);
            BaseMod.removeCard(theHedgehog.cardsPackExclusive.HomingAttack.ID, Sonic.Meta.CARD_COLOR);
            BaseMod.removeCard(theHedgehog.cardsPackExclusive.Ring.ID, Sonic.Meta.CARD_COLOR);
            BaseMod.removeCard(theHedgehog.cardsPackExclusive.Trick.ID, Sonic.Meta.CARD_COLOR);
        }

        if (Loader.isModLoaded("skindex") || Loader.isModLoaded("spireTogether")) {
            BaseMod.removeCard(Ricochet.ID, Sonic.Meta.CARD_COLOR);
        }
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(SoundLibrary.LoseRings, audioPath("LoseRings.ogg"));
        BaseMod.addAudio(SoundLibrary.LetsBlastThrough, audioEngPath("sh_lets_blast_through_w_sonic_speed.ogg"));
        BaseMod.addAudio(SoundLibrary.ALLRIGHT, audioEngPath("sr_AllRight.ogg"));
        BaseMod.addAudio(SoundLibrary.COOL, audioEngPath("sr_Cool.ogg"));
        BaseMod.addAudio(SoundLibrary.OK, audioEngPath("sr_OK.ogg"));
        BaseMod.addAudio(SoundLibrary.OW, audioEngPath("sr_OW.ogg"));
        BaseMod.addAudio(SoundLibrary.YES, audioEngPath("sr_Yes.ogg"));
        BaseMod.addAudio(SoundLibrary.FeelingGood, audioEngPath("su_feelinggood.ogg"));
        BaseMod.addAudio(SoundLibrary.SmallYahoo, audioEngPath("sh_yahoo.ogg"));
        BaseMod.addAudio(SoundLibrary.SmallYes, audioEngPath("sh_Yes.ogg"));
        BaseMod.addAudio(SoundLibrary.SmallAllRight, audioEngPath("sh_AllRight.ogg"));
        BaseMod.addAudio(SoundLibrary.SmallAllRightLetsGo, audioEngPath("sh_AllRight_LetsGo.ogg"));
        BaseMod.addAudio(SoundLibrary.Hehe, audioEngPath("sh_Hehe.ogg"));
        BaseMod.addAudio(SoundLibrary.ThatsIt, audioEngPath("sh_Thats_It.ogg"));
        BaseMod.addAudio(SoundLibrary.BlastAway, audioEngPath("sh_Blast_Away.ogg"));
        BaseMod.addAudio(SoundLibrary.NeverUnderestimate, audioEngPath("sh_Never_Underestimate_Sonic_Speed.ogg"));
        BaseMod.addAudio(SoundLibrary.Bingo, audioEngPath("sh_bingo.ogg"));
        BaseMod.addAudio(SoundLibrary.PerfectBingo, audioEngPath("sh_Perfect_Bingo.ogg"));
        BaseMod.addAudio(SoundLibrary.TheHedgehog, audioEngPath("sa2_im_sonic_the_hedgehog.ogg"));

        BaseMod.addAudio(SoundLibrary.Amazing1, audioEngPath("sc_01_AMAZING.ogg"));
        BaseMod.addAudio(SoundLibrary.Amazing2, audioEngPath("sc_02_OUTSTANDING.ogg"));
//        BaseMod.addAudio(SoundLibrary.Amazing3, audioEngPath("03_GREAT.ogg"));
//        BaseMod.addAudio(SoundLibrary.Amazing4, audioEngPath("04_GOOD.ogg"));

        BaseMod.addAudio(SoundLibrary.QuickAir1, audioEngPath("su_01_V_SNC_000_b.ogg"));
        BaseMod.addAudio(SoundLibrary.QuickAir2, audioEngPath("su_01_V_SNC_001_a.ogg"));
        BaseMod.addAudio(SoundLibrary.QuickAir3, audioEngPath("su_01_V_SNC_002_a.ogg"));

        BaseMod.addAudio(SoundLibrary.BlueTornado, audioPath("bluetornado.ogg"));
        BaseMod.addAudio(SoundLibrary.Booster, audioPath("SE_Booster.ogg"));
        BaseMod.addAudio(SoundLibrary.Spring, audioPath("SE_Spring.ogg"));
        BaseMod.addAudio(SoundLibrary.Ring, audioPath("Ring.ogg"));

        BaseMod.addAudio(SoundLibrary.LevelUp, audioEngPath("sh_level_up.ogg"));

        BaseMod.addAudio(SoundLibrary.Attack1, audioEngPath("brawl_Attack01.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack2, audioEngPath("brawl_Attack02.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack3, audioEngPath("brawl_Attack03.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack4, audioEngPath("brawl_Attack04.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack5Go, audioEngPath("brawl_Attack05_Go.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack6, audioEngPath("brawl_Attack06.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack7, audioEngPath("brawl_Attack07.ogg"));
        BaseMod.addAudio(SoundLibrary.YESSS, audioEngPath("SBAT_325_SonicYESS.ogg"));

        BaseMod.addAudio(SoundLibrary.SonicStyle, audioEngPath("ult_super_sonic_style.ogg"));

        BaseMod.addAudio(SoundLibrary.DropDash, audioPath("DropDash.ogg"));
        BaseMod.addAudio(SoundLibrary.LightningShield, audioPath("LightningShield.ogg"));
        BaseMod.addAudio(SoundLibrary.StarPost, audioPath("StarPost.ogg"));

        BaseMod.addAudio(SoundLibrary.OmochaoPerfectLanding, audioEngPath("sri_00610_he_makes_a_perfect_landing_and.ogg"));
        BaseMod.addAudio(SoundLibrary.OmochaoIncorrectLanding, audioEngPath("sri_00616_ohh_he_failed_to_land_correctly.ogg"));
        BaseMod.addAudio(SoundLibrary.OmochaoTurbulence, audioEngPath("sri_00765_look_at_sonic_ride_that_turbulence.ogg"));

        BaseMod.addAudio(SoundLibrary.SonicsTheName, audioEngPath("brawl_Win01_SonicsTheName.ogg"));
        BaseMod.addAudio(SoundLibrary.TooEasy, audioEngPath("brawl_Win02_TooEasyPieceOfCake.ogg"));
        BaseMod.addAudio(SoundLibrary.HeyWeShould, audioEngPath("brawl_Win03_HeyWeShould.ogg"));

        BaseMod.addAudio(SoundLibrary.CatchMeIfYouCan, audioEngPath("sh_catchmeifyoucan.ogg"));
        BaseMod.addAudio(SoundLibrary.WhatsTheMatter, audioEngPath("sh_Whats_The_Matter.ogg"));
        BaseMod.addAudio(SoundLibrary.StepItUp, audioEngPath("brawl_Step_It_Up.ogg"));
        BaseMod.addAudio(SoundLibrary.TooSlow, audioEngPath("brawl_Too_Slow.ogg"));

        BaseMod.addAudio(SoundLibrary.Nooo, audioEngPath("brawl_Nooo.ogg"));
        BaseMod.addAudio(SoundLibrary.Shoot, audioEngPath("sh_shoot_not_my_day.ogg"));
        BaseMod.addAudio(SoundLibrary.Dead, audioPath("Dead.ogg"));
        BaseMod.addAudio(SoundLibrary.LongLiveTheEggmanEmpire, audioEngPath("sa2_Long_Live_The_Eggman_Empire.ogg"));

        BaseMod.addAudio(SoundLibrary.SpeedBreak, audioEngPath("satsr_Speed_Break.ogg"));
        BaseMod.addAudio(SoundLibrary.TimeBreak, audioEngPath("satsr_Time_Break.ogg"));

        BaseMod.addAudio(SoundLibrary.Amy, audioEngPath("sh_amy_herewego.ogg"));
        BaseMod.addAudio(SoundLibrary.Big, audioEngPath("sh_big_myturn.ogg"));
        BaseMod.addAudio(SoundLibrary.Blaze, audioEngPath("sr_blaze_youcantescapeme.ogg"));
        BaseMod.addAudio(SoundLibrary.Charmy, audioEngPath("sh_charmy_illtakethelead.ogg"));
        BaseMod.addAudio(SoundLibrary.Chao, audioPath("sa2_chao.ogg"));
        BaseMod.addAudio(SoundLibrary.Chip, audioEngPath("su_chip_myturnsonic.ogg"));
        BaseMod.addAudio(SoundLibrary.Cream, audioEngPath("sh_cream_herewego.ogg"));
        BaseMod.addAudio(SoundLibrary.CuteCouple, audioEngPath("sa1_0509_Cute_Couples.ogg"));
        BaseMod.addAudio(SoundLibrary.Espio, audioEngPath("sh_espio_letsgo.ogg"));
        BaseMod.addAudio(SoundLibrary.Jet, audioEngPath("sri_jet_nowayimgonnalose.ogg"));
        BaseMod.addAudio(SoundLibrary.JetSneeze, audioEngPath("sri_jet_achoo.ogg"));
        BaseMod.addAudio(SoundLibrary.JetWhat, audioEngPath("sri_jet_what.ogg"));
        BaseMod.addAudio(SoundLibrary.Knuckles, audioEngPath("sh_knux_gotit.ogg"));
        BaseMod.addAudio(SoundLibrary.Rouge, audioEngPath("sh_rouge_illtakeitfromhere.ogg"));
        BaseMod.addAudio(SoundLibrary.Shadow, audioEngPath("sh_shadow_illtakeitfromhere.ogg"));
        BaseMod.addAudio(SoundLibrary.Silver, audioEngPath("s06_silver_itsnouse.ogg"));
        BaseMod.addAudio(SoundLibrary.Sticks, audioEngPath("boom_sticks_imready.ogg"));
        BaseMod.addAudio(SoundLibrary.Tails, audioEngPath("sh_tails_leaveittome.ogg"));
        BaseMod.addAudio(SoundLibrary.Vector, audioEngPath("sh_vector_herewego.ogg"));
        BaseMod.addAudio(SoundLibrary.MetalHaha, audioEngPath("sh_metal_hahaha.ogg"));
        BaseMod.addAudio(SoundLibrary.MetalData, audioEngPath("sh_metal_alllifeformdata.ogg"));
        BaseMod.addAudio(SoundLibrary.MetalAppropriate, audioEngPath("sh_metal_how_appropriate.ogg"));

        BaseMod.addAudio(SoundLibrary.Boost, audioPath("su_boost.ogg"));
        BaseMod.addAudio(SoundLibrary.Jump, audioPath("su_jump.ogg"));
        BaseMod.addAudio(SoundLibrary.Rail, audioPath("su_rail.ogg"));
        BaseMod.addAudio(SoundLibrary.SonicBoom, audioPath("su_sonicboom.ogg"));
        BaseMod.addAudio(SoundLibrary.Trick, audioPath("su_trickpress.ogg"));
        BaseMod.addAudio(SoundLibrary.TrickOK, audioPath("su_trick_ok.ogg"));

    }

    public static int attackCardsPlayedThisTurn = 0;
    public static int cardsExhaustedThisTurn = 0;
    public static boolean sawMetalRelic = false;
    public static final int RANK_S_REWARD = 100;
    public static final int RANK_A_REWARD = 70;
    public static final int RANK_B_REWARD = 50;
    public static final int RANK_C_REWARD = 20;

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        cardsExhaustedThisTurn++;
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        attackCardsPlayedThisTurn = 0;
        cardsExhaustedThisTurn = 0;
        Trick.TricksPlayed = 0;
        Trick.firstTrickNumber = 0;
        SlotMachineStatus.hasAppliedDebuff = false;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard.type == AbstractCard.CardType.ATTACK) {
            attackCardsPlayedThisTurn++;
            if (AbstractDungeon.player instanceof Sonic && (
                    abstractCard.damage >= 100 ||
                            (abstractCard.cardID.equals(Whirlwind.ID) && abstractCard.damage * 3 >= 100) ||
                            (abstractCard.cardID.equals(DoubleAirKick.ID) && abstractCard.damage * 2 >= 100))
            ) {
                final float MINDBLAST_H = 146.0F;
                final float TOPBAR_H = Settings.HEIGHT - (Settings.isMobile ? 164.0F * Settings.scale : 128.0F * Settings.scale) + (MINDBLAST_H / 2);
                AbstractDungeon.actionManager.addToTop(new ModWaitAction(2f));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SuperFinisherEffect(
                    abstractCard.hasTag(SonicTags.ERA_CLASSIC) ? SonicTags.ERA_CLASSIC :
                    abstractCard.hasTag(SonicTags.ERA_ADVENTURE) ? SonicTags.ERA_ADVENTURE :
                    abstractCard.hasTag(SonicTags.ERA_MODERN) ? SonicTags.ERA_MODERN :
                    SonicTags.ERA_ADVENTURE
                )));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ModMindblastEffect(0, TOPBAR_H, false)));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ModMindblastEffect(0, 0, false, true)));
                AbstractDungeon.actionManager.addToTop(SoundLibrary.AlwaysPlayVoiceAction(SoundLibrary.BlastAway));
                AbstractDungeon.actionManager.addToTop(SoundLibrary.AlwaysPlayVoiceAction(SoundLibrary.BlastAway));

            }
        }

        // AbstractDungeon.actionManager.addToBottom(new ModXFastAction(() -> {
        //     for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
        //         if (monster.hasPower(DizzyPower.POWER_ID)){
        //             DizzyPower dizzyPower = (DizzyPower) monster.getPower(DizzyPower.POWER_ID);
        //             dizzyPower.calculateHighestCost(abstractCard);
        //         }
        //     }
        // }));
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if (!(AbstractDungeon.player instanceof Sonic)) {
            return;
        }
        String monsterName = abstractRoom.monsters.getMonsterNames().get(0);

        ArrayList<String> bosses = new ArrayList<>(Arrays.asList(
                "Hexaghost",
                "SlimeBoss",
                "TheGuardian",

                "BronzeAutomaton",
                "Champ",
                "TheCollector",

                "AwakenedOne",
                "Deca",
                "TimeEater"
        ));

        if (bosses.contains(monsterName) && MyModConfig.enableVoice && SoundLibrary.isRandomlyTrue()) {
            CardCrawlGame.sound.play(SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                    SoundLibrary.SonicsTheName,
                    SoundLibrary.TooEasy,
                    SoundLibrary.HeyWeShould
            ))));
        }
    }

    @Override
    public void receivePostDeath() {
        if (AbstractDungeon.player instanceof Sonic) {
            if (!isVictory) {
                if (MyModConfig.enableVoice && SoundLibrary.isRandomlyTrue()) {
                    CardCrawlGame.sound.play(SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                            SoundLibrary.Nooo,
                            SoundLibrary.Dead,
                            SoundLibrary.Shoot,
                            SoundLibrary.LongLiveTheEggmanEmpire
                    ))));
                }

            }
        }
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID) // Loads files from this mod
                .packageFilter(BaseRelic.class) // In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { // Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); // Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); // Register a shared or base game character specific relic

                    // If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    // If you want all your relics to be visible by default, just remove this if statement.
//                    if (info.seen)
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveSetUnlocks() {
        // UnlockTracker.markCardAsSeen(AssistAmy.ID);
        // UnlockTracker.markCardAsSeen(AssistBarry.ID);
        // UnlockTracker.markCardAsSeen(AssistBig.ID);
        // UnlockTracker.markCardAsSeen(AssistBlaze.ID);
        // UnlockTracker.markCardAsSeen(AssistChip.ID);
        // UnlockTracker.markCardAsSeen(AssistCream.ID);
        // UnlockTracker.markCardAsSeen(AssistKnuckles.ID);
        // UnlockTracker.markCardAsSeen(AssistRosy.ID);
        // UnlockTracker.markCardAsSeen(AssistRouge.ID);
        // UnlockTracker.markCardAsSeen(AssistShadow.ID);
        // UnlockTracker.markCardAsSeen(AssistSticks.ID);
        // UnlockTracker.markCardAsSeen(AssistTails.ID);
        // UnlockTracker.markCardAsSeen(AssistTikal.ID);

        UnlockTracker.addCard(FireTackle.ID);
        UnlockTracker.addCard(FireSomersault.ID);
        UnlockTracker.addCard(VolcanoSlider.ID);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD,
                FireTackle.ID,
                FireSomersault.ID,
                VolcanoSlider.ID), Sonic.Meta.THE_HEDGEHOG, 0);

        UnlockTracker.addCard(MagicHands.ID);
        UnlockTracker.addCard(Relax.ID);
        UnlockTracker.addCard(SlotMachineGame.ID);

        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.CARD,
                MagicHands.ID,
                Relax.ID,
                SlotMachineGame.ID), Sonic.Meta.THE_HEDGEHOG, 1);
    }

    private void loadConfig() {
        // info.Name = The Hedgehog
        String configName = "sonicmod";
        try {
            sonicmodConfig = new SpireConfig(configName, configName + ".config");
            sonicmodConfig.load();
            SonicTipTracker.initialize();
        } catch (Exception ex) {
            logger.catching(ex);
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        if (SoundLibrary.isRandomlyTrue()) {
            SonicStartTalkingHelper.Chat(abstractRoom);
        }
    }

    // public static SingleCardReward hoverRewardWorkaround;
    // @Override
    // public void receivePostRender(SpriteBatch sb) {
    //     if(hoverRewardWorkaround != null) {
    //         hoverRewardWorkaround.renderCardOnHover(sb);
    //         hoverRewardWorkaround = null;
    //     }
    //     BrokenSpaceZone.shaderTimer += Gdx.graphics.getDeltaTime();
    // }

}
