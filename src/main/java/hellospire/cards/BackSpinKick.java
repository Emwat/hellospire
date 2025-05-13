package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.BarrageAction;
import com.megacrit.cardcrawl.actions.watcher.BrillianceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Flechettes;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.cards.purple.SandsOfTime;
import com.megacrit.cardcrawl.cards.purple.WindmillStrike;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class BackSpinKick extends BaseCard {
    public static final String ID = makeID("BackSpinKick");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public BackSpinKick() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }


    /// Deal !D! damage. This deals !M! additional damage for each channeled orb you have.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }


    public void calculateCardDamage(AbstractMonster mo) {

        int channeledOrbs = 0;
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (!(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot)) {
                channeledOrbs++;
            }
        }

        int realBaseDamage = this.baseDamage;
        this.baseDamage += channeledOrbs * magicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }



    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BackSpinKick();
    }
}
