package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Amplify;
import com.megacrit.cardcrawl.cards.red.ThunderClap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BurstPower;
import com.megacrit.cardcrawl.powers.DoubleTapPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.DashPanelAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class DashPanel extends BaseCard {
    public static final String ID = makeID("DashPanel");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public DashPanel() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
    }

    /// Play the two cards to the right of this card.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(SoundLibrary.Booster));
        ArrayList<AbstractCard> cards = getCardsToTheRight(AbstractDungeon.player.hand.group);
        if (cards.isEmpty()) {
            return;
        }
        for (AbstractCard card : cards) {
            addToBot(new DashPanelAction(m, card, this.energyOnUse));
            if (card.type == CardType.POWER) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        p.hand.removeCard(card);
                        this.isDone = true;
                    }
                });
            } else if (card.exhaust) {
                addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand, true));
            } else if (card.returnToHand) {

            } else {
                addToBot(new DiscardSpecificCardAction(card));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DashPanel();
    }

    @Override
    public void hover() {
        super.hover();
        if (isPlayerHandNull()) {
            return;
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            ArrayList<AbstractCard> cards = getCardsToTheRight(AbstractDungeon.player.hand.group);
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard q : cards) {
                q.glowColor = Color.GOLD.cpy();
                q.beginGlowing();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        if (isPlayerHandNull()) {
            return;
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            ArrayList<AbstractCard> cards = getCardsToTheRight(AbstractDungeon.player.hand.group);
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard q : cards) {
                q.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
                q.triggerOnGlowCheck();
            }
        }
    }

    private ArrayList<AbstractCard> getCardsToTheRight(ArrayList<AbstractCard> hand) {
        ArrayList<AbstractCard> cardsToTheRight = new ArrayList<>();
        boolean startCounting = false;
        int numberOfCards = magicNumber;

        for (AbstractCard q : hand) {
            if (cardsToTheRight.size() >= numberOfCards) {
                break;
            }

            if (startCounting) {
                cardsToTheRight.add(q);
            }

            if (q == this) {
                startCounting = true;
            }
        }

        return cardsToTheRight;
    }

}
