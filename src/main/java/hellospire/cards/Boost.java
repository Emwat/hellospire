package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.SmokeBomb;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import hellospire.SonicTags;
import hellospire.SoundLibrary;
import hellospire.actions.BoostAction;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

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
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Boost();
    }
}
