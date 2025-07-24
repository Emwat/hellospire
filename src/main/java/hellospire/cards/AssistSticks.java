package hellospire.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.cardmodifiers.SpinUpModifier;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.ArrayList;

public class AssistSticks extends BaseCard {
    public static final String ID = makeID("AssistSticks");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    public AssistSticks() {
        super(ID, info);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Sticks));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                modifyStrikesAndDefends(p.drawPile);
                modifyStrikesAndDefends(p.discardPile);
                modifyStrikesAndDefends(p.hand);
                modifyStrikesAndDefends(p.exhaustPile);
                this.isDone = true;
            }
        });
    }

    private void modifyStrikesAndDefends(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card.hasTag(CardTags.STARTER_STRIKE) || card.hasTag(CardTags.STARTER_DEFEND)) {
                BaseCard.setCostForCombat(card, 0);
                if (!card.hasTag(SonicTags.SPIN_UP)) {
                    CardModifierManager.addModifier(card, new SpinUpModifier());
                }
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    card.flash();
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AssistSticks();
    }
}
