package hellospire.cards;

import com.megacrit.cardcrawl.actions.unique.NightmareAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class GizoidEggman extends BaseCard {
    public static final String ID = makeID("GizoidEggman");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 3;

    public GizoidEggman() {
        super(ID, info);

        setCostUpgrade(2);
        setMagic(MAGIC);
        setExhaust(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new NightmareAction(p, p, magicNumber));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new GizoidEggman();
    }
}
