package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Havoc;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.LowerCostAction;
import hellospire.actions.SwapCostsAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class GrindRail extends BaseCard {
    public static final String ID = makeID("GrindRail");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public GrindRail() {
        super(ID, info);
//        setMagic(MAGIC, UPG_MAGIC);
        if (this.upgraded){
            tags.add(SonicTags.ANTI_DASH);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new DrawCardAction(magicNumber));
        if (!this.upgraded) {
            ArrayList<AbstractCard> neighbors = getNeighbors(p.hand);
            for (AbstractCard neighbor : neighbors) {
                addToBot(new LowerCostAction(neighbor));
            }
        } else {
            addToBot(new SelectCardsInHandAction(
                    2,
                    "Select 2 cards to lower costs.",
                    true,
                    true,
                    pickableCards,
                    cards -> {
                        if (cards.isEmpty()) {
                            return;
                        }
                        for (AbstractCard card : cards) {
                            addToBot(new LowerCostAction(card));
                        }
                    }));
        }
    }

    public ArrayList<AbstractCard> getNeighbors(CardGroup hand) {
        ArrayList<AbstractCard> neighbors = new ArrayList<>();

        if (hand.contains(this)) {
            int index = hand.group.indexOf(this);
            if (index > 0) {
                AbstractCard leftCard = hand.group.get(index - 1);
                if (leftCard.costForTurn > 0) {
                    neighbors.add(leftCard);
                }
            }
            if (index < hand.size() - 1) {
                AbstractCard rightCard = hand.group.get(index + 1);
                if (rightCard.costForTurn > 0) {
                    neighbors.add(rightCard);
                }
            }
        }
        return neighbors;
    }

    @Override
    public void hover() {
        super.hover();
        if (isPlayerHandNull()) {
            return;
        }
        if (this.upgraded) {
            return;
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            ArrayList<AbstractCard> neighbors = getNeighbors(AbstractDungeon.player.hand);
            if (!neighbors.isEmpty()) {
                for (AbstractCard q : neighbors) {
                    q.glowColor = Color.GOLD.cpy();
                    q.beginGlowing();
                }
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        if (isPlayerHandNull()) {
            return;
        }
        if (this.upgraded) {
            return;
        }
        if (AbstractDungeon.isPlayerInDungeon()) {
            ArrayList<AbstractCard> neighbors = getNeighbors(AbstractDungeon.player.hand);
            if (!neighbors.isEmpty()) {
                for (AbstractCard q : neighbors) {
                    q.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
                    q.triggerOnGlowCheck();
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GrindRail();
    }
}
