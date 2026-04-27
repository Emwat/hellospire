package theHedgehog.console;

import basemod.devcommands.ConsoleCommand;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.unique.SetupAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Blind;
import com.megacrit.cardcrawl.cards.colorless.MasterOfStrategy;
import com.megacrit.cardcrawl.cards.colorless.Trip;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.cards.purple.CarveReality;
import com.megacrit.cardcrawl.cards.red.Warcry;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cards.*;
import theHedgehog.cardsTails.IQ200Attack;
import theHedgehog.cardsTails.IQ300Attack;
import theHedgehog.cardsTails.IQ400Attack;
import theHedgehog.cardsTails.MagicHook;
import theHedgehog.relics.CDFutureRelic;
import theHedgehog.relics.PowerBrakeRelic;
import theHedgehog.relics.StampRelic;

import java.util.ArrayList;

// valid commands:
// sss

// https://github.com/daviscook477/BaseMod/wiki/Console#adding-your-own-commands
public class SonicConsoleDevCustom extends ConsoleCommand {
    public SonicConsoleDevCustom() {
        maxExtraTokens = 1; // How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; // How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = false; // if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        simpleCheck = true;
    }

    @Override
    protected void execute(String[] tokens, int depth) {

        if (tokens.length > 1) {
            String firstToken = tokens[1];

            if (firstToken.equals("exhausthand")) {
                atbExhaustEntireHand();
            }
        } else {
            atb(new LoseEnergyAction(9));
            atb(new GainEnergyAction(5));

            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (!mo.isDeadOrEscaped()) {
                    atb(new HealAction(mo, AbstractDungeon.player, 9999));
                }
            }

            DoesAssistAmyWork();
            // DoesPipingCarryRocketAccel();
            // SneckoEyeChaosEmerald();
            // ChaosEmeraldCards();
            // DebuffCards();
            // RandomCostCards();
            // TailsTest();
            // HomingAttackBranchTest();
            // LoopDeLoopTest();
            // LightSpeedAttackTest();
            // TopKickTest();
            // DizzyTest();
            // FireTest();
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();

        result.add("exhausthand");

        return result;
    }


