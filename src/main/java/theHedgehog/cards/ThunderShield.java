package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.character.Sonic;
import theHedgehog.powers.ThunderShieldPower;
import theHedgehog.util.CardStats;

public class ThunderShield extends BaseCard {
    public static final String ID = makeID("ThunderShield");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ThunderShield() {
        super(ID, info);
        this.cardsToPreview = new Ring();

        setMagic(MAGIC, UPG_MAGIC);
        // setCostUpgrade(2);

        tags.add(SonicTags.LIKE_DEFECT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.LightningShield));
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new ChannelAction(new Lightning()));
        }
        addToBot(new ApplyPowerAction(p, p, new ThunderShieldPower(p, 1)));
    }

    // public void upgrade() {
    //     if (!this.upgraded) {
    //         this.setInnate(true);
    //     }
    //
    //     super.upgrade();
    // }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ThunderShield();
    }
}
