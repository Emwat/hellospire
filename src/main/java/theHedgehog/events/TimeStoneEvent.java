package theHedgehog.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHedgehog.SonicMod;
import theHedgehog.cards.HomingAttack;
import theHedgehog.cards.SpinDash;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.CDPastRelic;
import thePackmaster.ThePackmaster;

import java.util.*;

import static theHedgehog.SonicMod.makeID;
import static theHedgehog.powers.OneUpPower.reviveAmount;
import static theHedgehog.relics.CDPastRelic.reviveCost;
import static theHedgehog.util.GeneralUtils.ColorWord;

public class TimeStoneEvent extends PhasedEvent {
    public static final String ID = makeID("TimeStoneEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String DesIntro = DESCRIPTIONS[0];
    private static final String DesPeekPast = DESCRIPTIONS[1];
    private static final String DesPeekPresent = DESCRIPTIONS[2];
    private static final String DesPeekFuture = DESCRIPTIONS[3];
    private static final String DesCommitPast = DESCRIPTIONS[4];
    private static final String DesCommitPresent = DESCRIPTIONS[5];
    private static final String DesCommitFuture = DESCRIPTIONS[6];

    private static final String OptFinish = OPTIONS[0];
    private static final String OptTimeChoicePast = OPTIONS[1];
    private static final String OptTimeChoicePresent = OPTIONS[2];
    private static final String OptTimeChoiceFuture = OPTIONS[3];
    private static final String OptTimeChoiceReThink = OPTIONS[4];
    private static final String OptTimeChoiceCommitPast = OPTIONS[5];
    private static final String OptTimeChoiceCommitPresent = OPTIONS[6];
    private static final String OptTimeChoiceCommitFuture = OPTIONS[7];


    private static final String phase0 = "phase0_start";
    private static final String phase00 = "phase01_peek_at_past";
    private static final String phase01 = "phase02_peek_at_present";
    private static final String phase02 = "phase03_peek_at_future";
    private static final String phase000 = "phase01_commit_past";
    private static final String phase010 = "phase02_commit_present";
    private static final String phase020 = "phase03_commit_future";

    float x = (float) Settings.WIDTH / 2.0F * Settings.scale;
    float x1 = (float) Settings.WIDTH / 2.0F - 190.0F * Settings.scale;
    float x2 = (float) Settings.WIDTH / 2.0F + 190.0F * Settings.scale;
    float y = (float) Settings.HEIGHT / 2.0F;

    private static final String IMG = SonicMod.imagePath("events/TimeStone.png");

    private static int presentDamageForUpgrade;

    public TimeStoneEvent() {
        super(ID, NAME, IMG);
        initializeEventVariables();

        registerPhase(phase0, new TextPhase(DesIntro)
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoicePast)
                        .setOptionResult(this::Option00_PeekAtPast))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoicePresent)
                        .setOptionResult(this::Option01_PeekAtPresent))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceFuture)
                        .setOptionResult(this::Option02_PeekAtFuture))
        );

        registerPhase(phase00, new TextPhase(DesPeekPast
                .replace("{0}", CDPastRelic.toleranceToPain + "+")
                .replace("{1}", Integer.toString(reviveCost))
                .replace("{2}", Integer.toString(reviveCost))
                .replace("{3}", reviveAmount + "%")
                .replace("{4}", ColorWord("#r", new HomingAttack().name))
                .replace("{5}", ColorWord("#g", new SpinDash().name)))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceCommitPast)
                        .setOptionResult(this::Option000_Past_Commit))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceReThink)
                        .setOptionResult(this::Option0X2_Return_to_Start))
        );

        registerPhase(phase01, new TextPhase(DesPeekPresent
                .replace("{0}", Integer.toString(presentDamageForUpgrade))
                )
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceCommitPresent)
                        .setOptionResult(this::Option010_Present_Commit))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceReThink)
                        .setOptionResult(this::Option0X2_Return_to_Start))
        );

        registerPhase(phase02, new TextPhase(DesPeekFuture)
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceCommitFuture)
                        .setOptionResult(this::Option020_Future_Commit))
                .addOption(new TextPhase
                        .OptionInfo(OptTimeChoiceReThink)
                        .setOptionResult(this::Option0X2_Return_to_Start))
        );

        registerPhase(phase000, new TextPhase(DesCommitPast).addOption(OptFinish, (i) -> openMap()));
        registerPhase(phase010, new TextPhase(DesCommitPresent).addOption(OptFinish, (i) -> openMap()));
        registerPhase(phase020, new TextPhase(DesCommitFuture).addOption(OptFinish, (i) -> openMap()));

        transitionKey(phase0);
    }

    private void initializeEventVariables() {
        if (AbstractDungeon.ascensionLevel >= 15) {
            presentDamageForUpgrade = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.3F);
        } else {
            presentDamageForUpgrade = MathUtils.round((float) AbstractDungeon.player.maxHealth * 0.2F);
        }
    }

    private void Option00_PeekAtPast(Integer i) {
        transitionKey(phase00);
    }

    private void Option01_PeekAtPresent(Integer i) {
        transitionKey(phase01);
    }

    private void Option02_PeekAtFuture(Integer i) {
        transitionKey(phase02);
    }

    private void Option0X2_Return_to_Start(Integer i) {
        transitionKey(phase0);
    }

    private void Option000_Past_Commit(Integer i) {
        AbstractCard spinDash = new SpinDash();
        AbstractRelic r = new CDPastRelic();
        ArrayList<String> cardsObtained = new ArrayList<>();
        ArrayList<String> cardsRemoved = new ArrayList<>();
        ArrayList<String> relicsObtained = new ArrayList<>();

        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(x, y, r);
        relicsObtained.add(r.name);

        boolean isUpgraded = false;
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.cardID.equals(HomingAttack.ID)) {
                // AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, x1, y));
                AbstractDungeon.player.masterDeck.removeCard(card);
                isUpgraded = card.upgraded;
                cardsRemoved.add(card.name);
                break;
            }
        }
        if (isUpgraded) {
            spinDash.upgrade();
        }
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(spinDash.makeStatEquivalentCopy(), x, y));
        cardsObtained.add(spinDash.name);

        AbstractEvent.logMetric(
                NAME,
                "Past",
                cardsObtained,
                cardsRemoved,
                null,
                null,
                relicsObtained,
                null,
                null,
                0, 0, 0, 0, 0, 0);

        transitionKey(phase000);
    }

    private void Option010_Present_Commit(Integer i) {
        this.presentEventActions();
        transitionKey(phase010);
    }

    private void Option020_Future_Commit(Integer i) {
        AbstractCard homingAttack = new HomingAttack();
        if (Loader.isModLoaded("anniv5") && AbstractDungeon.player instanceof ThePackmaster) {
            homingAttack = new theHedgehog.cardsPackExclusive.HomingAttack();
        }

        AbstractRelic r = new CDFutureRelic();
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(x1, y, r);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(homingAttack.makeStatEquivalentCopy(), x2, y));
        AbstractEvent.logMetricObtainCardAndRelic(NAME, "Future", homingAttack, r);
        transitionKey(phase020);
    }

    private void presentEventActions() {
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, y));
        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                upgradableCards.add(c);
            }
        }

        List<String> cardsUpgraded = new ArrayList<>();
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!upgradableCards.isEmpty()) {
            if (upgradableCards.size() == 1) {
                upgradableCards.get(0).upgrade();
                cardsUpgraded.add((upgradableCards.get(0)).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect((upgradableCards.get(0)).makeStatEquivalentCopy()));
            } else {
                upgradableCards.get(0).upgrade();
                upgradableCards.get(1).upgrade();
                cardsUpgraded.add(upgradableCards.get(0).cardID);
                cardsUpgraded.add(upgradableCards.get(1).cardID);
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy(), x1, y));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy(), x2, y));
            }
        }

        AbstractEvent.logMetric(
                NAME,
                "Present",
                null,
                null,
                null,
                cardsUpgraded,
                null, null, null, presentDamageForUpgrade, 0, 0, 0, 0, 0);
    }
}
