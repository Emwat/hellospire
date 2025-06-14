package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.LoseFocusPower;
import hellospire.powers.TurbulencePower;
import hellospire.util.CardStats;

public class Turbulence extends BaseCard {
    public static final String ID = makeID("Turbulence");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = -2;
    private static final int UPG_MAGIC = 1;


    public Turbulence() {
        super(ID, info);

        setMagic(MAGIC, UPG_MAGIC);
        tags.add(SonicTags.LIKE_IRONCLAD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayVoice(SoundLibrary.OmochaoTurbulence));
        addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber), -magicNumber));
        addToBot(new ApplyPowerAction(p, p, new TurbulencePower(p, 1), 1));
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy(), 1));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            this.upgradeBaseCost(2);
//            this.setInnate(true);
            this.cardsToPreview = new Trick();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Turbulence();
    }
}
