package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.PommelStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hellospire.SonicMod;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class Taunt extends BaseCard {
    public static final String ID = makeID("Taunt");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 3;

    ///    "DESCRIPTION": "Apply 2 Vulnerable. NL Gain 2 Temporary Dexterity."
    public Taunt() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPG_MAGIC);
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            loadCardImage(SonicMod.imagePath("cards/skill/Taunt2.png"));
//            this.initializeDescription();
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Taunt();
    }
}
