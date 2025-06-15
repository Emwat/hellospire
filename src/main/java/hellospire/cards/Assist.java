package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.MyModConfig;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.rewards.AssistReward;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Objects;

public class Assist extends BaseCard {
    public static final String ID = makeID("AssistBlaze");
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
        characterCards.add(new AssistCream());
        characterCards.add(new AssistKnuckles());
        characterCards.add(new AssistTails());
        if (this.upgraded) {
            characterCards.add(new Gizoid());
        }

        setExhaust(true);
    }


    @Override

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard randomCard = characterCards.get(AbstractDungeon.cardRandomRng.random(0, characterCards.size() - 1)).makeCopy();
        if (this.upgraded) {
            randomCard.setCostForTurn(-99);
            randomCard.isCostModifiedForTurn = true;
        }


        addToBot(new MakeTempCardInHandAction(randomCard, true));
        AbstractDungeon.getCurrRoom().rewards.add(new AssistReward(this, this.uuid, randomCard));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Assist();
    }

    // The card already plays the voice.
    private void PlayAssistVoice(AbstractCard randomCard){
        if (MyModConfig.enableVoice && MyModConfig.voiceFrequency > 0) {
            if (Objects.equals(randomCard.cardID, AssistAmy.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Amy));
            } else if (Objects.equals(randomCard.cardID, AssistBarry.ID)) {
//                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Barry));
            } else if (Objects.equals(randomCard.cardID, AssistBig.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Big));
            } else if (Objects.equals(randomCard.cardID, AssistBlaze.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Blaze));
            } else if (Objects.equals(randomCard.cardID, AssistCream.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Cream));
            } else if (Objects.equals(randomCard.cardID, AssistKnuckles.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Knuckles));
            } else if (Objects.equals(randomCard.cardID, AssistTails.ID)) {
                addToBot(SoundLibrary.AlwaysPlayVoice(SoundLibrary.Tails));
            }
        }
    }
}
