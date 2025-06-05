package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Distraction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class Assist extends BaseCard {
    public static final String ID = makeID("Assist");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    ArrayList<AbstractCard> characterCards = new ArrayList<>();

    public Assist() {
        super(ID, info);

        characterCards.add(new UnbreakableBond());
        characterCards.add(new CuteCouple());
        characterCards.add(new FightingBuddies());
        characterCards.add(new PoliteGirl());
        characterCards.add(new Barry());
        if (this.upgraded) {
            characterCards.add(new Gizoid());
        }

        setExhaust(true);
    }


    @Override

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard randomCard = characterCards.get(AbstractDungeon.cardRandomRng.random(0, characterCards.size() - 1));
        if (this.upgraded) {
            randomCard.setCostForTurn(-99);
            randomCard.isCostModifiedForTurn = true;
        }
        addToBot(new MakeTempCardInHandAction(randomCard, true));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Assist();
    }
}
