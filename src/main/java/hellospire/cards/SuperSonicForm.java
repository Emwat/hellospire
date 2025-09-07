package hellospire.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import hellospire.SoundLibrary;
import hellospire.actions.ModFastAction;
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
        // this.rarity = CardRarity.RARE;
        setDisplayRarity(CardRarity.RARE);
        // setBackgroundTexture();

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.VoiceAction(SoundLibrary.SonicStyle));
//        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SuperSonicPower(p)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new WraithFormPower(p, -1), -1));

        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new FocusPower(p, magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new BiasPower(p, 1), 1));
        }
        if (p instanceof Sonic) {
            addToBot(new ModFastAction(() -> {
                ((Sonic) p).playAnimation("super");
            }));
        }

//        addToBot(new ApplyPowerAction(p, p, new SuperSonicPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SuperSonicForm();
    }
}
