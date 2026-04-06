package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class CloseShave extends BaseCard {
    public static final String ID = makeID("CloseShave");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 20;
    private static final int UPG_MAGIC = 10;
    // private int blockDeductions = 0;

    public CloseShave() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int outputBlock = block;
        addToBot(new GainBlockAction(p, outputBlock));
        if (p.currentHealth < magicNumber) {
            addToBot(new GainBlockAction(p, outputBlock));
        }
        addToBot(new ModifyBlockAction(this.uuid, -1));
    }


    @Override
    public AbstractCard makeCopy() { // Optional
        return new CloseShave();
    }
}
