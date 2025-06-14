package hellospire.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.Objects;

public class LightSpeedDash extends BaseCard {
    public static final String ID = makeID("LightSpeedDash");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public LightSpeedDash() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        this.setExhaust(true);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), CalculateRings()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int ringsPlayed = 0;
                for (AbstractCard card : p.hand.group) {
                    if (Objects.equals(card.cardID, Ring.ID)) {
                        ringsPlayed++;
                        addToBot(new NewQueueCardAction(card, modGetRandomMonster(), true, true));
                    }
                }
                if (ringsPlayed > 6) {
                    addToBot(SoundLibrary.PlayVoice(SoundLibrary.FeelingGood));
                }
                this.isDone = true;
            }
        });
    }

//    @Override
//    public void triggerOnOtherCardPlayed(AbstractCard c) {
//        super.triggerOnOtherCardPlayed(c);
//
//        this.rawDescription = cardStrings.DESCRIPTION + String.format(" (%s Rings)", CalculateRings());
//        initializeDescription();
//    }

    private int CalculateRings() {
        return BaseMod.MAX_HAND_SIZE - (AbstractDungeon.player.hand.size() - 1);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LightSpeedDash();
    }
}
