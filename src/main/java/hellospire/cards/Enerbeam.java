package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.character.Sonic;
import hellospire.powers.NextTurnEchoPower;
import hellospire.util.CardStats;

public class Enerbeam extends BaseCard {
    public static final String ID = makeID("Enerbeam");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 3;

    public Enerbeam() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        if (!m.hasPower("Artifact")) {
            addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Enerbeam();
    }
}
