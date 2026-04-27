package theHedgehog.cards;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ApotheosisAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.cardmodifiers.MagicHandsModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class AssistVector extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistVector");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final int BLOCK = 22;
    private static final int UPG_BLOCK = 5;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(113, 163, 90);
    private static final Color FLAVOR_TEXT_COLOR = Color.BLACK.cpy();

    public AssistVector() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.Vector));
        addToBot(new GainBlockAction(p, block));
        addToBot(new ModXFastAction(() -> {
            modifyAllCardsInGroup(p.hand);
            modifyAllCardsInGroup(p.drawPile);
            modifyAllCardsInGroup(p.discardPile);
            modifyAllCardsInGroup(p.exhaustPile); // Apotheosis includes exhausted cards
        }));
    }

    private void modifyAllCardsInGroup(CardGroup cardGroup) {
        for (AbstractCard c : cardGroup.group) {
            int placeholder = c.isCostModifiedForTurn ? c.costForTurn : 3;

            if (c.cost < 2 || placeholder < 2) {
                continue;
            }
            if (c.type != CardType.ATTACK) {
                continue;
            }
            if (c.hasTag(SonicTags.DO_NOT_THROW)) {
                continue;
            }
            if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                c.superFlash();
            }

            CardModifierManager.addModifier(c, new MagicHandsModifier());
            c.applyPowers();
        }

    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistVector();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.hasTag(SonicTags.UPG_ASSIST));
    }
}
