package hellospire.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import hellospire.character.Sonic;
import hellospire.util.CardStats;

public class SonicWave extends BaseCard {
    public static final String ID = makeID("SonicWave");
    private static final CardStats info = new CardStats(
            Sonic.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private static final int MAGIC = 1;

    public SonicWave() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    /// "DESCRIPTION": "Deal !D! damage. NL Evoke all of your orbs.",
    /// "UPGRADE_DESCRIPTION": "Deal !D! damage. NL Channel !M! Lightning. NL Evoke all of your orbs."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ChannelAction(new Lightning()));
        if (this.upgraded) {
            addToBot(new EvokeAllOrbsAction());
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicWave();
    }
}
