package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class LevelUp3 extends BaseCard {
    public static final String ID = makeID("LevelUp3");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );


    public LevelUp3() {
        super(ID, info);
        setDisplayRarity(CardRarity.RARE);
        loadCardImage(SonicMod.imagePath("cards/power/LevelUpPower.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUp3();
    }
}
