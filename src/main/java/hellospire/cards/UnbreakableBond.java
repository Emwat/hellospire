package hellospire.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.orbs.Plasma;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class UnbreakableBond extends BaseCard {
    public static final String ID = makeID("UnbreakableBond");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2
    );

    private static final int MAGIC = 10;
    private static final int UPG_MAGIC = 2;

    public UnbreakableBond() {
        super(ID, info);

//        tags.add(CardTags.HEALING);
//        setMagic(MAGIC, UPG_MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new HealAction(p, p, magicNumber));
        addToBot(new IncreaseMaxOrbAction(1));
        if (this.upgraded) {
            addToBot(new ChannelAction(new Lightning()));
        }
        addToBot(new ChannelAction(new Plasma()));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.setExhaust(false);
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new UnbreakableBond();
    }
}
