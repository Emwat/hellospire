package theHedgehog.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class LoopDeLoopAction extends AbstractGameAction {
    private boolean shuffleCheck;
    private static final Logger logger = LogManager.getLogger(DrawCardAction.class.getName());
    public static ArrayList<AbstractCard> drawnCards = new ArrayList();
    private boolean clearDrawHistory;
    private AbstractGameAction followUpAction;

    public LoopDeLoopAction(AbstractCreature source, int amount, boolean endTurnDraw) {
        this.shuffleCheck = false;
        this.clearDrawHistory = true;
        this.followUpAction = null;
        if (endTurnDraw) {
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        }

        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }

    }

    public LoopDeLoopAction(AbstractCreature source, int amount) {
        this(source, amount, false);
    }

    public LoopDeLoopAction(int amount, boolean clearDrawHistory) {
        this(amount);
        this.clearDrawHistory = clearDrawHistory;
    }

    public LoopDeLoopAction(int amount) {
        this((AbstractCreature) null, amount, false);
    }

    public LoopDeLoopAction(int amount, AbstractGameAction action) {
        this(amount, action, true);
    }

    public LoopDeLoopAction(int amount, AbstractGameAction action, boolean clearDrawHistory) {
        this(amount, clearDrawHistory);
        this.followUpAction = action;
    }

    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            drawnCards.clear();
        }

        if (AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            AbstractDungeon.player.getPower(NoDrawPower.POWER_ID).flash();
            this.endActionWithFollowUp();
        } else if (this.amount <= 0) {
            this.endActionWithFollowUp();
        } else {
            int deckSize = AbstractDungeon.player.drawPile.size();
            int discardSize = AbstractDungeon.player.discardPile.size();
            if (!SoulGroup.isActive()) {
                if (deckSize + discardSize == 0) {
                    this.endActionWithFollowUp();
                } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.player.createHandIsFullDialog();
                    this.endActionWithFollowUp();
                } else {
                    if (!this.shuffleCheck) {
                        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                            int handSizeAndDraw = BaseMod.MAX_HAND_SIZE - (this.amount + AbstractDungeon.player.hand.size());
                            this.amount += handSizeAndDraw;
                            AbstractDungeon.player.createHandIsFullDialog();
                        }

                        if (this.amount > discardSize) {
                            if (discardSize > 0) {
                                this.addToTop(new LoopDeLoopAction(discardSize, this.followUpAction, false));
                            }

                            this.amount = 0;
                            this.isDone = true;
                            return;
                        }

                        this.shuffleCheck = true;
                    }

                    this.duration -= Gdx.graphics.getDeltaTime();
                    if (this.amount != 0 && this.duration < 0.0F) {
                        if (Settings.FAST_MODE) {
                            this.duration = Settings.ACTION_DUR_XFAST;
                        } else {
                            this.duration = Settings.ACTION_DUR_FASTER;
                        }

                        --this.amount;
                        if (!AbstractDungeon.player.discardPile.isEmpty()) {
                            AbstractCard topCard = AbstractDungeon.player.discardPile.getTopCard();
                            if (topCard.costForTurn > 0) {
                                topCard.costForTurn = 0;
                                topCard.isCostModifiedForTurn = true;
                            }
                            drawnCards.add(topCard);
                            drawFromDiscard();
                            AbstractDungeon.player.hand.refreshHandLayout();
                        } else {
                            logger.warn("Player attempted to draw from an empty discardpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                            this.endActionWithFollowUp();
                        }

                        if (this.amount == 0) {
                            this.endActionWithFollowUp();
                        }
                    }

                }
            }
        }
    }

    private void endActionWithFollowUp() {
        this.isDone = true;
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }

    }

    private void drawFromDiscard(int numCards) {
        AbstractPlayer thisPlayer = AbstractDungeon.player;
        for (int i = 0; i < numCards; ++i) {
            if (!thisPlayer.discardPile.isEmpty()) {
                AbstractCard c = thisPlayer.discardPile.getTopCard();
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                c.setAngle(0.0F, true);
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                c.triggerWhenDrawn();
                thisPlayer.hand.addToHand(c);
                thisPlayer.discardPile.removeTopCard();

                for (AbstractPower p : thisPlayer.powers) {
                    p.onCardDraw(c);
                }

                for (AbstractRelic r : thisPlayer.relics) {
                    r.onCardDraw(c);
                }
            }
        }

    }

    public void drawFromDiscard() {
        AbstractPlayer thisPlayer = AbstractDungeon.player;

        if (thisPlayer.hand.size() == 10) {
            thisPlayer.createHandIsFullDialog();
        } else {
            CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
            drawFromDiscard(1);
            thisPlayer.onCardDrawOrDiscard();
        }
    }
}
