package theHedgehog.actions;


import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import theHedgehog.cards.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class DiscoveryAssistAction extends AbstractGameAction {
    private boolean retrieveCard = false;

    public DiscoveryAssistAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = 1;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // Only generate once. This used to run on every frame of the action, building
            // and discarding cards (and spending cardRandomRng) each time.
            ArrayList<AbstractCard> generatedCards = this.generateCardChoices();
            AbstractDungeon.cardRewardScreen.customCombatOpen(generatedCards, "idk1", true);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    AbstractCard disCard2 = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                        disCard2.upgrade();
                    }

                    disCard.setCostForTurn(0);
                    disCard2.setCostForTurn(0);
                    disCard.current_x = -1000.0F * Settings.xScale;
                    disCard2.current_x = -1000.0F * Settings.xScale + AbstractCard.IMG_HEIGHT_S;
                    if (this.amount == 1) {
                        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        }

                        AbstractCard var4 = null;
                    } else if (AbstractDungeon.player.hand.size() + this.amount <= BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE - 1) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard2, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        // Pick 3 distinct assists without replacement, constructing only the 3 that are chosen
        // (the old code built all 8 for every pick).
        ArrayList<Supplier<AbstractCard>> pool = new ArrayList<>(Arrays.asList(
                AssistAmy::new,
                AssistBarry::new,
                AssistBig::new,
                AssistBlaze::new,
                AssistCream::new,
                AssistKnuckles::new,
                AssistRosy::new,
                AssistTails::new
        ));

        ArrayList<AbstractCard> choices = new ArrayList<>();
        while (choices.size() < 3 && !pool.isEmpty()) {
            choices.add(pool.remove(cardRandomRng.random(pool.size() - 1)).get());
        }

        return choices;
    }
}
