package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.powers.GainedHeightPower;
import hellospire.util.CardStats;

import java.util.Objects;

public class Height extends BaseCard {
    public static final String ID = makeID("Height");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 2;

    public Height() {
        super(ID, info);
        setMagic(MAGIC);

        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int stack = this.magicNumber;
        int heightsPlayedForFrostOrbs = 0;

        for (AbstractCard c : p.hand.group)
        {
            if (c == this){
                continue;
            }
            if (Objects.equals(c.cardID, this.cardID))
            {
                heightsPlayedForFrostOrbs++;
                addToBot(new AutoplayCardAction(c, p.hand));
                if(heightsPlayedForFrostOrbs == 2) {
                    heightsPlayedForFrostOrbs = 0;
//                    addToBot(new ChannelAction(new Frost()));
                }
            } else if (Objects.equals(c.cardID, Trick.ID)){
                addToBot(new UpgradeSpecificCardAction(c));
                addToBot(new AutoplayCardAction(c, p.hand));
            }

        }

        //AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
        addToBot(new ApplyPowerAction(p, p, new GainedHeightPower(p, stack), stack)
        );
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Height();
    }
}
