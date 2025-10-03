package hellospire.events;

import com.badlogic.gdx.graphics.Color;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Beggar;
import com.megacrit.cardcrawl.events.shrines.UpgradeShrine;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import hellospire.SonicMod;
import hellospire.SonicTags;
import hellospire.cardmodifiers.MagicHandsModifier;
import hellospire.cards.Assist;

import java.util.ArrayList;
import java.util.Arrays;

import static hellospire.SonicMod.makeID;

public class RougeEvent extends PhasedEvent {
    public static final String ID = makeID("RougeEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private final AbstractCard theAssist = new Assist();
    private AbstractCard wantedCard;
    private int amountOfAssists;
    private int maxGoldStolen;
    private int healAmt;
    private int goldStolen;
    private int decreaseMaxHPAmt;

    private static final String IMG = SonicMod.imagePath("events/SonicXRouge.png");

    // [#c29eb5] does not seem to be working in EventStrings.json
    public RougeEvent() {
        super(ID, NAME, IMG);
        initializeEventVariables();
        initializeWantedCardAndAmountOfAssists();

        registerPhase("start0", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s#g%s #g%s.",
                            OPTIONS[1], ColorWord("#r", wantedCard.name), OPTIONS[2], amountOfAssists, theAssist.name))
                        .setOptionResult(this::Option00_TradeForAssist))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s %s%s%s",
                            OPTIONS[3], goldStolen, OPTIONS[4], wantedCard.name, OPTIONS[5]))
                        .setOptionResult(this::Option01_TheftVictim))
        );

        registerPhase("start1", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OPTIONS[6], ColorWord("#b", wantedCard.name), OPTIONS[5]))
                        .setOptionResult(this::Option10_BottleUpgrade))
        );

        registerPhase("start2", new TextPhase(DESCRIPTIONS[1])
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s%s%s", OPTIONS[7], healAmt, OPTIONS[8], decreaseMaxHPAmt, OPTIONS[9]))
                        .setOptionResult(this::Option20_AcceptHeal))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s", OPTIONS[10]))
                        .setOptionResult(this::Option21_DeclineHeal))
        );

        registerPhase("Option00_Leave", new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option01_Leave", new TextPhase(DESCRIPTIONS[3] + ColorWord("#b", wantedCard.name) + DESCRIPTIONS[4])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option10_Leave", new TextPhase(DESCRIPTIONS[5])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option20_Leave", new TextPhase(DESCRIPTIONS[6])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option21_Leave", new TextPhase(DESCRIPTIONS[7])
                .addOption(OPTIONS[0], (i)->openMap()));

        if (wantedCard != null) {
            if (!isCardBottled()) {
                transitionKey("start0");
            } else {
                transitionKey("start1");
            }
        } else {
            transitionKey("start2");
        }
    }

    private void initializeEventVariables(){
        if (AbstractDungeon.ascensionLevel >= 15) {
            maxGoldStolen = 40;
            goldStolen = Math.min(AbstractDungeon.player.gold, maxGoldStolen);
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.20F);
            decreaseMaxHPAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.03F);
        } else {
            maxGoldStolen = 60;
            goldStolen = Math.min(AbstractDungeon.player.gold, maxGoldStolen);
            healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
            decreaseMaxHPAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.02F);
        }
    }

    private void Option00_TradeForAssist(Integer i) {
        ArrayList<String> cardsObtained = new ArrayList<>();
        ArrayList<String> cardsRemoved = new ArrayList<>(Arrays.asList(wantedCard.name));

        AbstractDungeon.player.masterDeck.removeCard(wantedCard);

        for (int j = 0; j < amountOfAssists; j++) {
            AbstractCard c = theAssist.makeStatEquivalentCopy();
            cardsObtained.add(c.name);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }

        logMetric(ID, "Trade For Assist",
                cardsObtained, cardsRemoved, null, null, null, null, null,
                0, 0, 0, 0, 0, 0);

        transitionKey("Option00_Leave");
    }

    private void Option01_TheftVictim(Integer i) {
        ArrayList<String> cardsModified = new ArrayList<>(Arrays.asList(wantedCard.name));
        CardModifierManager.addModifier(wantedCard, new MagicHandsModifier());

        AbstractDungeon.player.loseGold(goldStolen);
        showUpgradeShineEffect();

        logMetric(ID, "Theft Victim",
                null, null, null, cardsModified, null, null, null,
                0, 0, 0, 0, goldStolen, 0);

        transitionKey("Option01_Leave");
    }

    private void Option10_BottleUpgrade(Integer i) {
        ArrayList<String> cardsUpgraded = new ArrayList<>(Arrays.asList(wantedCard.name));
        CardModifierManager.addModifier(wantedCard, new MagicHandsModifier());

        showUpgradeShineEffect();

        logMetric(ID, "Bottle Upgrade",
                null, null, null, cardsUpgraded, null, null, null,
                0, 0, 0, 0, 0, 0);

        transitionKey("Option10_Leave");
    }

    private void Option20_AcceptHeal(Integer i) {
        AbstractDungeon.player.heal(this.healAmt);
        AbstractDungeon.player.decreaseMaxHealth(this.decreaseMaxHPAmt);

        logMetric(ID, "Accept Heal",
                null, null, null, null, null, null, null,
                0, healAmt, decreaseMaxHPAmt, 0, 0, 0);

        transitionKey("Option20_Leave");
    }

    private void Option21_DeclineHeal(Integer i) {
        logMetric(ID, "Decline Heal",
                null, null, null, null, null, null, null,
                0, 0, 0, 0, 0, 0);

        transitionKey("Option21_Leave");
    }

    private ArrayList<AbstractCard> getCardsOfRarity(AbstractCard.CardRarity cardRarity) {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        ArrayList<AbstractCard> output = new ArrayList<>();

        for (int i = 0; i < masterDeck.size() - 1; i++) {
            AbstractCard card = masterDeck.get(i);
            if (card.rarity == cardRarity && !card.hasTag(SonicTags.DO_NOT_THROW)) {
                output.add(card);
            }
        }
        return output;

    }

    private void initializeWantedCardAndAmountOfAssists(){
        // com.megacrit.cardcrawl.events.city.
        ArrayList<AbstractCard> uncommonCards = getCardsOfRarity(AbstractCard.CardRarity.UNCOMMON);
        ArrayList<AbstractCard> rareCards = getCardsOfRarity(AbstractCard.CardRarity.RARE);

        if (!uncommonCards.isEmpty()) {
            wantedCard = uncommonCards.get(AbstractDungeon.miscRng.random(0, uncommonCards.size() - 1));
            amountOfAssists = 2;
        } else if (!rareCards.isEmpty()) {
            wantedCard = rareCards.get(AbstractDungeon.miscRng.random(0, rareCards.size() - 1));
            amountOfAssists = 3;
        }
    }

    private String ColorWord(String prepend, String str){
        String[] splitStr = str.split("\\s+");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < splitStr.length; i++) {
            if (i == 0) {
                output.append(prepend).append(splitStr[i]);
            } else {
                output.append(" ").append(prepend).append(splitStr[i]);
            }
        }
        return output.toString();
    }

    private boolean isCardBottled(){
        return wantedCard.inBottleFlame || wantedCard.inBottleLightning || wantedCard.inBottleTornado;
    }

    private void showUpgradeShineEffect(){
        float x = (float)Settings.WIDTH / 2.0F;
        float y = (float)Settings.HEIGHT / 2.0F;
        AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(wantedCard.makeStatEquivalentCopy(), x, y));
        AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(x, y));
    }

    // 08/28/2025 07:49 PM
    // I can't get it to work.
    // public float[] _lightsOutGetXYRI() {
    //     return new float[] {
    //             450*Settings.scale, 183*Settings.scale, 500f, 1.5f,
    //             478*Settings.scale, 167*Settings.scale, 500f, 1.5f,
    //     };
    // }
    //
    // public Color[] _lightsOutGetColor() {
    //     return new Color[] {
    //             new Color(67f / 255f, 188f / 255f, 188f / 255f, 1f),
    //             new Color(67f / 255f, 188f / 255f, 188f / 255f, 1f)
    //     };
    // }
}
