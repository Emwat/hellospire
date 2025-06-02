package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class SpeedUp extends BaseCard {
    public static final String ID = makeID("SpeedUp");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

    public SpeedUp() {
        super(ID, info);
        this.cardsToPreview = new Trick();

        setMagic(MAGIC, UPG_MAGIC);
    }

    /// Gain !B! Block. NL Exhaust up to !M! cards in your hand.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard ring = new Ring();
        int thisCardsIndex = 0;
        for (int i = 0; i < p.hand.size(); i++) {
            if (p.hand.group.get(i) == this) {
                thisCardsIndex = i;
                break;
            }
        }

        for (int i = 0; i < p.hand.size(); i++) {
            AbstractCard card = p.hand.group.get(i);
            int j = 0;
            if (i > thisCardsIndex) {
                j = 1;
            }
            if (card.cardID == ring.cardID) {
                addToBot(new TransformCardInHandAction(i - j, cardsToPreview.makeStatEquivalentCopy()));
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SpeedUp();
    }
}
