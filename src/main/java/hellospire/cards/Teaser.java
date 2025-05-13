package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.cards.blue.Coolheaded;
import com.megacrit.cardcrawl.cards.purple.Evaluate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class Teaser extends BaseCard {
    public static final String ID = makeID("FlagPole");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public Teaser() {
        super(ID, info);
        this.cardsToPreview = new ClawsUnleashed();

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard createdCard = this.cardsToPreview.makeStatEquivalentCopy();
        addToBot(new ChannelAction(new Dark()));
        addToBot(new MakeTempCardInDrawPileAction(createdCard, 1, true, true, false));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Teaser();
    }
}
