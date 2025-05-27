package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hellospire.SoundLibrary;
import hellospire.character.Sonic;
import hellospire.powers.RingPower;
import hellospire.util.CardStats;

public class Ring extends BaseCard {
    public static final String ID = makeID("Ring");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int EXHAUSTIVE = 2;

    public Ring() {
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
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction(SoundLibrary.Ring));
        addToBot(new HealAction(p, p, MAGIC));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard flurryOfBlows = new Boost();
                if (!p.discardPile.isEmpty()) {
                    for (AbstractCard card : p.discardPile.group) {
                        if (card.cardID == flurryOfBlows.cardID) {
                            addToBot(new DiscardToHandAction(card));
                        }
                    }
                }
                this.isDone = true;
            }
        });

    }

    /// "DESCRIPTION": "Retain. NL Ring. NL While you have this in your hand, you have two temporary dexterity. Exhaust."
    @Override
    public void triggerWhenCopied() {
        super.triggerWhenCopied();
        AbstractPlayer p = AbstractDungeon.player;
        addToTop(new ApplyPowerAction(p, p, new RingPower(p, 1)));
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
        return new Ring();
    }
}
