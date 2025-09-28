package hellospire.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Adrenaline;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.actions.LoopDeLoopAction;
import hellospire.actions.LowerCostAction;
import hellospire.actions.SecretRouteAction;
import hellospire.character.Sonic;
import hellospire.relics.AirBoostShoesRelic;
import hellospire.util.CardStats;

public class SecretRoute extends BaseCard {
    public static final String ID = makeID("SecretRoute");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    // Adrenaline draw 2, gain 1(2) energy.
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int DRAW = 2;
    private static final String DRAW_KEYWORD = "CustomVar_DRAW";

    public SecretRoute() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        setCustomVar(DRAW_KEYWORD, DRAW);
        // setExhaustive2();
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(customVar(DRAW_KEYWORD)));
        addToBot(new SelectCardsInHandAction(magicNumber,
                CardCrawlGame.languagePack.getUIString(makeID("SecretRouteMessage")).TEXT[0],
                false, false, pickableCards, cards -> {
            if (cards.isEmpty()) {
                return;
            }

            for (AbstractCard card : cards) {
                addToBot(new LowerCostAction(card, 1));
            }
        }));
    }

    private void setExhaustive2() {
        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, 2);
        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, 2);
        exhaust = false;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SecretRoute();
    }
}
