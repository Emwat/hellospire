package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class FlagPole extends BaseCard {
    public static final String ID = makeID("FlagPole");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public FlagPole() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        setMagic(MAGIC, UPG_MAGIC);
    }

    /// "Add a Ring to your hand. Channel a Frost for each Ring in your hand."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                p.updatePowers();
                for (AbstractCard card : p.hand.group) {
                    if (card.cardID == cardsToPreview.cardID) {
                        addToBot(new ChannelAction(new Frost()));
                    }
                }
                this.isDone = true;
            }
        });
    }

//    public void upgrade() {
//        if (!this.upgraded) {
//            this.upgradeName();
//            this.upgradeBaseCost(1);
//        }
//    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FlagPole();
    }
}
