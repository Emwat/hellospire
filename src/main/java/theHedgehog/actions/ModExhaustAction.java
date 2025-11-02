package theHedgehog.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ModExhaustAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    private String textForSelect;
    private static final String defaultTextForSelectForAnyCard = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT[0];

    public static int numExhausted;

    public ModExhaustAction(int amount, String textForSelect, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.textForSelect = textForSelect;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    public ModExhaustAction(AbstractCreature target, AbstractCreature source, int amount, String textForSelect, boolean isRandom, boolean anyNumber) {
        this(amount, textForSelect, isRandom, anyNumber);
        this.target = target;
        this.source = source;
    }

    public ModExhaustAction(AbstractCreature target, AbstractCreature source, int amount, String textForSelect, boolean isRandom) {
        this(amount, textForSelect, isRandom, false, false);
        this.target = target;
        this.source = source;
    }

    public ModExhaustAction(AbstractCreature target, AbstractCreature source, int amount, String textForSelect, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(amount, textForSelect, isRandom, anyNumber, canPickZero);
        this.target = target;
        this.source = source;
    }

    public ModExhaustAction(boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this(99, defaultTextForSelectForAnyCard, isRandom, anyNumber, canPickZero);

    }

    public ModExhaustAction(int amount, String textForSelect, boolean canPickZero) {
        this(amount, textForSelect, false, false, canPickZero);
    }

    public ModExhaustAction(int amount, String textForSelect, boolean isRandom, boolean anyNumber) {
        this(amount, textForSelect, isRandom, anyNumber, false);
    }

    public ModExhaustAction(int amount, String textForSelect, boolean isRandom, boolean anyNumber, boolean canPickZero, float duration) {
        this(amount, textForSelect, isRandom, anyNumber, canPickZero);
        this.duration = this.startDuration = duration;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                int tmp = this.p.hand.size();

                for(int i = 0; i < tmp; ++i) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToExhaustPile(c);
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                return;
            }

            if (!this.isRandom) {
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(this.textForSelect, this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for(int i = 0; i < this.amount; ++i) {
                this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToExhaustPile(c);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

}

