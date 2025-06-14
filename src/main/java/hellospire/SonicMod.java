package hellospire;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.helpers.CardBorderGlowManager;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardSave;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hellospire.cards.BaseCard;
import hellospire.cards.CrestOfFireCard;
import hellospire.character.Sonic;
//import hellospire.ui.FlagDropDown;
import hellospire.powers.RingPower;
import hellospire.rewards.AssistReward;
import hellospire.rewards.RewardTypePatch;
import hellospire.util.GeneralUtils;
import hellospire.util.KeywordInfo;
import hellospire.util.TextureLoader;
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

import java.nio.charset.StandardCharsets;
import java.util.*;

@SpireInitializer
public class SonicMod implements
        EditCharactersSubscriber,
        EditCardsSubscriber,
        AddAudioSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        OnPlayerTurnStartSubscriber,
        OnCardUseSubscriber,
        OnStartBattleSubscriber,
        PostExhaustSubscriber,
//        PostBattleSubscriber,
        PostDeathSubscriber,
        PostInitializeSubscriber {
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    public static SpireConfig modConfig = null; //Used for implementing dropdown?? 05/26/2025 11:32 AM


    static {
        loadModInfo();
    }

    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new SonicMod();
        Sonic.Meta.registerColor();
    }

    public SonicMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));

//        ModPanel settingsPanel = new ModPanel();
//        settingsPanel.addUIElement(new FlagDropDown(new ArrayList<String>() {{
//            add("English");
//            add("Japanese");
//            add("None");
//        }}, 455 * Settings.xScale, 763 * Settings.yScale, settingsPanel,
//                new Label(FontHelper.buttonLabelFont, "Flag: ", 400 * Settings.xScale, 750 * Settings.yScale, 0, 1, Color.WHITE),
//                dropdown -> {
//                    modConfig.setString(ConfigField.FLAG.id, dropdown.selection);
//                    FlagPreview.setFlag(dropdown.selection);
//                    saveConfig();
//                },
//                index -> {
//                    modConfig.setInt(ConfigField.INDEX.id, index);
//                    saveConfig();
//                }));

        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, new MyModConfig());

        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard card) {
                return card instanceof CrestOfFireCard && ((CrestOfFireCard) card).willBurnPlayer(card);
                //return true if "card" follows this rule, else return false
            }

            @Override
            public Color getColor(AbstractCard card) {
                return CrestOfFireCard.CREST_OF_FIRE_BURN_GLOW_COLOR.cpy();
                //return an instance of Color to be used as the color. e.g. Color.WHITE.cpy().
            }

            @Override
            public String glowID() {
                return makeID("CrestOfFireBurnGlow");
                //return a string to be used as a unique ID for this glow.
                //It's recommended to follow the usual modding convention of "modname:name"
            }
        });

        BaseMod.registerCustomReward(
                RewardTypePatch.ASSIST_LOCKIN,
                (rewardSave) -> { // this handles what to do when this quest type is loaded.
                    // I don't understand this code at all.
                    return new AssistReward(null, null, null);
                },
                (customReward) -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null, 0, 0);
                });
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
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
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
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
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty()) {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
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

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = SonicMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
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
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
    }


    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(SoundLibrary.ALLRIGHT, audioEngPath("sr_AllRight.ogg"));
        BaseMod.addAudio(SoundLibrary.COOL, audioEngPath("sr_Cool.ogg"));
        BaseMod.addAudio(SoundLibrary.OK, audioEngPath("sr_OK.ogg"));
        BaseMod.addAudio(SoundLibrary.OW, audioEngPath("sr_OW.ogg"));
        BaseMod.addAudio(SoundLibrary.YES, audioEngPath("sr_Yes.ogg"));
        BaseMod.addAudio(SoundLibrary.FeelingGood, audioEngPath("su_feelinggood.ogg"));

        BaseMod.addAudio(SoundLibrary.Amazing1, audioEngPath("sc_01_AMAZING.ogg"));
        BaseMod.addAudio(SoundLibrary.Amazing2, audioEngPath("sc_02_OUTSTANDING.ogg"));
