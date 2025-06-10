package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.WraithFormPower;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class GizoidChaos extends BaseCard {
    public static final String ID = makeID("GizoidChaos");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public GizoidChaos() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    /// "Show me your power. Or I shall not obey. I represent all things, and shall become Gizoid, the conquerer of all."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new WraithFormPower(p, -1), -1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GizoidChaos();
    }
}
