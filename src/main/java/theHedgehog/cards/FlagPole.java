package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import theHedgehog.SonicTags;
import theHedgehog.actions.ModFastAction;
import theHedgehog.actions.ModXFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.powers.FlagPolePower;
import theHedgehog.util.CardStats;

public class FlagPole extends BaseCard {
    public static final String ID = makeID("FlagPole");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public FlagPole() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        // setExhaust(true, false);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FlagPolePower(p, 1)));
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeCopy(), magicNumber));
        }
        addToBot(new ModXFastAction(() -> {
            if (p.hand.isEmpty()) {
                return;
            }
            for (AbstractCard card : p.hand.group) {
                if (card.hasTag(SonicTags.RING)) {
                    card.setCostForTurn(-9);
                }
            }
        }));
        // addToBot(new ModFastAction(() -> {
        //     p.updatePowers();
        //     for (AbstractCard card : p.hand.group) {
        //         if (card.hasTag(SonicTags.RING)) {
        //             addToBot(new ChannelAction(new Frost()));
        //         }
        //     }
        // }));
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new FlagPole();
    }
}
