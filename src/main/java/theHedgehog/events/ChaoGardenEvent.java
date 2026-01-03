package theHedgehog.events;

import basemod.DevConsole;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.QuestionCard;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.console.SonicConsoleDebugString;
import theHedgehog.potions.ChaosSodaPotion;
import theHedgehog.relics.*;
import theHedgehog.strings.SonicChaoGardenStrings;

import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static theHedgehog.SonicMod.makeID;

public class ChaoGardenEvent extends PhasedEvent {
    private class DrinkingBuddy {
        public String Name;
        public int NumberOfDrinks = 0;
        public String OptionColor;
        public AbstractCard.CardColor Color;
        public CardLibrary.LibraryType CardLibrary;
        public String Character;
        public String DiscussionText;
        public String DrinkText;
        public String DrinkSoundKey;

        private DrinkingBuddy(String buddy, AbstractCard.CardColor color, CardLibrary.LibraryType cardLibrary, String characterName) {
            this.Name = buddy;
            this.Color = color;
            this.CardLibrary = cardLibrary;
            this.Character = characterName;

            String buddyKey = SonicMod.chaoGardenEventHelperExternal.makeID(buddy);
            SonicMod.logger.info("buddy: " + buddy + " - buddyKey " + buddyKey);

            SonicChaoGardenStrings chaoGardens = SonicMod.chaoGardenEventHelperExternal.GetSonicChaoGardenString(buddyKey);
            if (chaoGardens == null) {
                chaoGardens = SonicMod.chaoGardenEventHelperExternal.GetSonicChaoGardenString(buddy);
            }
            if (chaoGardens == null) {
                chaoGardens = SonicMod.modLocalizedStrings.getChaoGardenString(SonicMod.makeID(buddy));
            }
            if (chaoGardens == null) {
                chaoGardens = SonicMod.modLocalizedStrings.getChaoGardenString(SonicMod.makeID("Default"));
            }

            this.DiscussionText = chaoGardens.TALK;

            this.OptionColor = chaoGardens.OPTIONCOLOR == null ? "" : chaoGardens.OPTIONCOLOR;
            if (chaoGardens.DRINKCOLOR == null) {
                this.DrinkText = chaoGardens.DRINK;
            } else {
                this.DrinkText = theHedgehog.util.GeneralUtils.ColorWord(chaoGardens.DRINKCOLOR, chaoGardens.DRINK);
            }
            this.DrinkSoundKey = chaoGardens.DRINKSOUND;
        }

        private String OptBuddy() {
            String[] friends = new String[] {
                    "Tails", "Knuckles", "Amy", "Rouge"
            };
            if (Arrays.stream(friends).anyMatch(s -> s.equals(this.Name))) {
                String capitalizedName = this.Name.substring(0, 1).toUpperCase() + this.Name.substring(1);

                return String.format("%s%s%s%s %s %s%s",
                        OptPartnerA,
                        capitalizedName,
                        OptPartnerB,
                        numberOfNewChoices,
                        theHedgehog.util.GeneralUtils.ColorWord(this.OptionColor, this.Character),
                        OptPartnerC,
                        OptPartnerD);
            }


            String formattedName = theHedgehog.util.GeneralUtils.ColorWord(
                    this.OptionColor,
                    this.Name.substring(0, 1).toUpperCase() + this.Name.substring(1));

            return String.format("%s%s%s%s %s%s",
                    OptPartnerA, // [Give Chaos Soda to
                    formattedName, // The Silent
                    OptPartnerB, //] Add
                    numberOfNewChoices, // 5
                    OptPartnerC, // choices
                    OptPartnerD); // to the discussion

        }
    }

    public static final String ID = makeID("ChaoGardenEvent");