//        BaseMod.addAudio(SoundLibrary.Amazing3, audioEngPath("03_GREAT.ogg"));
//        BaseMod.addAudio(SoundLibrary.Amazing4, audioEngPath("04_GOOD.ogg"));

        BaseMod.addAudio(SoundLibrary.QuickAir1, audioEngPath("su_01_V_SNC_000_b.ogg"));
        BaseMod.addAudio(SoundLibrary.QuickAir2, audioEngPath("su_01_V_SNC_001_a.ogg"));
        BaseMod.addAudio(SoundLibrary.QuickAir3, audioEngPath("01_V_SNC_002_b.ogg"));

        BaseMod.addAudio(SoundLibrary.BlueTornado, audioPath("bluetornado.ogg"));
        BaseMod.addAudio(SoundLibrary.Booster, audioPath("SE_Booster.ogg"));
        BaseMod.addAudio(SoundLibrary.Spring, audioPath("SE_Spring.ogg"));
        BaseMod.addAudio(SoundLibrary.Ring, audioPath("Ring.ogg"));

        BaseMod.addAudio(SoundLibrary.LevelUp, audioEngPath("sh_level_up.ogg"));

        BaseMod.addAudio(SoundLibrary.Attack1, audioEngPath("smashult_vc_sonic_attack01.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack2, audioEngPath("smashult_vc_sonic_attack02.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack3, audioEngPath("smashult_vc_sonic_attack03.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack4, audioEngPath("smashult_vc_sonic_attack04.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack5, audioEngPath("smashult_vc_sonic_attack05.ogg"));
        BaseMod.addAudio(SoundLibrary.Attack6, audioEngPath("smashult_vc_sonic_attack06.ogg"));

        BaseMod.addAudio(SoundLibrary.SonicStyle, audioEngPath("smashult_vc_sonic_super_sonic_style.ogg"));

        BaseMod.addAudio(SoundLibrary.DropDash, audioPath("DropDash.ogg"));
        BaseMod.addAudio(SoundLibrary.LightningShield, audioPath("LightningShield.ogg"));
        BaseMod.addAudio(SoundLibrary.Jump, audioPath("Jump.ogg"));
        BaseMod.addAudio(SoundLibrary.StarPost, audioPath("StarPost.ogg"));

        BaseMod.addAudio(SoundLibrary.OmochaoPerfectLanding, audioEngPath("sri_00610_he_makes_a_perfect_landing_and.ogg"));
        BaseMod.addAudio(SoundLibrary.OmochaoIncorrectLanding, audioEngPath("sri_00616_ohh_he_failed_to_land_correctly.ogg"));
        BaseMod.addAudio(SoundLibrary.OmochaoTurbulence, audioEngPath("sri_00765_look_at_sonic_ride_that_turbulence.ogg"));

        BaseMod.addAudio(SoundLibrary.SonicsTheName, audioEngPath("snd_vc_Sonic_Win01_Sonics_The_Name.ogg"));
        BaseMod.addAudio(SoundLibrary.TooEasy, audioEngPath("snd_vc_Sonic_Win02_Too_Easy.ogg"));
        BaseMod.addAudio(SoundLibrary.HeyWeShould, audioEngPath("snd_vc_Sonic_Win03_Hey_We_Should.ogg"));

        BaseMod.addAudio(SoundLibrary.WindUpPunchGo, audioEngPath("snd_vc_Sonic_Attack05_Go.ogg"));
        BaseMod.addAudio(SoundLibrary.Nooo, audioEngPath("snd_vc_Sonic_Nooo.ogg"));
        BaseMod.addAudio(SoundLibrary.Dead, audioPath("Dead.ogg"));
        BaseMod.addAudio(SoundLibrary.LongLiveTheEggmanEmpire, audioEngPath("sa2_Long_Live_The_Eggman_Empire.ogg"));

        BaseMod.addAudio(SoundLibrary.BossMusic, resourcesFolder + "/audio/music/" + "MetalScratchin2.mp3");

        BaseMod.addAudio(SoundLibrary.SpeedBreak, audioEngPath("satsr_VOICE_E_2_Speed_Break.ogg"));
        BaseMod.addAudio(SoundLibrary.TimeBreak, audioEngPath("satsr_VOICE_E_24_Time_Break.ogg"));

        BaseMod.addAudio(SoundLibrary.Amy, audioEngPath("sh_amy_herewego.ogg"));
        BaseMod.addAudio(SoundLibrary.Big, audioEngPath("sh_big_myturn.ogg"));
        BaseMod.addAudio(SoundLibrary.Blaze, audioEngPath("sr_blaze_youcantescapeme.ogg"));
        BaseMod.addAudio(SoundLibrary.Cream, audioEngPath("sh_cream_herewego.ogg"));
        BaseMod.addAudio(SoundLibrary.Knuckles, audioEngPath("sh_knux_gotit.ogg"));
        BaseMod.addAudio(SoundLibrary.Tails, audioEngPath("sh_tails_leaveittome.ogg"));
        BaseMod.addAudio(SoundLibrary.CuteCouple, audioEngPath("sa1_0509_Cute_Couples.ogg"));
    }


    public static int attackCardsPlayedThisTurn = 0;
    public static int cardsExhaustedThisTurn = 0;

    @Override
    public void receivePostExhaust(AbstractCard abstractCard) {
        cardsExhaustedThisTurn++;
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        attackCardsPlayedThisTurn = 0;
        cardsExhaustedThisTurn = 0;
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard.type == AbstractCard.CardType.ATTACK) {
            attackCardsPlayedThisTurn++;
        }
    }


    //
