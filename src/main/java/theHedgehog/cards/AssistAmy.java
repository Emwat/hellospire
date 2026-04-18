package theHedgehog.cards;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Forethought;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AssistAmy extends BaseCard implements OnObtainCard {
    public static final String ID = makeID("AssistAmy");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int MAGIC = 2;
    private static final Color FLAVOR_BOX_COLOR = CardHelper.getColor(224, 156, 180);
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(181, 0, 0);

    public AssistAmy() {
        super(ID, info);
        setMagic(MAGIC);
        setExhaust(true);
        setCostUpgrade(0);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> output = new ArrayList<TooltipInfo>();
        output.add(new TooltipInfo(cardStrings.EXTENDED_DESCRIPTION[0], cardStrings.EXTENDED_DESCRIPTION[1]));
        return output;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.RandomVoiceAction(new ArrayList<>(Arrays.asList(
                SoundLibrary.Amy,
                SoundLibrary.CuteCouple
        ))));
        addToBot(new ModXFastAction(() -> {
            if (p.drawPile.isEmpty()) {
                return;
            }
            for (int i = 0; i < magicNumber; i++) {
                if (p.drawPile.size() > i) {
                    AbstractCard topCard = p.drawPile.getNCardFromTop(i);
                    if (topCard.cost > 0) {
                        topCard.freeToPlayOnce = true;
                    }
                }
                // topCard.modifyCostForCombat(-99);
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistAmy();
    }

    @Override
    public void onObtainCard() {
        removeAssistCard(this.upgraded);
    }
}
