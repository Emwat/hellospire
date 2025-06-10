package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.SonicTags;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.Objects;

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
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int ringAmount = 0;
        for (AbstractCard card : p.hand.group){
            if (Objects.equals(card.cardID, Ring.ID)){
                ringAmount++;
                addToBot(new ExhaustSpecificCardAction(card, p.hand, true));
            }
        }
        for (int i = 0; i < ringAmount; i++) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
    }

    private void TransformRingToTrick(AbstractPlayer p, AbstractMonster m){
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
            if (card.cardID == Ring.ID) {
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