    // region Strings Description
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DesWelcome = DESCRIPTIONS[0];
    private static final String DesLobby = DESCRIPTIONS[1];
    private static final String DesExit = DESCRIPTIONS[2];
    private static final String DesSitDown = DESCRIPTIONS[3];
    private static final String DesActionsA = DESCRIPTIONS[4];
    private static final String DesActionsSingle = DESCRIPTIONS[5];
    private static final String DesActionsPlural = DESCRIPTIONS[6];
    private static final String DesEnterBar = DESCRIPTIONS[7];
    private static final String DesAfterBuyingDrinkA = DESCRIPTIONS[8];
    private static final String DesAfterBuyingDrinkB = DESCRIPTIONS[9];
    private static final String DesAfterBuyingTray = DESCRIPTIONS[10];
    private static final String DesDiscussScreenMessage = DESCRIPTIONS[11];
    private static final String DesDiscuss = DESCRIPTIONS[12];
    private static final String DesAfterWatching = DESCRIPTIONS[13];
    private static final String DesChaoStart = DESCRIPTIONS[14];
    private static final String DesAfterGrowingChao = DESCRIPTIONS[15];
    private static final String DesKidnapA = DESCRIPTIONS[16];
    private static final String DesKidnapB = DESCRIPTIONS[17];
    private static final String DesAfterGettingChao = DESCRIPTIONS[18];
    // endregion

    // region Options
    private static final String OptLeave = OPTIONS[0];
    private static final String OptAfterIntro = OPTIONS[1];
    private static final String OptNoMoreActions = OPTIONS[2];
    private static final String OptSitDown = OPTIONS[3];
    private static final String OptLookAround = OPTIONS[4];
    private static final String OptGoToBar = OPTIONS[5];
    private static final String OptYouNeedGold = OPTIONS[6];
    private static final String OptBuyDrinksA = OPTIONS[7];
    private static final String OptBuyDrinksB = OPTIONS[8];
    private static final String OptBuyPackA = OPTIONS[9];
    private static final String OptBuyPackB = OPTIONS[10];
    private static final String OptBuyPackC = OPTIONS[11];
    private static final String OptYouNeedDrinkA = OPTIONS[12];
    private static final String OptYouNeedDrinkB = OPTIONS[13];
    private static final String OptPartnerA = OPTIONS[14];
    private static final String OptPartnerB = OPTIONS[15];
    private static final String OptPartnerC = OPTIONS[16];
    private static final String OptPartnerD = OPTIONS[17];
    private static final String OptWatchShowA = OPTIONS[18];
    private static final String OptWatchShowB = OPTIONS[19];
    private static final String OptWatchShowC = OPTIONS[20];
    private static final String OptDiscussA = OPTIONS[21];
    private static final String OptDiscussB = OPTIONS[22];
    private static final String OptDiscussC = OPTIONS[23];
    private static final String OptDiscussLocked = OPTIONS[24];
    private static final String OptHugStart = OPTIONS[25];
    private static final String OptChao1 = OPTIONS[26];
    private static final String OptChao2 = OPTIONS[27];
    private static final String OptChao3 = OPTIONS[28];
    private static final String OptYouNeedChao = OPTIONS[29];
    private static final String OptChao5A = OPTIONS[30];
    private static final String OptChao5B = OPTIONS[31];
    private static final String OptYouAlrHaveChao = OPTIONS[32];
    private static final String OptChao5C = OPTIONS[33];
    private static final String OptThankYouForChao = OPTIONS[34];
    // endregion

    private static final String IMG = SonicMod.imagePath("events/ChaoGardenReveal.png");
    private static final String IMGLobby = SonicMod.imagePath("events/ChaoGardenReveal.png");
    private static final String IMGStage = SonicMod.imagePath("events/ChaoGardenStage.png");
    private static final String IMGBar = SonicMod.imagePath("events/ChaoGardenBar.png");
    private static final String IMGTable = SonicMod.imagePath("events/ChaoGardenTable.png");
    private boolean hasDiscussed = false;
    private final int maxActions = 2;
    private int actions;
    private ArrayList<DrinkingBuddy> DrinkBuddies = new ArrayList<>();
    private static final String phase0_start = "0_welcome";
    private static final String phase0_mainArea = "0_mainArea";
    private static final String phase00_bar = "00_bar";
    private static final String phase01_table = "01_table";
    private static final String phase010_afterDiscussion = "010_afterDiscussion";
    private static final String phase02_afterHeal = "02_afterHeal";
    private static final String phase03_chao = "03_chao";
    private static final String phase030_theKidnapping = "030_kidnapping";
    private static final String phase0300_theKidnapping = "0300_kidnapping";

