package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.BagOfPreparation;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;

import static theHedgehog.SonicMod.makeID;

public class DevBandaidPower extends BasePower {
    public static final String POWER_ID = makeID("DevBandaidPower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public DevBandaidPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        DoTheThing();
    }

    private void DoTheThing(){
        if (AbstractDungeon.player != null && AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            addToBot(new ModXFastAction(() -> {
                SonicMod.logger.info("Activating DevBandaidPower!!");
                ModPreBattlePrep();
                // AbstractDungeon.player.preBattlePrep();
                // AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);
            }));
            if (AbstractDungeon.player.hasRelic(BagOfPreparation.ID)) {
                addToBot(new DrawCardAction(2));
            }
        }
        addToBot(new ReducePowerAction(owner, owner, ID, amount));
    }

    private void ModPreBattlePrep(){
        AbstractPlayer player = AbstractDungeon.player;
        // AbstractDungeon.actionManager.clear();
        player.damagedThisCombat = 0;
        // player.cardsPlayedThisTurn = 0;
        player.maxOrbs = 0;
        player.orbs.clear();
        player.increaseMaxOrbSlots(player.masterMaxOrbs, false);
        player.isBloodied = player.currentHealth <= player.maxHealth / 2;
        // player.poisonKillCount = 0;
        GameActionManager.playerHpLastTurn = player.currentHealth;
        player.endTurnQueued = false;
        player.gameHandSize = player.masterHandSize;
        player.isDraggingCard = false;
        player.isHoveringDropZone = false;
        player.hoveredCard = null;
        player.cardInUse = null;
        player.drawPile.initializeDeck(player.masterDeck);
        // AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        // player.hand.clear();
        player.discardPile.clear();
        player.exhaustPile.clear();
        if (AbstractDungeon.player.hasRelic("SlaversCollar")) {
            ((SlaversCollar)AbstractDungeon.player.getRelic("SlaversCollar")).beforeEnergyPrep();
        }

        player.energy.prep();
        player.energy.recharge();
        // player.powers.clear();
        // player.isEndingTurn = false;
        player.healthBarUpdatedEvent();
        if (ModHelper.isModEnabled("Lethality")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, 3), 3));
        }

        if (ModHelper.isModEnabled("Terminal")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, 5), 5));
        }

        AbstractDungeon.getCurrRoom().monsters.usePreBattleAction();
        if (Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
            AbstractDungeon.getCurrRoom().applyEmeraldEliteBuff();
        }

        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        player.applyPreCombatLogic();
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
