package hellospire.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hellospire.SonicMod;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;
import hellospire.util.CardStats;

import java.util.ArrayList;
import java.util.Arrays;
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
        int ringsPlayed = CalculateRings();
        RingPower.setIsLightSpeedDashing(true);
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), ringsPlayed));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : p.hand.group) {
                    if (Objects.equals(card.cardID, Ring.ID)) {
                        addToBot(new NewQueueCardAction(card, modGetRandomMonster(), true, true));
                    }
                }
                if (ringsPlayed > 6) {
                    addToBot(SoundLibrary.PlayRandomVoice(new ArrayList<>(Arrays.asList(
                            SoundLibrary.FeelingGood,
                            SoundLibrary.Hehe,
                            SoundLibrary.SmallYahoo,
                            SoundLibrary.SmallYes
                    ))));
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


    @Override
    public void triggerOnEndOfPlayerTurn() {
        // this function does not trigger.
        // SonicMod.logger.info("You are Light Speed Dashing... " + RingPower.isLightSpeedDashing);
        // RingPower.setIsLightSpeedDashing(false);
        // SonicMod.logger.info("After set: You are Light Speed Dashing... " + RingPower.isLightSpeedDashing);

    }

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
