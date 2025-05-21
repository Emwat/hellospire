package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hellospire.SoundLibrary;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class BlueTornado extends BaseCard {
    public static final String ID = makeID("BlueTornado");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 99;

    public BlueTornado() {
        super(ID, info);
        this.cardsToPreview = new Ring();

//        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);

    }

    /// "Apply !M! Vulnerable. NL Add a Ring to your hand."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(SoundLibrary.BlueTornado));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
        addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), !upgraded ? 1 : 2 ));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlueTornado();
    }
}
