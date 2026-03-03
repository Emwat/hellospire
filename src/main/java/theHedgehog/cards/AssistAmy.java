package theHedgehog.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.patches.FlavorText;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Forethought;
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

public class AssistAmy extends BaseCard {
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
    private static final Color FLAVOR_TEXT_COLOR = CardHelper.getColor(181, 0, 0    );

    public AssistAmy() {
        super(ID, info);
        setMagic(MAGIC);
        setExhaust(true);
        setCostUpgrade(0);
        FlavorText.AbstractCardFlavorFields.boxColor.set(this, FLAVOR_BOX_COLOR);
        FlavorText.AbstractCardFlavorFields.textColor.set(this, FLAVOR_TEXT_COLOR);
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
                AbstractCard topCard = p.drawPile.getNCardFromTop(i);
                topCard.freeToPlayOnce = true;
                // topCard.modifyCostForCombat(-99);
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new AssistAmy();
    }
}
