package theHedgehog.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHedgehog.SonicMod;
import theHedgehog.SonicTags;
import theHedgehog.SoundLibrary;
import theHedgehog.actions.BoostAction;
import theHedgehog.character.Sonic;
import theHedgehog.util.CardStats;

public class BoostRare extends BaseCard {
    public static final String ID = makeID("BoostRare");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            0
    );

    // Boost 4(6)
    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int MAGIC = 1;

    public BoostRare() {
        super(ID, info);
        loadCardImage(SonicMod.imagePath("cards/attack/Boost.png"));
        SetChaosEmeraldCardback();

        this.cardsToPreview = new Ring();
        this.isMultiDamage = true;
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
        tags.add(SonicTags.LIKE_WATCHER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(SoundLibrary.SoundAction(SoundLibrary.Boost));
        addToBot(new BoostAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DrawCardAction(magicNumber));
        // addToBot(new RushdownAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BoostRare();
    }
}
