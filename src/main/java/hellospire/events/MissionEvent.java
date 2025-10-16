package hellospire.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.Transient;
import com.megacrit.cardcrawl.monsters.city.BronzeAutomaton;
import com.megacrit.cardcrawl.monsters.exordium.Lagavulin;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import hellospire.SonicMod;
import hellospire.actions.ModTextInCenterAction;
import hellospire.powers.DevBandaidPower;
import hellospire.powers.MissionRingRacePower;
import hellospire.powers.MissionScoreAttackPower;
import hellospire.powers.MissionTimeAttackPower;

import static hellospire.SonicMod.makeID;

public class MissionEvent extends PhasedEvent {
    public static final String ID = makeID("MissionEvent");

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final Color MissionTextColor = Color.YELLOW.cpy();
    public static final int RANK_S_REWARD = SonicMod.RANK_S_REWARD;
    public static final int RANK_A_REWARD = SonicMod.RANK_A_REWARD;
    public static final int RANK_B_REWARD = SonicMod.RANK_B_REWARD;
    public static final int RANK_C_REWARD = SonicMod.RANK_C_REWARD;

    private static final String IMG = SonicMod.imagePath("events/MissionDoors.png");

    public MissionEvent() {
        super(ID, NAME, IMG);

        registerPhase("start0", new TextPhase(String.format("%s%s%s%s%s%s%s%s%s",
                DESCRIPTIONS[0],
                RANK_S_REWARD, DESCRIPTIONS[1],
                RANK_A_REWARD, DESCRIPTIONS[2],
                RANK_B_REWARD, DESCRIPTIONS[3],
                RANK_C_REWARD, DESCRIPTIONS[4]
                ))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s%s%s", OPTIONS[1], MissionRingRacePower.MISSION_AMOUNT, OPTIONS[2]))
                        .setOptionResult(this::Option00_RingRace))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s", OPTIONS[3]))
                        .setOptionResult(this::Option01_ScoreAttack))
                .addOption(new TextPhase
                        .OptionInfo(String.format("%s", OPTIONS[4]))
                        .setOptionResult(this::Option02_TimeAttack))

        );

        registerPhase("00_RingRace", new CombatPhase("Automaton").addRewards(false, (room) -> {}));
        registerPhase("01_ScoreAttack", new CombatPhase(Transient.ID).addRewards(false, (room) -> {}));
        registerPhase("02_TimeAttack", new CombatPhase("TimeAttackLaga").addRewards(false, (room) -> {}));


        transitionKey("start0");
    }

    private void Option00_RingRace(Integer i) {
        logMetric(id, "Ring Race");
        transitionKey("00_RingRace");
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MissionRingRacePower(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DevBandaidPower(AbstractDungeon.player))
        );
        AbstractDungeon.actionManager.addToTop(new ModTextInCenterAction("EXHAUST " + MissionRingRacePower.MISSION_AMOUNT + " CARDS", MissionTextColor));
        AbstractDungeon.lastCombatMetricKey = "Mission Ring Race";

    }

    private void Option01_ScoreAttack(Integer i) {
        logMetric(id, "Score Attack");

        transitionKey("01_ScoreAttack");
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MissionScoreAttackPower(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DevBandaidPower(AbstractDungeon.player))
        );
        AbstractDungeon.actionManager.addToTop(new ModTextInCenterAction("DEAL MAXIMUM DAMAGE", MissionTextColor));

        AbstractDungeon.lastCombatMetricKey = "Mission Score Attack";
    }

    private void Option02_TimeAttack(Integer i) {
        logMetric(id, "Time Attack");

        // this.enterCombatFromImage();
        transitionKey("02_TimeAttack");
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MissionTimeAttackPower(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DevBandaidPower(AbstractDungeon.player))
        );
        AbstractDungeon.actionManager.addToTop(new ModTextInCenterAction("WIN AS FAST AS YOU CAN", MissionTextColor));
        AbstractDungeon.lastCombatMetricKey = "Mission Time Attack";
    }
}
