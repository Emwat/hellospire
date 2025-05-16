package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class SonicFlare extends BaseCard {
    public static final String ID = makeID("SonicFlare");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 16;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public SonicFlare() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int prevVigor = p.getPower("Vigor").amount;
        addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MakeTempCardInHandAction(new Trick(), 1,true));
        if( this.upgraded){
            addToBot(new ApplyPowerAction(p, p, new VigorPower(p, (int)(prevVigor * 0.5F))));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicFlare();
    }
}
