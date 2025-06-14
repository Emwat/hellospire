package hellospire.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.SuperSonicPower;
import hellospire.util.CardStats;

public class SuperSonicForm extends BaseCard {
    public static final String ID = makeID("SuperSonicForm");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 7;

    public SuperSonicForm() {
        super(ID, info);
        // This code adds Super Sonic to card rewards.
//        this.rarity = CardRarity.RARE;

        setMagic(MAGIC);
        tags.add(BaseModCardTags.FORM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.PlayVoice(SoundLibrary.SonicStyle));
//        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));

        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
        }
//        addToBot(new ApplyPowerAction(p, p, new SuperSonicPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SuperSonicForm();
    }
}
