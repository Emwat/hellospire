package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModXFastAction;

import static theHedgehog.SonicMod.makeID;

public class DizzyPower extends BasePower {
    public static final String POWER_ID = makeID("DizzyPower");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied;

    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public int highestCost = 0;
    public String highestCostCardName = "(no card)";

    public DizzyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = String.format("%s NL NL Card: Cost %s %s", DESCRIPTIONS[0], highestCost, highestCostCardName);
    }

    // The enemies are SUPPOSED to calculate Player's Post Draw cards, but it just never happens.

    // @Override
    // public void atStartOfTurn() {
    //     addToBot(new ModXFastAction(() -> { calculateHighestCost(false);}));
    // }

    // @Override
    // public void atStartOfTurnPostDraw() {
    //     super.atStartOfTurnPostDraw();
    //     // atbSetCostAndNameAndClearIfZero();
    //     addToTop(new ModXFastAction(() -> {
    //         SonicMod.logger.info("atStartOfTurnPostDraw owner" + owner.name);
    //         calculateHighestCost(false);
    //         // ForceMonsterApplyPowers();
    //     }));
    // }

    @Override
    public void atEndOfRound() {
        atbSetCostAndNameAndClearIfZero();
    }

    private void atbSetCostAndNameAndClearIfZero(){
        addToBot(new ModXFastAction(() -> {
            setCostAndName(0, "(no card)");
        }));
        if (justApplied) {
            justApplied = false;
        } else {
            if (amount == 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            } else {
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
            }
        }
    }

    // @Override
    // public void onCardDraw(AbstractCard card) {
    //     super.onCardDraw(card);
    //     addToBot(new ModXFastAction(() -> calculateHighestCost(card)));
    // }

    // @Override
    // public void onInitialApplication() {
    //     super.onInitialApplication();
    //     addToBot(new ModXFastAction(this::calculateHighestCost));
    // }

    //
    // @Override
    // public void onPlayCard(AbstractCard card, AbstractMonster m) {
    //     super.onPlayCard(card, m);
    // }

    // @Override
    // public void onAfterUseCard(AbstractCard usedCard) {
    //     super.onAfterCardPlayed(usedCard);
    //     addToBot(new ModXFastAction(() -> calculateHighestCost(usedCard)));
    // }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        addToBot(new ModXFastAction(() -> calculateHighestCost(card)));
    }

    // @Override
    // public float atDamageGive(float damage, DamageInfo.DamageType type) {
    //     if (type == DamageInfo.DamageType.NORMAL) {
    //         calculateHighestCost(false);
    //         if (damage - highestCost < 0) {
    //             return 0;
    //         }
    //         return damage - highestCost;
    //     } else {
    //         return damage;
    //     }
    // }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            calculateHighestCost(false);
            if (damage - highestCost < 0) {
                return 0;
            }
            return damage - highestCost;
        } else {
            return damage;
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public void calculateHighestCost(AbstractCard playedCard) {
        int oldCost = highestCost;
        if (playedCard.cost == -1) {
            int energy = EnergyPanel.totalCount;
            if (AbstractDungeon.player.hasRelic("Chemical X")) {
                energy += 2;
            }
            if (energy > highestCost) {
                highestCost = energy;
                highestCostCardName = playedCard.name;
            }
        }
        if (playedCard.costForTurn > highestCost) {
            setCostAndName(playedCard.costForTurn, playedCard.name);
        }

        calculateHighestCost(oldCost != highestCost);
    }

    public void calculateHighestCost(boolean hasChange) {
        int oldCost = highestCost;
        if (AbstractDungeon.player.hand.isEmpty()) {
            return;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.cost == -1) {
                int energy = EnergyPanel.totalCount;
                if (AbstractDungeon.player.hasRelic("Chemical X")) {
                    energy += 2;
                }
                if (energy > highestCost) {
                    setCostAndName(highestCost, card.name);
                }
            } else if (card.costForTurn > highestCost) {
                setCostAndName(card.costForTurn, card.name);
            }
        }

        if (oldCost != highestCost || hasChange) {
            updateDescription();
            this.flash();

            if (owner instanceof AbstractMonster) {
                ((AbstractMonster)owner).applyPowers();
            }
        }
    }

    private void ForceMonsterApplyPowers(){
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                mo.applyPowers();
            }
        }
    }

    private void setCostAndName(int newCost, String newName) {
        highestCost = newCost;
        highestCostCardName = newName;
    }

}