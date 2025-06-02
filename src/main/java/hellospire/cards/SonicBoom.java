package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.powers.SonicBoomPower;
import hellospire.util.CardStats;

public class SonicBoom extends BaseCard {
    public static final String ID = makeID("SonicBoom");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 2;

    public SonicBoom() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new SonicBoomPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicBoom();
    }
}
