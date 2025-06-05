package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.actions.IncreaseCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class CuteCouple extends BaseCard {
    public static final String ID = makeID("CuteCouple");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 3;

    public CuteCouple() {
        super(ID, info);
        setExhaust(true);
        tags.add(SonicTags.ANTI_DASH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction("to cost 0 for the rest of combat.", cards -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard pickedCard : cards) {
                addToBot(new IncreaseCostAction(pickedCard.uuid, -pickedCard.costForTurn));
            }
        }));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
//            this.upgradeMagicNumber(UPG_MAGIC);
            this.setExhaust(false);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new CuteCouple();
    }
}
