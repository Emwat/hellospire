package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class DashPanel extends BaseCard {
    public static final String ID = makeID("DashPanel");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public DashPanel() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC, UPG_MAGIC);
    }

    /// TODO: Additional testing. Like what happens if there's two dash panels next to each other?
    /// TODO: Also add sound.
    /// Play the two cards to the right of this card.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : getCardsToTheRight()){
            this.addToBot(new NewQueueCardAction(card, m, false, true));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DashPanel();
    }

    @Override
    public void hover() {
        super.hover();
//        if (Wiz.isInCombat()) {
            for (AbstractCard q : getCardsToTheRight()) {
                q.glowColor = Color.GOLD.cpy();
                q.beginGlowing();
            }
//        }
    }

    @Override
    public void unhover() {
        super.unhover();
//        if (Wiz.isInCombat()) {
            for (AbstractCard q : getCardsToTheRight()) {
                q.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
                q.triggerOnGlowCheck();
            }
            AbstractDungeon.player.hand.applyPowers();
//        }
    }

    private ArrayList<AbstractCard> getCardsToTheRight() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardsToTheRight = new ArrayList<>();
        boolean startCounting = false;
        int numberOfCards = magicNumber;

        for (AbstractCard q : p.hand.group) {
            if(cardsToTheRight.size() >= numberOfCards){
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
