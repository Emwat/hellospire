package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class LevelUp1 extends BaseCard {
    public static final String ID = makeID("LevelUp1");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );


    public LevelUp1() {
        super(ID, info);
        setDisplayRarity(CardRarity.RARE);
        loadCardImage(SonicMod.imagePath("cards/power/LevelUpSpeed.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new LevelUp1();
    }
}