//    @Override
//    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
//        if (!(AbstractDungeon.player instanceof Sonic)){
//            return;
//        }
//        if (abstractRoom.monsters.getMonsterNames().isEmpty()){
//            return;
//        }
//
//        String monsterName = abstractRoom.monsters.getMonsterNames().get(0);
//        ArrayList<String> bosses = new ArrayList<>(Arrays.asList(
//                "TheGuardian",
//                "CorruptHeart"
//        ));
//
//        if (bosses.contains(monsterName)) {
//            logger.info("PING");
//            CardCrawlGame.sound.play(SoundLibrary.SonicStyle);
////            MainMusic.newMusic(resourcesFolder + "/audio/music/MetalScratchin2.mp3");
////            abstractRoom.playBgmInstantly("MetalScratchin2.mp3");
//        }
//    }
////
//
//    @SpirePatch(clz = MainMusic.class, method = "getSong")
//    public static class Boss1Audio {
//        @SpirePostfixPatch
//        public static Music Postfix(Music __result, MainMusic __instance, String key) {
//            if (!(AbstractDungeon.player instanceof Sonic) &&
//                    "BOSS_BOTTOM".equals(key))
//                return MainMusic.newMusic(resourcesFolder + "/audio/music/MetalScratchin2.mp3");
//            return __result;
//        }
//    }
//
//    @Override
//    public void receivePostBattle(AbstractRoom abstractRoom) {
//        if (!(AbstractDungeon.player instanceof Sonic)) {
//            return;
//        }
//        String monsterName = abstractRoom.monsters.getMonsterNames().get(0);
//        ArrayList<String> bosses = new ArrayList<>(Arrays.asList(
//                "TheGuardian",
//                "CorruptHeart"
//        ));
//        logger.info("loggginggg" + monsterName);
//        if (bosses.contains(monsterName)) {
//            logger.info("PING");

    /// /            abstractRoom.playBGM("MetalScratchin2.mp3");
//
//            AbstractDungeon.actionManager.addToTop(
//                    SoundLibrary.PlayRandomVoice(new ArrayList<>(Arrays.asList(
//                            SoundLibrary.SonicsTheName,
//                            SoundLibrary.TooEasy,
//                            SoundLibrary.HeyWeShould
//                    ))));
//        }
//    }
    @Override
    public void receivePostDeath() {
        if (AbstractDungeon.player instanceof Sonic) {
            CardCrawlGame.sound.play(SoundLibrary.GetRandomVoice(new ArrayList<>(Arrays.asList(
                    SoundLibrary.Nooo,
                    SoundLibrary.Dead,
                    SoundLibrary.LongLiveTheEggmanEmpire
            ))));
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        RingPower.resetAmountHealed();
    }

//
////    /// Used for FlagDropDown
////    public static int getIndex()
////    {
////        if (modConfig == null) return 0;
////        return modConfig.getInt(ConfigField.INDEX.id);
////    }


}
