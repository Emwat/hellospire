package hellospire.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Vault;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

import java.util.Objects;

public class SlotMachinePullEggman extends BaseCard {
    public static final String ID = makeID("SlotMachineEggman");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public SlotMachinePullEggman() {
        super(ID, info);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerWhenDrawn() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group){
            SonicMod.logger.info(c.cardID + " " + SlotMachinePullEggman.ID);
            if (Objects.equals(c.cardID, SlotMachinePullEggman.ID)){
                count++;
                SonicMod.logger.info("substracted. count is now " + count);
            }
        }
        SonicMod.logger.info("count is " + count);

        if (count > 0){
            addToBot(new PressEndTurnButtonAction());
        }

        super.triggerWhenDrawn();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SlotMachinePullEggman();
    }
}
