package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.WrathStance;
import theHedgehog.SonicMod;
import theHedgehog.actions.ModFastAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

import java.util.Objects;

public class SlotMachineStatus extends BaseCard {
    public static final String ID = makeID("SlotMachineStatus");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public static boolean hasAppliedDebuff = false;

    public SlotMachineStatus() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerWhenDrawn() {
        if (!hasAppliedDebuff) {
            addToBot(new ModFastAction(() -> {
                int count = 0;
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    SonicMod.logger.info(c.cardID + " " + SlotMachineStatus.ID);
                    if (c instanceof SlotMachineStatus) {
                        count++;
                    }
                }
                if (count > 1) {
                    hasAppliedDebuff = true;
                    addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
                }

            }));
        }

        super.triggerWhenDrawn();
    }

    @Override
    public AbstractCard makeCopy() { // Optional
        return new SlotMachineStatus();
    }
}
