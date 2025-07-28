package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.MyModConfig;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.rewards.AssistReward;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Objects;

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

        characterCards.add(new AssistAmy());
        characterCards.add(new AssistBarry());
        characterCards.add(new AssistBig());
        characterCards.add(new AssistBlaze());
        characterCards.add(new AssistCharmy());
        characterCards.add(new AssistChip());
        characterCards.add(new AssistCream());
        characterCards.add(new AssistEspio());
        characterCards.add(new AssistKnuckles());
        characterCards.add(new AssistRosy());
        characterCards.add(new AssistRouge());
        characterCards.add(new AssistShadow());
        characterCards.add(new AssistSilver());
        characterCards.add(new AssistSticks());
        characterCards.add(new AssistTails());
        characterCards.add(new AssistTikal());
        characterCards.add(new AssistVector());
        // if (this.upgraded) {
        //     characterCards.add(new Gizoid());
        // }

        setExhaust(true);
    }


    @Override

    public void use(AbstractPlayer p, AbstractMonster m) {
        int randomNumber = AbstractDungeon.cardRandomRng.random(0, characterCards.size() - 1);
        AbstractCard randomCard = characterCards.get(randomNumber).makeStatEquivalentCopy();
        AbstractCard randomCard2 = characterCards.get(randomNumber).makeStatEquivalentCopy();

        if (this.upgraded) {
            randomCard.upgrade();
            randomCard2.upgrade();
            randomCard.setCostForTurn(-99);
            randomCard.isCostModifiedForTurn = true;
        }

        addToBot(new MakeTempCardInHandAction(randomCard, true));
        if (!this.inBottleLightning) {
            AbstractDungeon.getCurrRoom().rewards.add(
                    new AssistReward(this, this.uuid, randomCard2, this.upgraded));
        }
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Assist();
    }
}