    private static final String phase99_leave = "leave99";
    private int healAmt;
    private int healPercentage;
    private int hpLoss;
    private int damageHealed = 0;
    private int damageTaken = 0;
    private int goldLoss = 0;

    private final int numberOfNewChoices = 5;
    private final int numberOfSonicChoices = 5;

    int ChaosSodaCost;
    int ChaosSodaTrayCost;

    private static final int actionCostForGivingDrink = 0;
    private static final int actionCostForHeal = 1;
    private static final int actionCostForDiscussion = 1;
    private static final int actionCostForKidnappingChao = 1;
    private static final int actionCostForEvolvingChao = 1;

    private AbstractRelic givenRelic = null;
    private AbstractRelic growChao = null;
    private ArrayList<String> givenRelics = new ArrayList<>();
    private ArrayList<String> cardsObtained = new ArrayList<>();
    private ArrayList<String> potionsObtained = new ArrayList<>();
    private String FollowUp = "";
    private AbstractCard DiscussReward = null;

    // [#c29eb5] does not seem to be working in EventStrings.json
    public ChaoGardenEvent() {
        super(ID, NAME, IMG);
        initializeEventVariables();
        CardCrawlGame.music.playTempBgmInstantly("CHAO_GARDEN", true);

        // region phase start
        registerPhase(phase0_start, new TextPhase(DesWelcome)
                .addOption(new TextPhase
                        .OptionInfo(OptAfterIntro)
                        .setOptionResult((i) -> transitionKey(phase0_mainArea))));

        registerPhase(phase0_mainArea, GenerateTextPhaseWithActionAndImage(DesLobby, IMGLobby)
                .addOption(new TextPhase
                        .OptionInfo(OptGoToBar)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option00_GoToBar))
                .addOption(new TextPhase
                        .OptionInfo(OptSitDown)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option01_SitDown))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s%s", OptWatchShowA, healAmt, OptWatchShowB, healPercentage, OptWatchShowC))
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option02_WatchTheShow))
                .addOption(new TextPhase
                        .OptionInfo(OptHugStart)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option03_ChaoStart))
                .addOption(new TextPhase
                        .OptionInfo(OptLeave)
                        .setOptionResult(this::Option99_Leave))
        );
        // endregion

        // region phase bar
        registerPhase(phase00_bar, GenerateTextPhaseWithActionAndImage(DesEnterBar, IMGBar)
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OptBuyDrinksA, ChaosSodaCost, OptBuyDrinksB))
                        .enabledCondition(() -> AbstractDungeon.player.gold > ChaosSodaCost, OptYouNeedGold)
                        .setOptionResult(this::Option00_BuyDrinks))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s%s", OptBuyPackA, ChaosSodaTrayCost, OptBuyPackB, numberOfNewChoices, OptBuyPackC))
                        .enabledCondition(() -> AbstractDungeon.player.gold > ChaosSodaTrayCost, OptYouNeedGold)
                        .setOptionResult(this::Option00_BuyPack))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option090_LookAround))
        );
        // endregion

        // region phase table
        registerPhase(phase01_table, GenerateTextPhaseWithActionAndImage(DesSitDown, IMGTable)
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(0).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptNeedDrink(0) )
                        .setOptionResult((i) -> Option010_GiveDrinkToBuddy(DrinkBuddies.get(0).Name, i)))
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(1).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptNeedDrink(1))
                        .setOptionResult((i) -> Option010_GiveDrinkToBuddy(DrinkBuddies.get(1).Name, i)))
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(2).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptNeedDrink(2))
                        .setOptionResult((i) -> Option010_GiveDrinkToBuddy(DrinkBuddies.get(2).Name, i))
                )
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s+%s", OptDiscussA, OptDiscussB, numberOfNewChoices, OptDiscussC))
                        .enabledCondition(() -> !hasDiscussed, OptDiscussLocked)
                        .cardSelectOption(phase010_afterDiscussion,
                                DiscussSupplier,
                                DesDiscussScreenMessage,
                                1,
                                false,
                                false,
                                false,
                                false,
                                DiscussBiconsumer))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option090_LookAround))
        );

        registerPhase(phase010_afterDiscussion, GenerateDiscussTextPhase()
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option090_LookAround))
        );

        registerPhase(phase02_afterHeal, GenerateTextPhaseWithActionAndImage("", IMGStage)
                .addOption(new TextPhase
                        .OptionInfo(OptGoToBar)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option00_GoToBar))
                .addOption(new TextPhase
                        .OptionInfo(OptSitDown)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option01_SitDown))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s%s", OptWatchShowA, healAmt, OptWatchShowB, healPercentage, OptWatchShowC))
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option02_WatchTheShow))
                .addOption(new TextPhase
                        .OptionInfo(OptHugStart)
                        .enabledCondition(() -> actions > 0, OptNoMoreActions)
                        .setOptionResult(this::Option03_ChaoStart))
                .addOption(new TextPhase
                        .OptionInfo(OptLeave)
                        .setOptionResult(this::Option99_Leave))
        );
        // endregion

        // region phase chao
        registerPhase(phase03_chao, GenerateTextPhaseWithActionAndImage(DesChaoStart, IMGLobby)
                .addOption(new TextPhase
                        .OptionInfo(OptChao1)
                        .enabledCondition(this::HasChildChao, OptYouNeedChao)
                        .setOptionResult((i) -> Option030_Chao("Lightning", i)))
                .addOption(new TextPhase
                        .OptionInfo(OptChao2)
                        .enabledCondition(this::HasChildChao, OptYouNeedChao)
                        .setOptionResult((i) -> Option030_Chao("Frost", i)))
                .addOption(new TextPhase
                        .OptionInfo(OptChao3)
                        .enabledCondition(this::HasChildChao, OptYouNeedChao)
                        .setOptionResult((i) -> Option030_Chao("Dark", i)))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OptChao5A, hpLoss, OptChao5B))
                        .enabledCondition(() -> !HasChildChao() && !HasGrownUpChao(), OptYouAlrHaveChao)
                        .setOptionResult(this::Option031_KidnapChaoPage1))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option090_LookAround))
        );

        registerPhase(phase030_theKidnapping, new TextPhase(DesKidnapA)
                .addOption(new TextPhase
                        .OptionInfo(OptChao5C)
                        .setOptionResult(this::Option0310_KidnapChaoPage2))
        );

        registerPhase(phase0300_theKidnapping, new TextPhase(DesKidnapB)
                .addOption(new TextPhase
                        .OptionInfo(OptThankYouForChao)
                        .setOptionResult(this::Option03100_Chao))
        );
        // endregion

        registerPhase(phase99_leave, new TextPhase(DesExit)
                .addOption(OPTIONS[0], (i) -> openMap()));

        transitionKey(phase0_start);
    }

    private String OptNeedDrink(int buddyIndex) {

        String capitalizedName = DrinkBuddies.get(buddyIndex).Name.substring(0, 1).toUpperCase() + DrinkBuddies.get(buddyIndex).Name.substring(1);

        return OptYouNeedDrinkA + capitalizedName + OptYouNeedDrinkB;
    }

    private void initializeEventVariables() {

        if (AbstractDungeon.player instanceof Sonic) {
            InitializeSonicsFriends();
        }
        InitializeEveryone();
        Collections.shuffle(DrinkBuddies);

        if (!SonicMod.modDebugString.isEmpty()){
            MoveSpecificDrinkBuddyToTop();
        }

        // Library
        // Choose 1 of 20 cards to add to your deck
        // Heal 33(20%) of your Max HP
        // Campfires
        // Heal 30% (example: 21 hp from someone w/ 71 Max HP)

        if (AbstractDungeon.ascensionLevel >= 15) {
            ChaosSodaTrayCost = 99;
            ChaosSodaCost = 39;
            healPercentage = 10;
            hpLoss = 7;
        } else {
            ChaosSodaTrayCost = 79;
            ChaosSodaCost = 29;
            healPercentage = 16;
            hpLoss = 5;
        }

        actions = maxActions;

        healAmt = (int) (AbstractDungeon.player.maxHealth * (healPercentage * 0.01F));
    }

    private void MoveSpecificDrinkBuddyToTop(){
        int targetIndex = -1;

        for (int i = 0; i < DrinkBuddies.size(); i++) {
            if (SonicMod.modDebugString.equalsIgnoreCase(DrinkBuddies.get(i).Name)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1) {
            Collections.swap(DrinkBuddies, 0, targetIndex);
        } else {
            DevConsole.log(SonicMod.modDebugString + " is not found.");
        }
    }

    private TextPhase GenerateTextPhaseWithActionAndImage(String textBody, String imgUrl) {
        return new TextPhase("you should not see this") {
            @Override
            public void transition(PhasedEvent event) {
                super.transition(event);
                imageEventText.loadImage(imgUrl);
                event.imageEventText.updateBodyText(FollowUp + LobbyTextHelper(textBody));
                FollowUp = "";
            }
        };
    }

    private String LobbyTextHelper(String textBody) {
        if (actions == 1) {
            return String.format("%s %s%s%s", textBody, DesActionsA, actions, DesActionsSingle);
        }
        return String.format("%s %s%s%s", textBody, DesActionsA, actions, DesActionsPlural);
    }

    // region options bar
    private void Option00_GoToBar(Integer i) {
        transitionKey(phase00_bar);
    }

    private void Option00_BuyDrinks(Integer i) {
        AbstractDungeon.player.loseGold(ChaosSodaCost);
        goldLoss += ChaosSodaCost;

        for (int j = 0; j < AbstractDungeon.player.potionSlots; j++) {
            boolean isEmptyPotionSlot = AbstractDungeon.player.potions.get(j) instanceof PotionSlot;
            if (isEmptyPotionSlot) {
                AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(PotionHelper.getPotion(ChaosSodaPotion.ID)));
                potionsObtained.add(ChaosSodaPotion.ID);
            }
        }
        FollowUp = DesAfterBuyingDrinkA + ChaosSodaCost + DesAfterBuyingDrinkB;
        transitionKey(phase0_mainArea);
    }

    private void Option00_BuyPack(Integer i) {
        AbstractDungeon.player.loseGold(ChaosSodaTrayCost);
        goldLoss += ChaosSodaTrayCost;
        int numberOfDrinkBuddyChoices = 3;

        for (int j = 0; j < numberOfDrinkBuddyChoices; j++) {
            DrinkingBuddy drinkingBuddy = DrinkBuddies.get(j);
            drinkingBuddy.NumberOfDrinks += 1;
        }

        FollowUp = DesAfterBuyingTray;
        transitionKey(phase0_mainArea);
    }
    // endregion

    private void Option01_SitDown(Integer i) {
        transitionKey(phase01_table);
    }

    private void Option02_WatchTheShow(Integer i) {
        actions -= actionCostForHeal;
        AbstractDungeon.player.heal(healAmt);
        damageHealed += healAmt;
        FollowUp = DesAfterWatching;
        transitionKey(phase02_afterHeal);
    }

    private void Option03_ChaoStart(Integer i) {
        transitionKey(phase03_chao);
    }

    // region options table
    private void Option010_GiveDrinkToBuddy(String buddy, Integer i) {
        actions -= actionCostForGivingDrink;
        DrinkingBuddy thisGuy = DrinkBuddies.stream().filter(d -> Objects.equals(d.Name, buddy)).findFirst().get();
        for (int j = 0; j < AbstractDungeon.player.potionSlots; j++) {
            if (AbstractDungeon.player.potions.get(j).ID.equals(ChaosSodaPotion.ID)) {
                AbstractDungeon.player.removePotion(AbstractDungeon.player.potions.get(j));
                break;
            }
        }
        thisGuy.NumberOfDrinks += 1;
        FollowUp = thisGuy.DrinkText + " NL NL ";

        if (!"".equals(thisGuy.DrinkSoundKey)){
            try {
                CardCrawlGame.sound.play(thisGuy.DrinkSoundKey);
            } catch (Exception ex) {
                SonicMod.logger.error("Option020_GiveDrinkToBuddy {} {} voice not found.", thisGuy.Name, thisGuy.DrinkSoundKey);
            }
        }

        transitionKey(phase01_table);
    }

    Supplier<CardGroup> DiscussSupplier = () -> {
        CardGroup discussCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int extraCardChoice = AbstractDungeon.player.hasRelic(QuestionCard.ID) ? 1 : 0;
        if (!DrinkBuddies.isEmpty()) {
            for (DrinkingBuddy drinkBuddy : DrinkBuddies) {
                int drinkBuddyCardChoices = drinkBuddy.NumberOfDrinks * (numberOfNewChoices + extraCardChoice);
                ArrayList<AbstractCard> list = CardLibrary.getCardList(drinkBuddy.CardLibrary);

                for (int j = 0; j < drinkBuddyCardChoices; j++) {
                    AbstractCard card = list.get(AbstractDungeon.miscRng.random(0, list.size() - 1));
                    boolean containsDupe = true;

                    while (containsDupe) {
                        containsDupe = false;

                        for (AbstractCard discussCard : discussCards.group) {
                            if (discussCard.cardID.equals(card.cardID)) {
                                containsDupe = true;
                                card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                                break;
                            }
                        }
                    }

                    discussCards.addToBottom(card);
                }
            }
        }

        for (int i = 0; i < numberOfSonicChoices; i++) {
            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
            discussCards.addToTop(card);
        }

        return discussCards;
    };

    BiConsumer<PhasedEvent, ArrayList<AbstractCard>> DiscussBiconsumer = (p, cards) -> {
        actions -= actionCostForDiscussion;
        hasDiscussed = true;
        if (!cards.isEmpty()) {
            for (AbstractCard card : cards) {
                DiscussReward = card.makeCopy();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(DiscussReward, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                cardsObtained.add(DiscussReward.name);

                StampRelic stampRelic = new StampRelic();
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F, stampRelic);
                givenRelics.add(stampRelic.name);
            }
        }
    };

    private TextPhase GenerateDiscussTextPhase() {
        return new TextPhase("you should not see this") {
            @Override
            public void transition(PhasedEvent event) {
                super.transition(event);
                event.imageEventText.updateBodyText(GenerateDiscussTextPhaseHelper());
            }
        };
    }

    private String GenerateDiscussTextPhaseHelper() {
        for (DrinkingBuddy drinkingBuddy : DrinkBuddies) {
            if (DiscussReward.color == drinkingBuddy.Color) {
                return DesDiscuss + " NL NL " + drinkingBuddy.DiscussionText;
            }
        }
        return "";
    }
    // endregion

    // region options chao
    private void Option030_Chao(String element, Integer i) {
        actions -= actionCostForEvolvingChao;
        if ("Lightning".equals(element)) {
            this.growChao = new ChaoIroncladRelic();
        } else if ("Frost".equals(element)) {
            this.growChao = new ChaoSilentRelic();
        } else if ("Plasma".equals(element)) {
            this.growChao = new ChaoDefectRelic();
        } else if ("Dark".equals(element)) {
            this.growChao = new ChaoWatcherRelic();
        }

        givenRelics.add(this.growChao.name);
        AbstractDungeon.player.loseRelic(ChaoRelic.ID);

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float) Settings.HEIGHT / 2.0F, this.growChao);
        FollowUp = DesAfterGrowingChao;
        transitionKey(phase0_mainArea);
    }

    private void Option031_KidnapChaoPage1(Integer i) {
        actions -= actionCostForKidnappingChao;
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
        AbstractDungeon.player.damage(new DamageInfo(null, this.hpLoss));
        damageTaken += this.hpLoss;
        transitionKey(phase030_theKidnapping);
    }

    private void Option0310_KidnapChaoPage2(Integer i) {

        transitionKey(phase0300_theKidnapping);
    }

    private void Option03100_Chao(Integer i) {
        this.givenRelic = new ChaoRelic();
        givenRelics.add(this.givenRelic.name);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float) Settings.HEIGHT / 2.0F, this.givenRelic);
        FollowUp = DesAfterGettingChao;
        transitionKey(phase0_mainArea);
    }
    // endregion

    private void Option090_LookAround(Integer i) {
        transitionKey(phase0_mainArea);
    }

    private void Option99_Leave(Integer i) {
        logMetric(ID, "Chao Garden Variety",
                cardsObtained, null, null, null, givenRelics, potionsObtained, null,
                damageTaken, damageHealed, 0, 0, 0, goldLoss);

        transitionKey(phase99_leave);
    }

    private int CountSodas() {
        int output = 0;
        for (int j = 0; j < AbstractDungeon.player.potionSlots; j++) {
            if (AbstractDungeon.player.potions.get(j).ID.equals(ChaosSodaPotion.ID)) {
                output += 1;
            }
        }
        return output;
    }

    private String ListBuddies() {
        List<DrinkingBuddy> buddies = DrinkBuddies.stream().filter(d -> d.NumberOfDrinks > 0).collect(Collectors.toList());

        switch (buddies.size()) {
            case 0:
                return "";
            case 1:
                return buddies.get(0).Name;
            case 2:
                return String.format("%s and %s", buddies.get(0).Name, buddies.get(1).Name);
            default:
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < buddies.size() - 1; i++) {
                    sb.append(buddies.get(i)).append(", ");
                }
                return sb.append("and ").append(buddies.get(buddies.size() - 1)).toString();
        }
    }

    private boolean HasChildChao() {
        return AbstractDungeon.player.hasRelic(ChaoRelic.ID);
    }

    private boolean HasGrownUpChao() {
        return AbstractDungeon.player.hasRelic(ChaoIroncladRelic.ID) ||
                AbstractDungeon.player.hasRelic(ChaoSilentRelic.ID) ||
                AbstractDungeon.player.hasRelic(ChaoDefectRelic.ID) ||
                AbstractDungeon.player.hasRelic(ChaoWatcherRelic.ID);
    }

    private void InitializeSonicsFriends() {
        if (AbstractDungeon.player instanceof Sonic) {
            DrinkBuddies.add(new DrinkingBuddy("Tails", AbstractCard.CardColor.BLUE, CardLibrary.LibraryType.BLUE, "Defect"));
            DrinkBuddies.add(new DrinkingBuddy("Knuckles", AbstractCard.CardColor.RED, CardLibrary.LibraryType.RED, "Ironclad"));
            DrinkBuddies.add(new DrinkingBuddy("Rouge", AbstractCard.CardColor.GREEN, CardLibrary.LibraryType.GREEN, "Silent"));
            DrinkBuddies.add(new DrinkingBuddy("Amy", AbstractCard.CardColor.PURPLE, CardLibrary.LibraryType.PURPLE, "Watcher"));
        }
    }

    private void InitializeEveryone() {
        int counter = 0;
        int limit = 70;

        for (AbstractPlayer character : CardCrawlGame.characterManager.getAllCharacters()) {
            try {
                if (counter >= limit) {
                    break;
                }
                CardLibrary.LibraryType libraryType = null;
                String name = character.title;

                for (CardLibrary.LibraryType library : CardLibrary.LibraryType.values()) {
                    if (library.toString().equals(character.getCardColor().toString())) {
                        libraryType = library;
                        break;
                    }
                }

                if (libraryType == null) {
                    continue;
                }

                DrinkBuddies.add(new DrinkingBuddy(
                        name,
                        character.getCardColor(),
                        libraryType,
                        name));
                counter++;
            } catch (Exception ex) {
                SonicMod.logger.info("Could not make " + character.name + " into a drinking buddy.");
            }
        }
    }

}
