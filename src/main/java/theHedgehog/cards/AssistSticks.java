package theHedgehog.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.cardmodifiers.GainEnergyModifier;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistSticks extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistSticks");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    public AssistSticks() {
        super(ID, info);
        setCostUpgrade(0);
        CardModifierManager.addModifier(this, new SpinUpModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Sticks));
        addToBot(new ModFastAction(() -> {
            modifyStrikesAndDefends(p.drawPile);
            modifyStrikesAndDefends(p.discardPile);
            modifyStrikesAndDefends(p.hand);
            modifyStrikesAndDefends(p.exhaustPile);
        }));
    }

    private void modifyStrikesAndDefends(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card.hasTag(CardTags.STARTER_STRIKE) || card.hasTag(CardTags.STARTER_DEFEND)) {
                // BaseCard.setCostForCombat(card, 0);
                // card.isCostModifiedForTurn = true;
                if (!card.hasTag(SonicTags.SPIN_UP)) {
                    CardModifierManager.addModifier(card, new SpinUpModifier());
                }
                // for(AbstractCardModifier modifier : CardModifierManager.modifiers(card)) {
                //
                // }
                CardModifierManager.addModifier(card, new GainEnergyModifier(1));
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    card.flash();
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistSticks();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.hasTag(SonicTags.UPG_ASSIST));
    }
}
