package hellospire.cards;

import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class SlotMachineGame extends BaseCard {
    public static final String ID = makeID("Slot Machine Pull");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );
    private AbstractCard Pull = new SlotMachinePull();
    private AbstractCard Curse = new SlotMachinePullEggman();

    public SlotMachineGame() {
        super(ID, info);
        MultiCardPreview.add(Pull);
        MultiCardPreview.add(new SlotMachinePullEggman());

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), CalculateRings()));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : p.hand.group){
                    if(card.cardID == cardsToPreview.cardID){
                        addToBot(new NewQueueCardAction(card, modGetRandomMonster(), true, true));
                    }
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

    private int CalculateRings(){
        return BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SlotMachineGame();
    }
}
