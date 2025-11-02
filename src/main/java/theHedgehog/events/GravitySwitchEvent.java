package theHedgehog.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.cardmodifiers.SpinUpModifier;

import java.util.ArrayList;
import java.util.List;

import static theHedgehog.SonicMod.makeID;

public class GravitySwitchEvent extends PhasedEvent {
    public static final String ID = makeID("GravitySwitchEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private final int numberOfModifiers = 3;
    private final int hpAmt;
    private final int healAmt;
    private final int maxHPAmt;

    private static final String IMG = SonicMod.imagePath("events/gravitySwitch.png");
    // [Embrace Madness] Receive 2 Madness. Lose HP equal to 12.5% (18%) of max HP.
    // [Press On] Become Cursed - Writhe. Heals 25% (20%) of max HP.
    // [Retrace Your Steps] Lose 5% of Max HP.

          // "[Leave]",
          //         "[Pull the switch] #b5 random cards will cost #b1 less and gain #ySpin #yUp. Lose #r",
          //         " #rmax #rHP.",
          //         "[Jump down] Become #rCursed - #rWrithe. #gHeal #g",
          //         " #gHP.",
          //         "[Use Speed Run Strat] Lose #r",
          //         " #rMax #rHP."

    public GravitySwitchEvent() {
        super(ID, NAME, IMG);

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.18F);
            this.healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        } else {
            this.hpAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.125F);
            this.healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.25F);
        }

        this.maxHPAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.05F);

        registerPhase("start", new TextPhase(DESCRIPTIONS[0])
                .addOption(new TextPhase.OptionInfo(OPTIONS[1] + numberOfModifiers + OPTIONS[2] + this.hpAmt + OPTIONS[3]).setOptionResult(this::Option1_PullSwitch))
                .addOption(new TextPhase.OptionInfo(OPTIONS[4] + this.hpAmt + OPTIONS[5]).setOptionResult(this::Option2_EmbraceMadness))
                .addOption(new TextPhase.OptionInfo(OPTIONS[6] + this.healAmt + OPTIONS[7]).setOptionResult(this::Option3_JumpDown))
                .addOption(new TextPhase.OptionInfo(OPTIONS[8] + this.maxHPAmt + OPTIONS[9]).setOptionResult(this::Option4_SpeedRun))
        );

        registerPhase("Option11_Leave", new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option21_Leave", new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option31_Leave", new TextPhase(DESCRIPTIONS[3])
                .addOption(OPTIONS[0], (i)->openMap()));

        registerPhase("Option41_Leave", new TextPhase(DESCRIPTIONS[4])
                .addOption(OPTIONS[0], (i)->openMap()));

        transitionKey("start");
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_WINDING");
        }
    }

    private void Option1_PullSwitch(Integer i) {
        CardGroup masterDeck = AbstractDungeon.player.masterDeck;

        int maxPossibleSize = Math.min(numberOfModifiers, masterDeck.size());
        int j = 0;
        int tries = 0;
        int maxTries = 99;
        ArrayList<String> loggedCards = new ArrayList<>();
        while (j < maxPossibleSize) {
            int randomNumber = AbstractDungeon.eventRng.random(0, masterDeck.size() - 1);
            AbstractCard randomCard = masterDeck.group.get(randomNumber);
            if (randomCard.cost <= 0) {
                tries++;
                continue;
            }
            if (tries >= maxTries) {
                break;
            }
            randomCard.modifyCostForCombat(-1);
            if (!randomCard.hasTag(SonicTags.SPIN_UP)) {
                CardModifierManager.addModifier(randomCard, new SpinUpModifier());
            }
            loggedCards.add(randomCard.name);
            float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(randomCard.makeStatEquivalentCopy(), x, y));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            j++;
        }
        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.hpAmt));
        logMetric(ID, "Embrace Spin Up",
                (List)null, (List)null, (List)null, loggedCards, (List)null, (List)null, (List)null, this.hpAmt, 0, 0, 0, 0, 0);

        transitionKey("Option11_Leave");
    }

    private void Option2_EmbraceMadness(Integer i) {
        List<String> cards = new ArrayList();
        cards.add("Madness");
        cards.add("Madness");
        logMetric(ID, "Embrace Madness",
                cards, (List)null, (List)null, (List)null, (List)null, (List)null, (List)null, this.hpAmt, 0, 0, 0, 0, 0);
        CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_1");
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Madness(), (float)Settings.WIDTH / 2.0F - 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Madness(), (float)Settings.WIDTH / 2.0F + 350.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.hpAmt));
        transitionKey("Option21_Leave");
    }

    private void Option3_JumpDown(Integer i) {
        AbstractDungeon.player.heal(this.healAmt);
        AbstractCard c = new Writhe();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F + 10.0F * Settings.xScale, (float)Settings.HEIGHT / 2.0F));
        logMetricObtainCardAndHeal(ID, "Writhe", c, this.healAmt);
        transitionKey("Option31_Leave");
    }

    private void Option4_SpeedRun(Integer i) {
        logMetricMaxHPLoss(ID, "Max HP", this.maxHPAmt);
        AbstractDungeon.player.decreaseMaxHealth(this.maxHPAmt);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true);
        transitionKey("Option41_Leave");
    }
}
