package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.BoostAction;
// import theHedgehog.actions.RushdownAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class Boost extends BaseCard {
    public static final String ID = makeID("Boost");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public Boost() {
        super(ID, info);
        this.cardsToPreview = new Ring();
        this.isMultiDamage = true;
        setDamage(DAMAGE, UPG_DAMAGE);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.Boost));
        addToBot(new BoostAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        // addToBot(new RushdownAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Boost();
    }
}
