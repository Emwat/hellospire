package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Evaluate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hellospire.SoundLibrary;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class Checkpoint extends BaseCard {
    public static final String ID = makeID("Checkpoint");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Checkpoint() {
        super(ID, info);
        this.cardsToPreview = new SuperSonicForm();
        setExhaust(true);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hand.group.size() < 1) {
            return false;
        }
        AbstractCard requiredCard = new Ring();
        for (AbstractCard c : p.hand.group) {
            if (c.cardID == requiredCard.cardID) {
                return true;
            }
        }
        return false;
    }

    /// "Apply !M! Vulnerable. NL Add a Ring to your hand."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(SoundLibrary.BlueTornado));
        this.addToBot(new MakeTempCardInDrawPileAction(
                this.cardsToPreview.makeStatEquivalentCopy(),
                1, true, true, false));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Checkpoint();
    }
}
