package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class Assist extends BaseCard {
    public static final String ID = makeID("Assist");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 2;
    ArrayList<AbstractCard> characterCards = new ArrayList<>();


    public Assist() {
        super(ID, info);

        characterCards.add(new UnbreakableBond());
        characterCards.add(new CuteCouple());
        characterCards.add(new FightingBuddies());

        setExhaust(true);
//        tags.add(CardTags.HEALING);
//        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            addToBot(new MakeTempCardInHandAction(
                    characterCards.get(AbstractDungeon.cardRandomRng.random(0, characterCards.size() - 1))));
        } else {
            addToBot(new SelectCardsAction(characterCards, "add to your hand.", cards -> {
                if (cards.isEmpty()) {
                    return;
                }
                for (AbstractCard card : cards) {
                    addToBot(new MakeTempCardInHandAction(card));
                }
            }));
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Assist();
    }
}
