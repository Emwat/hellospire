package theHedgehog.cards;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;
import theHedgehog.SonicTags;
import theHedgehog.cardmodifiers.MagicHandsModifier;
import theHedgehog.cardmodifiers.SpinUpModifier;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class MagicHands extends BaseCard {
    public static final String ID = makeID("MagicHands");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public MagicHands() {
        super(ID, info);
        tags.add(SonicTags.LIKE_WATCHER);
        CardModifierManager.addModifier(this, new SpinUpModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChangeStanceAction(CalmStance.STANCE_ID));
        addToBot(new SelectCardsInHandAction(1, CardCrawlGame.languagePack.getUIString(makeID("MagicHandsMessage")).TEXT[0],
                false, false, filter -> !filter.tags.contains(SonicTags.DO_NOT_THROW), cards -> {
            if (cards.isEmpty()) {
                return;
            }
            if (!this.upgraded) {
                for (AbstractCard card : cards) {
                    CardModifierManager.addModifier(card, new RetainMod());
                }
            } else {
                for (AbstractCard card : cards) {
                    CardModifierManager.addModifier(card, new MagicHandsModifier());
                }
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MagicHands();
    }
}