    private void DoesAssistAmyWork() {
        atbExhaustEntireHand();
        atb(new MakeTempCardInHandAction(new AssistAmy(), 1));
        atb(new ModXFastAction(() -> {
            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                atb(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile, true));
            }
        }));
        atb(new MakeTempCardInHandAction(new Relax(), 1));
        atb(new MakeTempCardInHandAction(new Relax(), 1));
        atb(new MakeTempCardInHandAction(new Relax(), 1));
        atb(new MakeTempCardInHandAction(new Slide(), 1));
        AbstractCard rouge = new AssistRouge();
        rouge.upgrade();
        atb(new MakeTempCardInHandAction(rouge, 1));
        atb(new MakeTempCardInHandAction(new AssistBig(), 1));
        atb(new MakeTempCardInHandAction(new WallJump(), 1));
    }

    private void DoesPipingCarryRocketAccel() {
        atbExhaustEntireHand();
        atb(new MakeTempCardInHandAction(new HeavyBounceSlam(), 1));
        atb(new MakeTempCardInHandAction(new Piping(), 1));
        atb(new MakeTempCardInHandAction(new RocketAccel(), 1));
        atb(new MakeTempCardInHandAction(new Boost(), 1));
        atb(new MakeTempCardInHandAction(new Boost(), 1));
        atb(new MakeTempCardInHandAction(new AssistBarry(), 1));
        atb(new MakeTempCardInHandAction(new AssistBarry(), 1));
        atb(new MakeTempCardInHandAction(new HeavyBounceSlam(), 1));
    }

    private void SneckoEyeChaosEmerald() {
        atbExhaustEntireHand();
        AbstractPlayer p = AbstractDungeon.player;
        atb(new ApplyPowerAction(p, p, new ConfusionPower(p)));
        atb(new MakeTempCardInHandAction(new MasterOfStrategy(), 1));
        atb(new MakeTempCardInDrawPileAction(new ChaosEmeraldAttack(), 1, false, true));
        atb(new MakeTempCardInDrawPileAction(new ChaosEmeraldAttack(), 1, false, true));
        atb(new MakeTempCardInDrawPileAction(new ChaosEmeraldAttack(), 1, false, true));
    }

    private void DebuffCards(){
        ArrayList<AbstractCard> cards = new ArrayList<>();
        // cards.add(new Blind().makeStatEquivalentCopy());
        cards.add(new Trip().makeStatEquivalentCopy());
        for (AbstractCard c : cards) {
            atb(new MakeTempCardInHandAction(c, 1));
        }
    }

    private void ChaosEmeraldCards() {
        atbExhaustEntireHand();
        ArrayList<AbstractCard> cards = new ArrayList<>();
        // cards.add(new ChaosEmeraldAttack().makeStatEquivalentCopy());

        cards.add(new BackSpinKickRare());
        cards.add(new BoostRare());
        // cards.add(new CyanLaserRare());
        // cards.add(new DoubleAirKickRare());
        cards.add(new FalconPunchRare());
        // cards.add(new FireSomersaultRare());
        // cards.add(new FootSweepRare());
        // cards.add(new HorseKickRare());
        // cards.add(new HotRodRare());
        cards.add(new InstaShieldRare());
        // cards.add(new MeteorKickRare());
        // cards.add(new RicochetRare());
        cards.add(new SonicEagleRare());
        // cards.add(new SonicWaveRare());
        // cards.add(new SpinDashRare());
        // cards.add(new SpinningNeedleAttackRare());
        cards.add(new StrikeRare());
        // cards.add(new TopKickRare());
        // cards.add(new TripleKickRare());
        // cards.add(new UpDraftRare());
        // cards.add(new WindmillRare());
        // cards.add(new WindUpPunchRare());

        atb(new MakeTempCardInHandAction(new MasterOfStrategy(), 1));
        atb(new MakeTempCardInDrawPileAction(new ChaosEmeraldAttack(), 1, false, true));

        for (AbstractCard c : cards) {
            // atbMakeTempCardInDrawPileAction(c);
            atb(new MakeTempCardInHandAction(c, 1));
            AbstractCard uc = c.makeCopy();
            uc.upgrade();
            atb(new MakeTempCardInHandAction(uc, 1));
        }
    }

    private void RandomCostCards() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new Windmill().makeStatEquivalentCopy());
        cards.add(new SpinningNeedleAttack().makeStatEquivalentCopy());
        cards.add(new AssistJet().makeStatEquivalentCopy());
        atb(new MakeTempCardInHandAction(new MasterOfStrategy().makeStatEquivalentCopy(), 1));
        for (AbstractCard c : cards) {
            atb(new MakeTempCardInDrawPileAction(c, 1, false, true));
        }
    }

    private void TailsTest() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(new TopKick().makeStatEquivalentCopy());
        cards.add(new IQ200Attack().makeStatEquivalentCopy());
        cards.add(new IQ300Attack().makeStatEquivalentCopy());
        cards.add(new IQ400Attack().makeStatEquivalentCopy());
        cards.add(new MagicHook().makeStatEquivalentCopy());
        for (AbstractCard c : cards) {
            atb(new MakeTempCardInHandAction(c, 1));
        }
    }

    private void HomingAttackBranchTest() {
        AbstractCard homing = new HomingAttack().makeStatEquivalentCopy();
        ((BranchingUpgradesCard) homing).doBranchUpgrade();
        atb(new MakeTempCardInHandAction(homing, 1));
    }

    private void LoopDeLoopTest() {
        AbstractCard loop1 = new LoopDeLoop().makeStatEquivalentCopy();
        AbstractCard loop2 = new LoopDeLoop().makeStatEquivalentCopy();
        loop2.upgrade();
        atb(new MakeTempCardInHandAction(loop1, 1));
        atb(new MakeTempCardInHandAction(loop2, 1));
    }

    private void LightSpeedAttackTest() {
        AbstractCard lsa = new LightSpeedAttack().makeStatEquivalentCopy();
        AbstractCard ring = new Ring();
        AbstractPlayer p = AbstractDungeon.player;
        atb(new ApplyPowerAction(p, p, new WeakPower(p, 2, false)));
        atb(new MakeTempCardInDrawPileAction(ring.makeStatEquivalentCopy(), 4, false, true));
        atb(new MakeTempCardInHandAction(ring.makeStatEquivalentCopy(), 1));
        atb(new MakeTempCardInDiscardAction(ring.makeStatEquivalentCopy(), 4));
        atb(new MakeTempCardInHandAction(lsa, 1));
    }

    private void TopKickTest() {
        AbstractCard topKick = new TopKick().makeStatEquivalentCopy();
        AbstractCard topKick2 = new TopKick().makeStatEquivalentCopy();
        topKick2.upgrade();
        AbstractCard espio = new AssistEspio().makeStatEquivalentCopy();
        atb(new MakeTempCardInHandAction(topKick, 1));
        atb(new MakeTempCardInHandAction(topKick2, 1));
        atb(new MakeTempCardInHandAction(espio, 1));

        AbstractRelic r = new CDFutureRelic();
        if (!AbstractDungeon.player.hasRelic(CDFutureRelic.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                    Settings.WIDTH / 2.0F * Settings.scale,
                    Settings.HEIGHT / 2.0F * Settings.scale,
                    r);
        }
    }

    private void DizzyTest() {
        AbstractCard windmill = new Windmill().makeStatEquivalentCopy();
        AbstractCard needle = new SpinningNeedleAttack().makeStatEquivalentCopy();
        AbstractCard charmy = new AssistCharmy().makeStatEquivalentCopy();
        // AbstractCard barry = new AssistBarry().makeStatEquivalentCopy();
        AbstractCard sticks = new AssistSticks().makeStatEquivalentCopy();
        AbstractCard speedbreak = new SpeedBreak().makeStatEquivalentCopy();

        atb(new MakeTempCardInHandAction(windmill, 1));
        atb(new MakeTempCardInHandAction(needle, 1));
        atb(new MakeTempCardInHandAction(charmy, 1));
        atb(new MakeTempCardInHandAction(sticks, 1));
        atb(new MakeTempCardInHandAction(speedbreak, 1));

        AbstractRelic r = new PowerBrakeRelic();
        if (!AbstractDungeon.player.hasRelic(PowerBrakeRelic.ID)) {
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                    Settings.WIDTH / 2.0F * Settings.scale,
                    Settings.HEIGHT / 2.0F * Settings.scale,
                    r);
        }
    }

    private void FireTest() {
        atbExhaustEntireHand();
        AbstractCard crouch = new Crouch().makeStatEquivalentCopy();
        crouch.upgrade();

        AbstractCard somer = new FireSomersault().makeStatEquivalentCopy();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();
        somer.upgrade();

        atb(new MakeTempCardInHandAction(new Crouch().makeStatEquivalentCopy(), 2));
        atb(new MakeTempCardInHandAction(crouch, 1));

        atb(new MakeTempCardInHandAction(new FireTackle().makeStatEquivalentCopy(), 1));
        atb(new MakeTempCardInHandAction(new FireSomersault().makeStatEquivalentCopy(), 1));
        atb(new MakeTempCardInHandAction(somer, 1));
        atb(new MakeTempCardInHandAction(new VolcanoSlider().makeStatEquivalentCopy(), 1));
    }

    private void atbMakeTempCardInDrawPileAction(AbstractCard c){
        atb(new MakeTempCardInDrawPileAction(c, 1, false, true));
    }

    private void atbExhaustEntireHand(){
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            atb(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
        }
    }
    
    private void atb(AbstractGameAction action){
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
