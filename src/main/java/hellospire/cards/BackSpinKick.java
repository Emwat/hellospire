package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.cards.blue.CompileDriver;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
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
        int totalDamage;
        int channeledOrbs = 0;
        for(AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o.ID != null && !o.ID.equals("Empty")) {
                channeledOrbs++;
            }
        }
        totalDamage = damage + (channeledOrbs * magicNumber);

        addToBot(new DamageAction(m, new DamageInfo(p, totalDamage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BackSpinKick();
    }
}
