package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.Objects;

public class SlotMachineStatus extends BaseCard {
    public static final String ID = makeID("SlotMachineStatus");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public static boolean hasAppliedDebuff = false;
    // private static final int FRAIL_AMT = 2;

    public SlotMachineStatus() {
        super(ID, info);
        // setMagic(FRAIL_AMT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void atTurnStartPreDraw() {
        hasAppliedDebuff = false;
    }

    @Override
    public void triggerWhenDrawn() {
        if (!hasAppliedDebuff){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int count = 0;
                    for (AbstractCard c : AbstractDungeon.player.hand.group){
                        SonicMod.logger.info(c.cardID + " " + SlotMachineStatus.ID);
                        if (Objects.equals(c.cardID, SlotMachineStatus.ID)){
                            count++;
                            SonicMod.logger.info("substracted. count is now " + count);
                        }
                    }
                    if (count > 1){
                        hasAppliedDebuff = true;
                        addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
                        // addToBot(new ApplyPowerAction(
                        //         AbstractDungeon.player,
                        //         AbstractDungeon.player,
                        //         new FrailPower(AbstractDungeon.player, magicNumber, false), magicNumber));
                    }

                    this.isDone = true;
                }
            });
        }

        super.triggerWhenDrawn();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SlotMachineStatus();
    }
}
