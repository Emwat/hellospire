package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
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
        setMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.Amy,
                SoundLibrary.CuteCouple
        ))));
        addToBot(new SelectCardsInHandAction(CardCrawlGame.languagePack.getUIString(makeID("AssistAmyMessage")).TEXT[0], cards -> {
            if (cards.isEmpty()) {
                return;
            }
            for (AbstractCard pickedCard : cards) {
                addToBot(new ModFastAction(() -> {
                    pickedCard.modifyCostForCombat(-99);
                }));
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
    public AbstractCard makeCopy() { // Optional
        return new AssistAmy();
    }
}
