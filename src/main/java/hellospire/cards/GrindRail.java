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
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new DrawCardAction(magicNumber));
        ArrayList<AbstractCard> neighbors = getNeighbors(p.hand, false);
        for (AbstractCard neighbor : neighbors) {
            addToBot(new LowerCostAction(neighbor, magicNumber));
        }
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
            ArrayList<AbstractCard> neighbors = getNeighbors(AbstractDungeon.player.hand, false);
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
            ArrayList<AbstractCard> neighbors = getNeighbors(AbstractDungeon.player.hand, false);
            if (!neighbors.isEmpty()) {
                for (AbstractCard q : neighbors) {
                    q.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
                    q.triggerOnGlowCheck();
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new GrindRail();
    }
}
