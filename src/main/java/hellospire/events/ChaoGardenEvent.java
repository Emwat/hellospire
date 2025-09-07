package hellospire.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.QuestionCard;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.potions.ChaosSodaPotion;
import hellospire.relics.*;

import javax.smartcardio.Card;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static hellospire.SonicMod.makeID;

public class ChaoGardenEvent extends PhasedEvent {

    private class DrinkingBuddy {
        public String Name;
        public int NumberOfDrinks = 0;
        public AbstractCard.CardColor Color;
        public String Character;

        private DrinkingBuddy(String buddy, AbstractCard.CardColor color, String characterName) {
            this.Name = buddy;
            this.Color = color;
            this.Character = characterName;
        }

        private String OptBuddy() {
            return String.format("%s%s%s%s #g%s%s",
                    OptPartnerA,
                    this.Name,
                    OptPartnerB,
                    numberOfNewChoices,
                    this.Character,
                    OptPartnerC);

        }
    }


    public static final String ID = makeID("ChaoGardenEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String DesWelcome = DESCRIPTIONS[0];
    private static final String DesSitDown = DESCRIPTIONS[1];
    private static final String DesActionsA = DESCRIPTIONS[2];
    private static final String DesActionsB = DESCRIPTIONS[3];
    private static final String DesSodasA = DESCRIPTIONS[4];
    private static final String DesSodasB = DESCRIPTIONS[5];
    private static final String DesDiscuss = DESCRIPTIONS[6];
    private static final String DesChaoStart = DESCRIPTIONS[7];
    private static final String DesKidnapA = DESCRIPTIONS[8];
    private static final String DesKidnapB = DESCRIPTIONS[9];

    private static final String OptLeave = OPTIONS[0];
    private static final String OptNoMoreActions = OPTIONS[1];
    private static final String OptSitDown = OPTIONS[2];
    private static final String OptLookAround = OPTIONS[3];
    private static final String OptGoToBar = OPTIONS[4];
    private static final String OptYouNeedGold = OPTIONS[5];
    private static final String OptBuyDrinksA = OPTIONS[6];
    private static final String OptBuyDrinksB = OPTIONS[7];
    private static final String OptBuyPackA = OPTIONS[8];
    private static final String OptBuyPackB = OPTIONS[9];
    private static final String OptBuyPackC = OPTIONS[10];
    private static final String OptYouNeedDrink = OPTIONS[11];
    private static final String OptPartnerA = OPTIONS[12];
    private static final String OptPartnerB = OPTIONS[13];
    private static final String OptPartnerC = OPTIONS[14];
    private static final String OptWatchShowA = OPTIONS[15];
    private static final String OptWatchShowB = OPTIONS[16];
    private static final String OptWatchShowC = OPTIONS[17];
    private static final String OptDiscussA = OPTIONS[18];
    private static final String OptDiscussB = OPTIONS[19];
    private static final String OptDiscussC = OPTIONS[20];
    private static final String OptDiscussLocked = OPTIONS[21];
    private static final String OptHugStart = OPTIONS[22];
    private static final String OptChao1 = OPTIONS[23];
    private static final String OptChao2 = OPTIONS[24];
    private static final String OptChao3 = OPTIONS[25];
    private static final String OptChao4 = OPTIONS[26];
    private static final String OptYouNeedChao = OPTIONS[27];
    private static final String OptChao5A = OPTIONS[28];
    private static final String OptChao5B = OPTIONS[29];
    private static final String OptYouAlrHaveChao = OPTIONS[30];
    private static final String OptChao5C = OPTIONS[31];

    private static final String IMG = SonicMod.imagePath("events/ChaoGardenReveal.png");
    private static final String IMGLobby = SonicMod.imagePath("events/ChaoGardenReveal.png");
    private static final String IMGStage = SonicMod.imagePath("events/ChaoGardenStage.png");
    private static final String IMGBar = SonicMod.imagePath("events/ChaoGardenBar.png");
    private static final String IMGTable = SonicMod.imagePath("events/ChaoGardenTable.png");
    private boolean hasDiscussed = false;
    private final int maxActions = 5;
    private int actions = maxActions;
    private final int numberOfNewChoices = 3;
    private final int trayWithNumberOfSodas = 3;
    private ArrayList<DrinkingBuddy> DrinkBuddies = new ArrayList<>();
    private static final String phase0_start = "start0";
    private static final String phase00_bar = "bar01";
    private static final String phase01_table = "table02";
    private static final String phase02_afterHeal = "afterHeal04";
    private static final String phase03_chao = "chao03";
    private static final String phase031_thekidnapping = "kidnapping041";

    private static final String phase99_leave = "leave99";
    private int healAmt;
    private int damageHealed = 0;
    private int healPercentage = 7;
    private int hpLoss;
    private int damageTaken = 0;
    private int goldLoss = 0;

    int numberOfChoices = 20;

    int ChaosSodaCost = 30;
    int ChaosSodaPackCost = 100;

    private AbstractRelic givenRelic = null;
    private AbstractRelic growChao = null;
    private ArrayList<String> givenRelics = new ArrayList<>();
    private ArrayList<String> cardsObtained = new ArrayList<>();
    private ArrayList<String> potionsObtained = new ArrayList<>();

    // [#c29eb5] does not seem to be working in EventStrings.json
    public ChaoGardenEvent() {
        super(ID, NAME, IMG);
        initializeEventVariables();
        CardCrawlGame.music.playTempBgmInstantly("CHAO_GARDEN", true);

        registerPhase(phase0_start, LobbyText(DesWelcome, IMGLobby)
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

        registerPhase(phase00_bar, LobbyText(DesSitDown, IMGBar)
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OptBuyDrinksA, ChaosSodaCost, OptBuyDrinksB))
                        .enabledCondition(() -> AbstractDungeon.player.gold > ChaosSodaCost, OptYouNeedGold)
                        .setOptionResult(this::Option00_BuyDrinks))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s%s", OptBuyPackA, ChaosSodaPackCost, OptBuyPackB, trayWithNumberOfSodas, OptBuyPackC))
                        .enabledCondition(() -> AbstractDungeon.player.gold > ChaosSodaPackCost, OptYouNeedGold)
                        .setOptionResult(this::Option00_BuyPack))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option019_LookAround))
        );

        registerPhase(phase01_table, LobbyText(DesSitDown, IMGTable)
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(0).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptYouNeedDrink)
                        .setOptionResult((i) -> Option020_GiveDrinkToBuddy(DrinkBuddies.get(0).Name, i)))
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(1).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptYouNeedDrink)
                        .setOptionResult((i) -> Option020_GiveDrinkToBuddy(DrinkBuddies.get(1).Name, i)))
                .addOption(new TextPhase
                        .OptionInfo(DrinkBuddies.get(2).OptBuddy())
                        .enabledCondition(() -> CountSodas() > 0, OptYouNeedDrink)
                        .setOptionResult((i) -> Option020_GiveDrinkToBuddy(DrinkBuddies.get(2).Name, i))
                )
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s", OptDiscussA, OptDiscussB, numberOfChoices, OptDiscussC))
                        .enabledCondition(() -> !hasDiscussed, OptDiscussLocked)
                        .setOptionResult(this::Option024_Discuss))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option019_LookAround))
        );

        registerPhase(phase02_afterHeal, LobbyText(DesWelcome, IMGStage)
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

        registerPhase(phase03_chao, LobbyText(DesChaoStart, IMGLobby)
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
                        .setOptionResult((i) -> Option030_Chao("Plasma", i)))
                .addOption(new TextPhase
                        .OptionInfo(OptChao4)
                        .enabledCondition(this::HasChildChao, OptYouNeedChao)
                        .setOptionResult((i) -> Option030_Chao("Darkness", i)))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OptChao5A, hpLoss, OptChao5B))
                        .enabledCondition(() -> !HasChildChao() && !HasGrownUpChao(), OptYouAlrHaveChao)
                        .setOptionResult(this::Option031_KidnapChao))
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option019_LookAround))
        );

        registerPhase(phase031_thekidnapping, new TextPhase(DesKidnapA)
                .addOption(new TextPhase
                        .OptionInfo(OptChao5C)
                        .setOptionResult(this::Option0310_Chao))
        );

        registerPhase(phase031_thekidnapping, new TextPhase(DesKidnapB)
                .addOption(new TextPhase
                        .OptionInfo(OptLookAround)
                        .setOptionResult(this::Option0310_Chao))
        );

        registerPhase(phase99_leave, new TextPhase("You take your rewards and go.")
                .addOption(OPTIONS[0], (i) -> openMap()));

        transitionKey(phase0_start);
    }

    private void initializeEventVariables() {
        if (AbstractDungeon.player instanceof Sonic){
            DrinkBuddies.add(new DrinkingBuddy("Tails", AbstractCard.CardColor.BLUE, "Defect"));
            DrinkBuddies.add(new DrinkingBuddy("Knuckles", AbstractCard.CardColor.RED, "Ironclad"));
            DrinkBuddies.add(new DrinkingBuddy("Rouge", AbstractCard.CardColor.GREEN, "Silent"));
            DrinkBuddies.add(new DrinkingBuddy("Amy", AbstractCard.CardColor.PURPLE, "Watcher"));
        } else {
            if (!(AbstractDungeon.player instanceof Ironclad)) {
                DrinkBuddies.add(new DrinkingBuddy("Ironclad", AbstractCard.CardColor.RED, "Ironclad"));
            }
            if (!(AbstractDungeon.player instanceof TheSilent)) {
                DrinkBuddies.add(new DrinkingBuddy("The Silent", AbstractCard.CardColor.GREEN, "Silent"));
            }
            if (!(AbstractDungeon.player instanceof Defect)) {
                DrinkBuddies.add(new DrinkingBuddy("Defect", AbstractCard.CardColor.BLUE, "Defect"));
            }
            if (!(AbstractDungeon.player instanceof Watcher)) {
                DrinkBuddies.add(new DrinkingBuddy("Watcher", AbstractCard.CardColor.PURPLE, "Watcher"));
            }
        }
        Collections.shuffle(DrinkBuddies);

        if (AbstractDungeon.ascensionLevel >= 15) {
            ChaosSodaCost = 40;
            healPercentage = 7;
            hpLoss = 7;
        } else {
            ChaosSodaCost = 30;
            healPercentage = 8;
            hpLoss = 5;
        }

        healAmt = (int) ((float) AbstractDungeon.player.maxHealth * (healPercentage * 0.01F));
    }

    private TextPhase LobbyText(String textBody, String imgUrl) {
        return new TextPhase(String.format("%s NL NL %s%s%s", textBody, DesActionsA, maxActions, DesActionsB)) {
            @Override
            public void transition(PhasedEvent event) {
                super.transition(event);
                imageEventText.loadImage(imgUrl);
            }
        };
    }

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
        transitionKey(phase0_start);
    }

    private void Option00_BuyPack(Integer i) {
        AbstractDungeon.player.loseGold(ChaosSodaPackCost);
        goldLoss += ChaosSodaPackCost;

        for (DrinkingBuddy drinkBuddy : DrinkBuddies) {
            drinkBuddy.NumberOfDrinks += 1;
        }

        transitionKey(phase0_start);
    }

    private void Option01_SitDown(Integer i) {
        transitionKey(phase01_table);
    }

    private void Option02_WatchTheShow(Integer i) {
        actions -= 1;
        AbstractDungeon.player.heal(healAmt);
        damageHealed += healAmt;
        transitionKey(phase02_afterHeal);
    }

    private void Option03_ChaoStart(Integer i) {
        transitionKey(phase03_chao);
    }

    private void Option020_GiveDrinkToBuddy(String buddy, Integer i) {
        actions -= 1;
        DrinkingBuddy thisGuy = DrinkBuddies.stream().filter(d -> Objects.equals(d.Name, buddy)).findFirst().get();
        for (int j = 0; j < AbstractDungeon.player.potionSlots; j++) {
            if (AbstractDungeon.player.potions.get(j).ID.equals(ChaosSodaPotion.ID)) {
                AbstractDungeon.player.removePotion(AbstractDungeon.player.potions.get(j));
                break;
            }
        }
        thisGuy.NumberOfDrinks += 1;
        transitionKey(phase01_table);
    }

    private void Option024_Discuss(Integer i) {
        actions -= 1;
        CardGroup discussCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int extraCardChoice = AbstractDungeon.player.hasRelic(QuestionCard.ID) ? 1 : 0;
        if (!DrinkBuddies.isEmpty()) {
            for (DrinkingBuddy drinkBuddy : DrinkBuddies) {
                int drinkBuddyCardChoices = drinkBuddy.NumberOfDrinks * (3 + extraCardChoice);
                AbstractCard.CardColor cardColor = drinkBuddy.Color;
                ArrayList<AbstractCard> list = null;

                if (cardColor == AbstractCard.CardColor.RED ||
                        cardColor == AbstractCard.CardColor.GREEN ||
                        cardColor == AbstractCard.CardColor.BLUE ||
                        cardColor == AbstractCard.CardColor.PURPLE) {
                    list = CardLibrary.getCardList(
                            cardColor == AbstractCard.CardColor.RED ? CardLibrary.LibraryType.RED :
                            cardColor == AbstractCard.CardColor.GREEN ? CardLibrary.LibraryType.GREEN :
                            cardColor == AbstractCard.CardColor.BLUE ? CardLibrary.LibraryType.BLUE :
                            CardLibrary.LibraryType.PURPLE
                    );
                } else {
                    // TODO: RANDOM CHARACTERS
                }

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

        while (discussCards.size() < 20) {
            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
            discussCards.addToTop(card);
        }

        hasDiscussed = true;
        AbstractDungeon.gridSelectScreen.open(discussCards, 1, "Add a card to your deck.", false);

        transitionKey(phase0_start);
    }

    private void Option019_LookAround(Integer i) {
        transitionKey(phase0_start);
    }

    private void Option030_Chao(String element, Integer i) {
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
        transitionKey(phase0_start);
    }

    private void Option031_KidnapChao(Integer i) {
        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature) null, this.hpLoss));
        damageTaken += this.hpLoss;
        transitionKey(phase031_thekidnapping);
    }

    private void Option0310_Chao(Integer i) {
        this.givenRelic = new ChaoRelic();
        givenRelics.add(this.givenRelic.name);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) Settings.WIDTH * 0.28F, (float) Settings.HEIGHT / 2.0F, this.givenRelic);

        transitionKey(phase0_start);
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


}
