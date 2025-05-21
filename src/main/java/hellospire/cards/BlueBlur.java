package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EchoPower;
import hellospire.character.MyCharacter;
import hellospire.powers.LoseEchoPower;
import hellospire.powers.NextTurnEchoPower;
import hellospire.util.CardStats;

public class BlueBlur extends BaseCard {
    public static final String ID = makeID("BlueBlur");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    private static final int BLOCK = 1;

    public BlueBlur() {
        super(ID, info);

        setBlock(BLOCK);
        setExhaust(true);
    }

    ///            "DESCRIPTION": "Gain !B! Block. Next turn, your first move will play twice."

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new NextTurnEchoPower(p, 1)));
    }



    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlueBlur();
    }
}
