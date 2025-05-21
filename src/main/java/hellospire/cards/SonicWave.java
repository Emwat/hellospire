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
import hellospire.character.MyCharacter;
import hellospire.util.CardStats;

public class SonicWave extends BaseCard {
    public static final String ID = makeID("SonicWave");
    private static final CardStats info = new CardStats(
            MyCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public SonicWave() {
        super(ID, info);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    /// "DESCRIPTION": "Deal !D! damage. NL Evoke all of your orbs.",
    /// "UPGRADE_DESCRIPTION": "Deal !D! damage. NL Channel !M! Lightning. NL Evoke all of your orbs."
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (this.upgraded) {
            for (int i = 0; i < magicNumber; i++) {
                addToBot(new ChannelAction(new Lightning()));
            }
        }
        addToBot(new EvokeAllOrbsAction());

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SonicWave();
    }
}
