package theHedgehog.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theHedgehog.actions.ModTransformWorkaroundAction;
import theHedgehog.cards.TeaserRareAttack1;
import theHedgehog.cards.TeaserRareAttack1Multi;
import theHedgehog.cards.TeaserRareAttack2;

import static theHedgehog.SonicMod.makeID;

public class TeaserRarePower extends BasePower {
    public static final String POWER_ID = makeID("TeaserRarePower");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public TeaserRarePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        int s = this.amount == 1 ? 1 : 2;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[s] + DESCRIPTIONS[3];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);

        AbstractCard output = null;

        if (card.type == AbstractCard.CardType.ATTACK && isCommonCard(card)) {
            if (card.cost <= 1) {
                if (card.target == AbstractCard.CardTarget.ENEMY) {
                    output = new TeaserRareAttack1();
                } else {
                    output = new TeaserRareAttack1Multi();
                }
            } else {
                output = new TeaserRareAttack2();
            }
            if (card.upgraded) {
                output.upgrade();
            }
        }

        if (output != null) {
            addToBot(new ModTransformWorkaroundAction(card, output));
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    private boolean isCommonCard(AbstractCard card){
        return card.rarity == AbstractCard.CardRarity.COMMON ||
                card.rarity == AbstractCard.CardRarity.BASIC;
                // card.hasTag(AbstractCard.CardTags.STARTER_STRIKE);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}