package hellospire.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.SandsOfTime;
import com.megacrit.cardcrawl.cards.red.Flex;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import hellospire.powers.GainedHeightPower;
import hellospire.util.CardStats;

import java.util.Objects;

public class Height extends BaseCard {
    public static final String ID = makeID("Height");
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int EXHAUSTIVE = 2;

    public Height() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);

        this.selfRetain = true;
//        this.returnToHand = true;

        // I would like for this card to be used twice before completely exhausting.
//        ExhaustiveVariable.setBaseValue(this, EXHAUSTIVE);
//        this.isEthereal = true;
        this.exhaust = true;
//        ExhaustiveField.ExhaustiveFields.baseExhaustive.set(this, magicNumber);
//        ExhaustiveField.ExhaustiveFields.exhaustive.set(this, magicNumber);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    /// "DESCRIPTION": "Retain. NL Height. NL While you have this in your hand, you have two temporary dexterity. Exhaust."
    @Override
    public void triggerWhenCopied() {
        super.triggerWhenCopied();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new GainedHeightPower(p, 1)));
//        this.addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    }

//    @Override
//    public void triggerOnExhaust() {
//        super.triggerOnExhaust();
//        AbstractPlayer p = AbstractDungeon.player;
//        this.addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));
//    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Height();
    }
}
