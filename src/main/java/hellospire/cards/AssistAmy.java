package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.IncreaseCostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;

public class AssistAmy extends BaseCard {
    public static final String ID = makeID("AssistAmy");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public AssistAmy() {
        super(ID, info);
        setExhaust(true);
        tags.add(SonicTags.ANTI_DASH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayRandomVoice(new ArrayList<>(Arrays.asList(
                SoundLibrary.Amy,
                SoundLibrary.CuteCouple
        ))));
        addToBot(new SelectCardsInHandAction("to cost 0 for the rest of combat.", cards -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard pickedCard : cards) {
                addToBot(new IncreaseCostAction(pickedCard.uuid, -99));
            }
        }));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.setCostUpgrade(0);
        }

        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistAmy();
    }
}
